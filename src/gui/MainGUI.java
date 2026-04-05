package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import config.AppConfiguration;
import engine.map.Room;
import engine.mobile.Animal;
import engine.mobile.AnimalType;
import engine.mobile.Maitre;
import engine.process.GameBuilder;
import engine.process.GameManager;
import engine.process.GameState;

public class MainGUI extends JFrame implements Runnable {

    private static final long serialVersionUID = 1L;

    private Room room;
    private GameManager gameManager;

    private RoomView roomView;
    private TopPanel topPanel;
    private RightPanel rightPanel;
    private AnimalType animaltype;
    
    private boolean stop = true;  
    
    private List<Double> histProprete   = new ArrayList<>();
    private List<Double> histDiscipline = new ArrayList<>();
    private List<Double> histObeissance = new ArrayList<>();
    
    private final static Dimension gridSize = new Dimension(
                    AppConfiguration.COLUMN_COUNT * AppConfiguration.CELL_SIZE,
                    AppConfiguration.LINE_COUNT * AppConfiguration.CELL_SIZE
            );

    public MainGUI(String title, AnimalType type) {
        super(title);
        animaltype = type;
        init();
    }

    private void init() {

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        

        //  Construction du jeu
        room = GameBuilder.buildRoom();
        GameBuilder.buildObjects(room,animaltype);

        Animal animal = GameBuilder.buildAnimal(room,  animaltype, "Mimi");
        Maitre maitre = GameBuilder.buildMaitre(room);

        
        
        GameState gameState = new GameState(room, animal, maitre);
        gameManager = GameBuilder.buildGameManager(gameState, animaltype);
        
        //  Panel in top 
        topPanel = new TopPanel(this);
        contentPane.add(topPanel, BorderLayout.NORTH);

        //  Vue centrale (grille)
        roomView = new RoomView(room, animal, maitre);
        roomView.setPreferredSize(gridSize);
        //  Panel droit (compétences + infos)
        rightPanel = new RightPanel();
        rightPanel.setPreferredSize(new Dimension(400, gridSize.height));
        
        rightPanel.updateData(
    	        gameManager.getBonnesAct(),
    	        0,
    	        gameManager.getScoreProprete(),
    	        0,
    	        0
    	    );

        contentPane.add(roomView, BorderLayout.CENTER);
        contentPane.add(rightPanel, BorderLayout.EAST);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setResizable(false);
    }

    
    public void run() {

        while (!stop) {

            try {
                Thread.sleep(AppConfiguration.GAME_SPEED);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

            gameLoop();
        }
    }

    private void gameLoop() {
    	
    	// Si l’animal a terminé l’animation
    	System.out.print(roomView.animationTerminee());
    	
    	if (roomView.animationTerminee() ) {
    		System.out.println(" ----    lancer     ------ " + roomView.punitionTermine()+" ----    punition     ------ "+gameManager.getPunitionCourante());
    		if (gameManager.getPunitionCourante()!=null && roomView.getPunition() == null && !roomView.punitionTermine()) {
    			roomView.setPunition(gameManager.getPunitionCourante());
    			//System.out.println("Le set de punition" + roomView.getPunition());
    		}
    		if(roomView.punitionTermine()) {
    			System.out.println(" *****   Lancer la prochaine anim    *******     ");
    			gameManager.nextRound();
    	       roomView.setCibleImg(
    	        gameManager.getCible(),
    	        gameManager.getImgname()
    	    );

    	    rightPanel.updateData(
    	        gameManager.getBonnesAct(),
    	        gameManager.getActions(),
    	        gameManager.getScoreProprete(),
    	        gameManager.getScoreDiscipline(),
    	        gameManager.getScoreObeissance()
    	    );
    	    rightPanel.addMessage(gameManager.getMessage());
    		}
    		histProprete.add(gameManager.getScoreProprete());
    		histDiscipline.add(gameManager.getScoreDiscipline());
    		histObeissance.add(gameManager.getScoreObeissance());

    		if (gameManager.getActions() >= AppConfiguration.NBR_MAX_ACTIONS) {
    		    stop = true;
    		    showEndScreen();
    		    return;
    		}
    		
    		
    		if(gameManager.getPunitionCourante()!=null) {
    			roomView.setLance(false);
    		}
    		
    	}
    	
        roomView.repaint();
        
    }
    
    
    public boolean isRunning() {
        return !stop;
    }

    public void startThread() {
        stop = false;
        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    public void stopThread() {
        stop = true;
    }
    
    public void resetGame() {

        stop = true;

        gameManager.reset();

        roomView.resetGameData(
            gameManager.getGameState().getRoom(),
            gameManager.getGameState().getAnimal(),
            gameManager.getGameState().getMaitre()
        );
        rightPanel.clearLog();

        rightPanel.updateData(40, 0, gameManager.getScoreProprete(), 0, 0);

        repaint();
    }

    private void showEndScreen() {
        histProprete.add(gameManager.getScoreProprete());
        histDiscipline.add(gameManager.getScoreDiscipline());
        histObeissance.add(gameManager.getScoreObeissance());
        dispose();
        new EndGUI(
            gameManager.getScoreProprete(),
            gameManager.getScoreDiscipline(),
            gameManager.getScoreObeissance(),
            histProprete,
            histDiscipline,
            histObeissance,
            animaltype
        );
    }
	
    
    
}