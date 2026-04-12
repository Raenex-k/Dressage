package config;

import java.awt.Color;
import java.awt.Font;

public class AppConfiguration {
    public static final int WINDOW_WIDTH = 1400;
    public static final int WINDOW_HEIGHT = 1200;
    public static final int CELL_SIZE = 48;
    public static final int LINE_COUNT = 16;
    public static final int COLUMN_COUNT = 16 ;
    public static final int GAME_SPEED =10;
    public static final int ANIMATION_DURATION = 10;

    //House colors
    
    public static final Color CUISINE_COL = new Color(0xB5C9F2);
    public static final Color SALON_COL = new Color(0xFFE1C7);
    public static final Color CHAMBRE_COL = new Color(0xFFC0C0);
    public static final Color JARDIN_COL = new Color(0xDDFFC1);
    
    //skills intervals
    public static final int SEUIL_DISCIPLINE = 24;
    public static final int SEUIL_PROPRETE = 16;
    public static final int SEUIL_OBIESSANCE = 12;
    
    // Probabilités en pourcentage d'etre dans une piece
    public static final int SALON_PROB = 35;
    public static final int CUISINE_PROB = 25;
    public static final int JARDIN_PROB = 25;
    public static final int CHAMBRE_PROB = 15;
    
    // Animations
    public static final int NBR_MAX_ACTIONS = 100;
    public static final int DUREE_DEPLACEMENT = 50;
    public static final int DUREE_ACTION = 30;
    public static final int DUREE_ATTENTE = 40; 
    
    public static  int SEUIL_BONNES_ACTIONS = 10;
    
    
    public static final Font textfont= new Font("Arial", Font.BOLD, 18);
    
}