package gui;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.awt.Image;

import config.AppConfiguration;
import engine.map.Cell;
import engine.map.Room;
import engine.mobile.Animal;
import engine.mobile.Maitre;
import engine.strategy.PunitionRecompenseType;

public class RoomView extends JPanel {

    private static final long serialVersionUID = 1L;
    private Room room;
    private Animal animal;
    private Maitre master;
    private Cell cible;
    private String imgname;
    private boolean arriver = false;
    private int animationFrames = 0;
    private boolean lance = true;

    // Gestion punition
    private PunitionRecompenseType punition = null;
    private int punitionFrames = AppConfiguration.ANIMATION_DURATION;
    private Cell positionInitialeMaitre;

    // Cache images
    private static HashMap<String, Image> imageCache = new HashMap<>();

    public RoomView(Room room, Animal animal, Maitre master) {
        this.room = room;
        this.animal = animal;
        this.master = master;
        this.positionInitialeMaitre = master.getPosition(); // mémoriser position initiale
        setPreferredSize(new Dimension(
            room.getCells()[0].length * AppConfiguration.CELL_SIZE,
            room.getCells().length * AppConfiguration.CELL_SIZE
        ));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int size = AppConfiguration.CELL_SIZE;
        
        
        // Dessiner la grille
        for (Cell[] line : room.getCells()) {
            for (Cell cell : line) {
                // Couleur selon type pièce
                Color col;
                switch (cell.getRoomType()) {
                    case SALON: col = AppConfiguration.SALON_COL; break;
                    case CUISINE: col = AppConfiguration.CUISINE_COL; break;
                    case CHAMBRE: col = AppConfiguration.CHAMBRE_COL; break;
                    case JARDIN: col = AppConfiguration.JARDIN_COL; break;
                    default: col = Color.GRAY; break;
                }
                g.setColor(col);
                g.fillRect(cell.getColumn() * size, cell.getLine() * size, size, size);
                
                // Dessiner objets
                Image img = null;
                if (cible != null && cible.getLine() == cell.getLine() &&
                    cible.getColumn() == cell.getColumn() && arriver && animationFrames > 0) {
                    img = getCachedImage(imgname);
                } else if (cell.getObject() != null) {
                    img = getCachedImage(cell.getObject().getName());
                }

                if (img != null) {
                    g.drawImage(img, cell.getColumn() * size, cell.getLine() * size, size, size, null);
                }

                g.setColor(Color.black);
                g.drawRect(cell.getColumn() * size, cell.getLine() * size, size, size);
            }
        }
     

        //  Bordure
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.DARK_GRAY);
        g2.setStroke(new BasicStroke(4));
        int mid = AppConfiguration.LINE_COUNT / 2 * size;
        int midCol = AppConfiguration.COLUMN_COUNT / 2 * size;
        g2.drawLine(midCol, 0, midCol, AppConfiguration.LINE_COUNT * size);
        g2.drawLine(0, mid, AppConfiguration.COLUMN_COUNT * size, mid);
        g2.setStroke(new BasicStroke(1)); // reset

        //  Noms des pièces
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.setColor(new Color(0, 0, 0, 80));
        g2.drawString("Salon",   20, mid / 2);
        g2.drawString("Chambre", midCol + 20, mid / 2);
        g2.drawString("Jardin",  20, mid + mid / 2);
        g2.drawString("Cuisine", midCol + 20, mid + mid / 2);
       
        dessinerAnimal(g, size);
     // Décrémenter animation
        System.out.println(animationFrames);
        if (animationFrames > 0) {
            animationFrames--;
            if (animationFrames == 0) {
                cible = null;
                arriver = false;
            }
        }

        dessinerMaster(g,  size);

        
    }

    private void dessinerAnimal(Graphics g, int size) {
        if (animal == null) return;
        int line = animal.getPosition().getLine();
        int col = animal.getPosition().getColumn();

        // Si punition active → on ne bouge pas l’animal
        if (punition == null) {
            if (cible != null) {
                int cl = cible.getLine();
                int cc = cible.getColumn();

                if (cl == line && cc == col) {
                    if (!arriver) {
                        arriver = true;
                        animationFrames = AppConfiguration.ANIMATION_DURATION;
                    }
                } else {
                    if (line > cl) line--;
                    else if (line < cl) line++;

                    if (col > cc) col--;
                    else if (col < cc) col++;

                    animal.moveTo(room.getCells()[line][col]);
                }
            }
        }

        // on dessine TOUJOURS l’animal
        Image img = getCachedImage(animal.getType().toString());
        if (img != null) g.drawImage(img, col * size, line * size, size, size, null);
    }

    private void dessinerMaster(Graphics g, int size) {
        if (master== null) return;

        String imageName = "maitre";

        if (punition != null && punitionFrames > 0 ) {
            switch (punition) {
            
                case DIRE_STOP:
                    imageName = "maitre_stop"; // image "stop"
                    break;
                case REPRIMANDE:
                    imageName = "maitre_fache"; // image "fâché"
                    break;
                case FRAPPER:
                    imageName = "maitre_baton"; // image avec baton
                    master.deplacerVersAnimal(animal);
                    break;
                case ENCOURAGER:
                    imageName = "maitre_bravo"; // image "Bravo"
                    break;
                case CARESSER:
                    imageName = "maitrecaresse"; // image avec baton
                    master.deplacerVersAnimal(animal);
                    break;
            }

            // Décrémenter punitionFrames
            punitionFrames--;
            if (punitionFrames == 0) {
                punition = null;
                lance = true;
                master.setPosition(positionInitialeMaitre); // retour position initiale
            }
        }

        Image img = getCachedImage(imageName);
        g.drawImage(img, master.getPosition().getColumn() * size, master.getPosition().getLine() * size, size, size, null);
    }

    // Méthode pour récupérer l’image avec cache
    public static Image getCachedImage(String nom) {
        String key = nom.toLowerCase().trim();
        if (imageCache.containsKey(key)) return imageCache.get(key);
        try {
            Image img = javax.imageio.ImageIO.read(RoomView.class.getResource("/images/" + key + ".png"));
            if (img != null) imageCache.put(key, img);
            return img;
        } catch (Exception e) {
            System.err.println("Impossible de charger l'image : " + key);
            return null;
        }
    }

    // Set la cellule cible et image pour l’animation
    public void setCibleImg(Cell c, String n) {
        cible = c;
        imgname = n;
    }

    // Définir punition
    public void setPunition(PunitionRecompenseType p) {
        this.punition = p;
        lance = false;
        punitionFrames =AppConfiguration.ANIMATION_DURATION;
    }

    // Reset complet du jeu
    public void resetGameData(Room r, Animal a, Maitre m) {
        this.room = r;
        this.animal = a;
        this.master = m;
        this.positionInitialeMaitre = m.getPosition();

        cible = null;
        arriver = false;
        animationFrames = 0;
        punition = null;
        punitionFrames = 0;
        lance = true;
    }

    public boolean isArrived() { 
    	return cible == null; 
    	}
    public boolean animationTerminee() {
        return cible == null && animationFrames == 0;
    }
    public boolean punitionTermine() {
        return lance;
    }
    public PunitionRecompenseType getPunition() {
    	return punition ;
    }

	public void setLance(boolean b) {
		lance=b;
		
	}
}