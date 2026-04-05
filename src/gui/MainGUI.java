package gui;

import java.awt.BorderLayout;

import java.awt.Dimension;

import javax.swing.JFrame;

import config.AppConfiguration;
import engine.map.Room;
import engine.mobile.Animal;
import engine.process.GameBuilder;
import engine.process.GameManager;
import engine.process.GameState;
import gui.animation.Animation;
import gui.animation.GestionAnimations;

public class MainGUI extends JFrame implements Runnable {
    private static final long serialVersionUID = 1L;
    
    private GameState gameState;
    private GameManager gameManager;
    private GestionAnimations gestionAnimations;
    private RoomView roomView;
    private RightPanel rightPanel;
    private TopPanel topPanel;
    private String typeAnimal;
    private int nbActions = 0;
    private boolean actionTraitee = false;


    public MainGUI(String typeAnimal, String title) {
        super(title);
        this.typeAnimal =typeAnimal; 
        init();
    }
    
    private void init() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        gameState = GameBuilder.buildGame(typeAnimal, "Minou");
        gameManager = new GameManager(gameState);

        Room room = gameState.getRoom();
        Animal animal = gameState.getAnimal();

        // Créer RoomView sans gestionAnimations
        roomView = new RoomView(room, animal, null);

        // Créer GestionAnimations en lui passant roomView
        gestionAnimations = new GestionAnimations(room, animal, roomView);

        //Donner gestionAnimations au RoomView
        roomView.setGestionAnimations(gestionAnimations);

        topPanel = new TopPanel(gameManager, gestionAnimations, this);
        rightPanel = new RightPanel();
        
        add(topPanel,  BorderLayout.NORTH);
        add(roomView,  BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        this.pack();
        this.setSize(1000, 650);      // taille initiale
        this.setMinimumSize(new Dimension(1000,800));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    
    @Override
    public void run() {
        while (true) {
            if (!topPanel.isPaused()) {
                update();
            }
            
            roomView.repaint();
            rightPanel.repaint();
            
            try {
                Thread.sleep(AppConfiguration.DUREE_DEPLACEMENT); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void update() {
        gestionAnimations.update();
        
        if (gestionAnimations.getAnimationEnCours() != null) {
        	Animation anim = gestionAnimations.getAnimationEnCours();
            if (anim.isTerminee() && 
                anim.getType() != gui.animation.Animation.TypeAnimation.SE_DEPLACER) {
            	if (!actionTraitee) {
            	gameManager.traiterAction(anim.getType());
                nbActions++; 
                rightPanel.update(gameState.getAnimal(), nbActions);
                actionTraitee = true; 
            	}
            } else {
                actionTraitee = false; 
            }
        }
    }
    public void resetNbActions() {
        nbActions = 0;
        rightPanel.update(gameState.getAnimal(), nbActions);
    }
}