package gui;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import config.AppConfiguration;
import engine.map.Cell;
import engine.map.Room;
import engine.statique.Objet;
import engine.mobile.Animal;
import gui.animation.GestionAnimations;
import engine.map.*;

public class RoomView extends JPanel {
    private static final long serialVersionUID = 1L;
    private Room room;
    private Animal animal;
    private GestionAnimations gestionAnimations;
    
    // Cache des images
    private HashMap<String, Image> imageCache = new HashMap<String, Image>();
    
    // Images temporaires qui REMPLACENT l'image de l'objet
    private HashMap<Cell, String> imagesTemporaires = new HashMap<Cell, String>();
    
    // Overlays = images qui se SUPERPOSENT (pipi, ordures)
    private HashMap<Cell, String> overlays = new HashMap<Cell, String>();
    
    // Compteurs pour restauration/suppression automatique
    private HashMap<Cell, Integer> compteursRestauration = new HashMap<Cell, Integer>();
    private HashMap<Cell, Integer> compteursSuppression = new HashMap<Cell, Integer>();
    
    public RoomView(Room room, Animal animal, GestionAnimations gestionAnimations) {
        this.room = room;
        this.animal = animal;
        this.gestionAnimations = gestionAnimations;
        
        setPreferredSize(new Dimension(
            room.getColumnCount() * AppConfiguration.CELL_SIZE,
            room.getLineCount() * AppConfiguration.CELL_SIZE
        ));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int size = AppConfiguration.CELL_SIZE;
        
        // Dessiner la pièce
        dessinerPiece(g2, size);
        
        // Dessiner l'animal
        dessinerAnimal(g2, size);
        
        // Gérer les compteurs
        mettreAJourCompteurs();
    }
    
    private void dessinerPiece(Graphics2D g2, int size) {
        for (Cell[] line : room.getCells()) {
            for (Cell cell : line) {
                Objet obj = cell.getObject();
                
                // Fond beige
                Zone zone = room.getZone(cell);
                if (obj == null) {
                    if (zone == Zone.JARDIN)  g2.setColor(new Color(200,230,200));
                    else if (zone == Zone.CUISINE) g2.setColor(new Color(255,210,210));
                    else if (zone == Zone.SALON)   g2.setColor(new Color(255,245,200));
                    else g2.setColor(new Color(225,200,240));
                } else {
                    if (zone == Zone.JARDIN)  g2.setColor(new Color(170,210,170));
                    else if (zone == Zone.CUISINE) g2.setColor(new Color(240,180,180));
                    else if (zone == Zone.SALON)   g2.setColor(new Color(240,225,160));
                    else g2.setColor(new Color(205,175,225));
                }
                g2.fillRect(cell.getColumn() * size, cell.getLine() * size, size, size);

                
                // Image de l'objet
                String nomImage = null;
                
                // Si image temporaire existe → on l'utilise
                if (imagesTemporaires.containsKey(cell)) {
                    nomImage = imagesTemporaires.get(cell);
                } 
                // Sinon, image normale de l'objet
                else if (obj != null) {
                    nomImage = obj.getName();
                }
                
                // Dessiner l'image
                if (nomImage != null) {
                    Image img = chargerImage(nomImage);
                    if (img != null) {
                        g2.drawImage(img, cell.getColumn() * size, cell.getLine() * size, size, size, null);
                    }
                }
                
                // Overlay par-dessus (pipi, ordures)
                if (overlays.containsKey(cell)) {
                    Image overlay = chargerImage(overlays.get(cell));
                    if (overlay != null) {
                        g2.drawImage(overlay, cell.getColumn() * size, cell.getLine() * size, size, size, null);
                    }
                }
                
                // Grille
                g2.setColor(new Color(200, 200, 200, 100));
                g2.drawRect(cell.getColumn() * size, cell.getLine() * size, size, size);
            }
        }
    }
    
    private void dessinerAnimal(Graphics2D g2, int size) {
        Cell position = animal.getPosition();
        Image animalImg = chargerImage(animal.getType());

        
        if (animalImg != null) {
            g2.drawImage(animalImg, position.getColumn() * size, position.getLine() * size, size, size, null);
        } else {
            // Cercle bleu si pas d'image
            g2.setColor(Color.BLUE);
            g2.fillOval(position.getColumn() * size, position.getLine() * size, size, size);
        }
    }
    
    private void mettreAJourCompteurs() {

        // Restauration des images
        Iterator<Cell> itRestauration = new ArrayList<>(compteursRestauration.keySet()).iterator();
        while (itRestauration.hasNext()) {
            Cell cell = itRestauration.next();
            int compteur = compteursRestauration.get(cell) - 1;

            if (compteur <= 0) {
                imagesTemporaires.remove(cell);
                compteursRestauration.remove(cell);
            } else {
                compteursRestauration.put(cell, compteur);
            }
        }

        // Suppression des overlays
        Iterator<Cell> itSuppression = new ArrayList<>(compteursSuppression.keySet()).iterator();
        while (itSuppression.hasNext()) {
            Cell cell = itSuppression.next();
            int compteur = compteursSuppression.get(cell) - 1;

            if (compteur <= 0) {
                overlays.remove(cell);
                compteursSuppression.remove(cell);
            } else {
                compteursSuppression.put(cell, compteur);
            }
        }
    }

    
    // ========== MÉTHODES PUBLIQUES ==========
    
    /**
     * Change l'image d'une cellule (REMPLACE l'image de l'objet)
     * Ex: vase → vase_casse
     */
    public void changerImage(Cell cell, String nouvelleImage) {
        imagesTemporaires.put(cell, nouvelleImage);
    }
    
    /**
     * Restaure l'image originale de la cellule après X frames
     * Si frames = 0, restauration immédiate
     */
    public void restaurerImageApres(Cell cell, int frames) {
        if (frames <= 0) {
            imagesTemporaires.remove(cell);
        } else {
            compteursRestauration.put(cell, frames);
        }
    }
    
    /**
     * Ajoute un overlay (image par-dessus)
     * Ex: pipi, ordures
     */
    public void ajouterOverlay(Cell cell, String nomOverlay) {
        overlays.put(cell, nomOverlay);
    }
    
    /**
     * Retire un overlay après X frames
     */
    public void retirerOverlayApres(Cell cell, int frames) {
        if (frames <= 0) {
            overlays.remove(cell);
        } else {
            compteursSuppression.put(cell, frames);
        }
    }
    
    // ========== CHARGEMENT D'IMAGES ==========
    
    private Image chargerImage(String nom) {
    	
        String key = nom.toLowerCase();
        
        if (imageCache.containsKey(key)) {
            return imageCache.get(key);
        }
        
        try {
            Image img = ImageIO.read(new File("src/images/" + key + ".png"));
            imageCache.put(key, img);
            return img;
        } catch (IOException e) {
            return null;
        }
    }
    
    
    public void setGestionAnimations(GestionAnimations gestionAnimations) {
        this.gestionAnimations = gestionAnimations;
    }

}