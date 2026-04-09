package engine.process;

import engine.map.*;
import engine.mobile.*;
import engine.object.salon.*;
import engine.object.cuisine.*;
import engine.object.chambre.*;
import engine.object.jardin.*;
import org.apache.log4j.Logger;
import log.LoggerUtility;

import java.util.Random;

import config.AppConfiguration;

public class GameBuilder {

	private static Logger logger = LoggerUtility.getLogger(GameBuilder.class, "html");

	public static Room buildRoom() {
		logger.info("Creation de la Room " + AppConfiguration.LINE_COUNT + "x" + AppConfiguration.COLUMN_COUNT);
	    return new Room(
	        AppConfiguration.LINE_COUNT,
	        AppConfiguration.COLUMN_COUNT
	    );
	}

	public static void buildObjects(Room room, AnimalType type) {
		logger.info("Creation des objets pour type animal : " + type);
		 Random random = new Random();
	        int middleligne = AppConfiguration.LINE_COUNT / 2;
	        int middlecolumn = AppConfiguration.COLUMN_COUNT / 2;

	        for (int i = 0; i < AppConfiguration.LINE_COUNT; i++) {
	            for (int j = 0; j < AppConfiguration.COLUMN_COUNT; j++) {

	                Cell cell = room.getCells()[i][j];

	                if (!( (i == 0 && j == 0) || (i == middleligne && j == middlecolumn) )) {

	                if (i < middleligne && j < middlecolumn) {
	                    int r = random.nextInt(24);
	                    switch (r) {
	                    case 0:
	                        room.addObject(new Canape(cell));
	                        break;
	                    case 2:
	                        room.addObject(new TableBasse(cell));
	                        break;
	                    case 4:
	                        room.addObject(new VaseFragile(cell));
	                        break;
	                    case 6:
	                        room.addObject(new Tapis(cell));
	                        break;
	                    case 8:
	                    	if(type == AnimalType.CHAT) {
	                        room.addObject(new Balle(cell));}
	                    	else {
	                    		room.addObject(new Cercle(cell));
	                    	}
	                        break;
	                }
	                }

	                else if (i < middleligne && j >= middlecolumn) {
	                	int r = random.nextInt(14);
	                	switch (r) {
	                    case 0:
	                        room.addObject(new Lit(cell));
	                        break;
	                    case 2:
	                    	if(type == AnimalType.CHAT) {
	                        room.addObject(new Armoire(cell));}
	                    	else {
	                    	room.addObject(new Chaussures(cell));}
	                        break;
	                    case 4:
	                        room.addObject(new Panier(cell));
	                        break;
	                    case 6:
	                    	room.addObject(new Lampe(cell));
	                        break;
	                }
	                }

	                else if (i >= middleligne && j < middlecolumn) {
	                    if (i == AppConfiguration.LINE_COUNT - 1) {
	                        room.addObject(new Barriere(cell));
	                    }
	                    else {
	                        int r = random.nextInt(12);
	                        switch (r) {
	                        case 0:
	                            room.addObject(new Arbre(cell));
	                            break;
	                        case 2:
	                            room.addObject(new JouetExterieur(cell));
	                            break;
	                        case 4:
	                        	if(type == AnimalType.CHAT) {
	                            room.addObject(new Litiere(cell));
	                        	}
	                            break;
	                        case 6:
	                            room.addObject(new Plante(cell));
	                            break;
	                        }
	                        }
	                    }

	                else {
	                	int r = random.nextInt(24);
	                	switch (r) {
	                    case 0:
	                        room.addObject(new Poubelle(cell));
	                        break;
	                    case 2:
	                    	if(type == AnimalType.CHAT) {
	                    		room.addObject(new Gamelle(cell));}
	                    	else {
	                    		room.addObject(new Os(cell));
	                    	}
	                        break;
	                    case 4:
	                        room.addObject(new Refrigerateur(cell));
	                        break;
	                    case 6:
	                    		room.addObject(new Placard(cell));
	                        break;
	                    case 8:
	                        room.addObject(new TableManger(cell));
	                        break;
	                }
	                }
	                }
	            }
	            }
	        logger.info("Objets places dans la Room");
	        }

	public static Animal buildAnimal(Room room, AnimalType type, String nom) {
		logger.info("Creation animal : " + nom + " de type " + type);
	    Cell startCell = room.getCells()[0][0];
	    Animal animal = AnimalFactory.create(type, nom, startCell);
	    if (type == AnimalType.CHAT) {
	        animal.getProprete().increase(20);
	        logger.info("Proprete initiale du Chat fixee a 20");
	    }
	    return animal;
	}

	public static GameManager buildGameManager(GameState gameState, AnimalType type) {
	    double propreteInitiale = (type == AnimalType.CHAT) ? 20 : 0;
	    logger.info("Creation GameManager avec proprete initiale : " + propreteInitiale);
	    return new GameManager(gameState, propreteInitiale);
	}

	public static Maitre buildMaitre(Room room) {
		logger.info("Creation du Maitre au centre de la Room");
	    return new Maitre(
	        room.getCells()[AppConfiguration.LINE_COUNT / 2]
	                        [AppConfiguration.COLUMN_COUNT / 2]
	    );
	}
}