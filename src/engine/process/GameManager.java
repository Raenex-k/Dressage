package engine.process;

import java.util.Random;
import gui.RightPanel;
import java.lang.String;


import config.AppConfiguration;
import engine.controller.ActionController;
import engine.map.*;
import engine.mobile.*;
import engine.strategy.PunitionRecompenseType;

public class GameManager {

    public GameState gameState;
    private Random random = new Random();
    private RightPanel rightPanel;

    // Pourcentages évolutifs
    private double pourcentageBonne = 40;

    private int compteurBonnes = 0;
    private int compteuractions = 0;
    
    private double memoireErreurs = 0;
    private double fatigue = 0;
    private  String message;

    private double scoreProprete = 0;
    private double scoreDiscipline = 0;
    private double scoreObeissance = 0;

    private Cell cible = null;
    private String imgname = null;

    private AnimationLogicManager animation;

    // Gestion punition
    private PunitionRecompenseType ptype =null;
    

    public GameManager(GameState gameState){
        this.gameState = gameState;
    }
    public GameManager(GameState gameState, double propreteInitiale) {
        this.gameState = gameState;
        this.scoreProprete = propreteInitiale;
    }
    public void setRightPanel(RightPanel panel) {
        this.rightPanel = panel;
    }
    public void nextRound() {

        RoomType piece = choisirPiece();
        boolean actionBonne = random.nextFloat() * 100f < pourcentageBonne;


        lancerAnimation(piece, actionBonne);
        updateCompteur(actionBonne);
        ajusterPourcentages();
        calculerCompetences();
        

    }

    private RoomType choisirPiece() {
        ActionController controller = new ActionController(gameState.getRoom());
        return controller.choisirTypeCellulePourAction();
    }

    private void updateCompteur(boolean actionBonne){
        if(actionBonne) compteurBonnes++;
    }
    
    private void ajusterPourcentages(){

        int deg = animation.getDegPunition();

        // ---------------- MEMOIRE COMPORTEMENTALE ----------------
        // Les erreurs augmentent la mémoire, mais elle est bornée
        memoireErreurs += (-deg) * 0.25;
        memoireErreurs = Math.max(0, Math.min(30, memoireErreurs));

        // ---------------- FATIGUE ----------------
        fatigue = Math.sqrt(compteuractions);

        // ---------------- APPRENTISSAGE ----------------
        double apprentissage = Math.log(1 + memoireErreurs);

        if(deg >= 0){
            apprentissage *= 3;  // récompense plus efficace
        }
        else if(deg < 0){
            apprentissage *= -0.6; // punition moins efficace
        }

        // ---------------- FATIGUE EFFET ----------------
        double effetFatigue = fatigue * 0.08;

        // ---------------- DELTA FINAL ----------------
        double delta = apprentissage - effetFatigue;

        // ---------------- BRUIT COMPORTEMENTAL ----------------
        double bruit = random.nextGaussian() * 0.3;

        // ---------------- MISE A JOUR ----------------
        pourcentageBonne += delta + bruit;

        // ---------------- BONUS DE SERIE ----------------
        if(compteurBonnes > AppConfiguration.SEUIL_BONNES_ACTIONS){

            double bonus = Math.sqrt(compteurBonnes) * 0.01;
            pourcentageBonne += bonus;

            compteurBonnes = 0;
        }

        // ---------------- APPRENTISSAGE PASSIF ----------------
        // même sans récompense l'animal apprend un peu
        pourcentageBonne +=(scoreProprete +scoreDiscipline+scoreObeissance)/400 ;

        // ---------------- LIMITES ----------------
        pourcentageBonne = Math.max(10, Math.min(95, pourcentageBonne));

        System.out.println("********* pourcentage Bonne: " + pourcentageBonne);
    }

    private void lancerAnimation(RoomType piece, boolean actionBonne){
        animation=new AnimationLogicManager(gameState.getRoom(), gameState.getAnimal().getType());
        compteuractions++;
        cible = animation.CellCibleAnimation(piece,actionBonne);
        imgname = animation.getImgName();
        ptype = animation.getPunitionRecompense();
        
    }

    private void calculerCompetences() {

        int impactPr = animation.getPr();
        int impactDs = animation.getDs();
        int impactOb = animation.getOb();

        int deg = animation.getDegPunition();

        double diffPr = 1 - Math.pow(scoreProprete*2/300.0,2);
        double diffDs = 1 - Math.pow(scoreDiscipline*2/300.0,2);
        double diffOb = 1 - Math.pow(scoreObeissance*2/300.0,2);

        double gainPr = impactPr * diffPr * Math.log(1 + Math.abs(impactPr));
        double gainDs = impactDs * diffDs * Math.log(1 + Math.abs(impactDs));
        double gainOb = impactOb * diffOb * Math.log(1 + Math.abs(impactOb));

        double bonusPr = scoreDiscipline * 0.04;
        double bonusDs = scoreObeissance * 0.08;
        double bonusOb = scoreProprete * 0.06;

        double fatigueSkill = Math.abs(deg) * 0.4;

        double addP = gainPr + bonusPr - fatigueSkill + random.nextGaussian()*0.25;
        double addD = gainDs + bonusDs - fatigueSkill + random.nextGaussian()*0.25;
        double addO = gainOb + bonusOb - fatigueSkill + random.nextGaussian()*0.25;
        
        scoreProprete += addP;
        scoreDiscipline += addD ;
        scoreObeissance += addO;

        scoreProprete = Math.max(0, Math.min(100, scoreProprete));
        scoreDiscipline = Math.max(0, Math.min(100, scoreDiscipline));
        scoreObeissance = Math.max(0, Math.min(100, scoreObeissance));
        String signe = (deg >= 0) ? "Bonne action ✓" : "Mauvaise action ✗";

        this.message = String.format(
        	    "%s\nDegre de recompense/punition: %+d\nProprete: %+.2f%%\nDiscipline: %+.2f%%\nObeissance: %+.2f%%",
        	    signe,
        	    deg,
        	    addP,
        	    addD,
        	    addO
        	);    }
    
    
    
    public void reset() {
        Room room = GameBuilder.buildRoom();
        GameBuilder.buildObjects(room, gameState.getAnimal().getType());

        Animal animal = gameState.getAnimal();
        animal.setPosition(room.getCells()[0][0]);
        animal.resetSkills();

        scoreProprete = scoreDiscipline = scoreObeissance = 0;
        if(animation != null) animation.resetPrDsOb();

        Maitre maitre = gameState.getMaitre();
        Cell posInitiale = room.getCells()[AppConfiguration.LINE_COUNT/2][AppConfiguration.COLUMN_COUNT/2];
        maitre.setPosition(posInitiale);

        gameState.setRoom(room);
        
        pourcentageBonne = 40;

        compteurBonnes = 0;
        compteuractions = 0;
        message="";
        
        scoreProprete = 0;
        if (gameState.getAnimal().getType() == AnimalType.CHAT) {
        	 scoreProprete = 20;
        }
        scoreDiscipline = 0;
        scoreObeissance = 0;
        
        cible = null;
        
        animation =null;
        
        imgname = null;

        // Reset punition/recompense
        ptype =null;
    }

    // Getters
    public GameState getGameState() {return gameState;}
    public Cell getCible() { return cible; }
    public String getImgname() { return imgname; }
    public void setScoreProprete(double s) {scoreProprete=s;}
    public double getScoreProprete() { return scoreProprete; }
    public double getScoreDiscipline() { return scoreDiscipline; }
    public double getScoreObeissance() { return scoreObeissance; }
    public double getBonnesAct() { return pourcentageBonne; }
    public PunitionRecompenseType getPunitionCourante() { return ptype; }

	public int getActions() {	
		return compteuractions;
	}
	public String getMessage() {
		String reaction = ""; 
		if (ptype != null) {
			switch (ptype) { 
			case CARESSER: reaction = "Le maître caresse l'animal (+++)";
			break;
			case ENCOURAGER: reaction = "Le maître encourage l'animal (+)";
			break;
			case REPRIMANDE: reaction = "Le maître réprimande l'animal (-)";
			break;
			case DIRE_STOP: reaction = "Le maître dit STOP (--)";
			break;
			case FRAPPER: reaction = "Le maître frappe l'animal (---)";
			break;
			default: reaction = "Aucune réaction du maître";
			break;
			} 
			} else { reaction = "Aucune réaction du maître"; }
		
		return message+"\n"+reaction;}	
}