package engine.process;

import java.util.Random;
import java.lang.String;


import config.AppConfiguration;
import engine.controller.ActionController;
import engine.map.*;
import engine.mobile.*;
import engine.strategy.PunitionRecompenseType;

public class GameManager {

    public GameState gameState;
    private Random random = new Random();

    // Pourcentages évolutifs
    private double pourcentageBonne = 40;

    private int compteurBonnes = 0;
    private int compteuractions = 0;
    
    private double memoireErreurs = 0;
    private double fatigue = 0;
    private  String message="";

    private double scoreProprete = 0;
    private double scoreDiscipline = 0;
    private double scoreObeissance = 0;

    private LearningStrategy learningStrategy;
    private SkillEvolutionStrategy skillStrategy;
    
    private Cell cible = null;
    private String imgname = null;

    private AnimationLogicManager animation;

    // Gestion punition
    private PunitionRecompenseType ptype =null;
    

    public GameManager(GameState gameState, double propreteInitiale) {
        this.gameState = gameState;
        this.scoreProprete = propreteInitiale;
        learningStrategy = new DefaultLearningStrategy();
        skillStrategy = new DefaultSkillStrategy();
    }
  
    public void nextRound() {

        RoomType piece = choisirPiece();
        boolean actionBonne = random.nextFloat() * 100f < pourcentageBonne;


        lancerAnimation(piece, actionBonne);
        updateCompteur(actionBonne);
        int deg = animation.getDegPunition();

	     // mise à jour mémoire
	     memoireErreurs += (-deg) * 0.25;
	     memoireErreurs = Math.max(0, Math.min(30, memoireErreurs));
	
	     // fatigue
	     fatigue = Math.sqrt(compteuractions)/4;
        ajusterPourcentages();
        calculerCompetences();
        
        if (animation != null) {
            updateMessage();
        }

    }

    private RoomType choisirPiece() {
        ActionController controller = new ActionController(gameState.getRoom());
        return controller.choisirTypeCellulePourAction();
    }

    private void updateCompteur(boolean actionBonne){
        if(actionBonne) compteurBonnes++;
    }
    
    private void ajusterPourcentages(){
    	pourcentageBonne = learningStrategy.updatePourcentage(
    		    pourcentageBonne,
    		    animation.getDegPunition(),
    		    memoireErreurs,
    		    fatigue,
    		    compteurBonnes,
    		    scoreProprete,
    		    scoreDiscipline,
    		    scoreObeissance
    		);
    	pourcentageBonne = Math.max(10, Math.min(95, pourcentageBonne));
    }

    private void lancerAnimation(RoomType piece, boolean actionBonne){
        animation=new AnimationLogicManager(gameState.getRoom(), gameState.getAnimal().getType());
        compteuractions++;
        cible = animation.CellCibleAnimation(piece,actionBonne);
        imgname = animation.getImgName();
        ptype = animation.getPunitionRecompense();
        
    }

    private void calculerCompetences() {

    	double[] gains = skillStrategy.compute(
    		    scoreProprete,
    		    scoreDiscipline,
    		    scoreObeissance,
    		    animation.getPr(),
    		    animation.getDs(),
    		    animation.getOb(),
    		    animation.getDegPunition()
    		);
    	

    		scoreProprete += gains[0];
    		scoreDiscipline += gains[1];
    		scoreObeissance += gains[2];
    		
    		scoreProprete = Math.max(0, Math.min(100, scoreProprete));
    		scoreDiscipline = Math.max(0, Math.min(100, scoreDiscipline));
    		scoreObeissance = Math.max(0, Math.min(100, scoreObeissance));
    }
    
    
    private void updateMessage() {

        int deg = animation.getDegPunition();

        String signe = (deg >= 0) ? "Bonne action ✓" : "Mauvaise action ✗";

        message = String.format(
            "%s\nDegre de recompense/punition: %+d\nProprete: %.2f\nDiscipline: %.2f\nObeissance: %.2f",
            signe,
            deg,
            scoreProprete,
            scoreDiscipline,
            scoreObeissance
        );
    }
    
    
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
        
        memoireErreurs = 0;
        fatigue = 0;
        
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
    public double getFatigue() { return fatigue; }
    public double getMemoireErreurs() { return memoireErreurs; }
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