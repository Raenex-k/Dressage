package engine.object.cuisine;

import engine.map.Cell;
import engine.map.RoomType;
import engine.mobile.Animal;
import engine.object.GameObject;

public class Gamelle extends GameObject {
    public Gamelle(Cell position){
        super("Gamelle", position, RoomType.CUISINE);
    }

    public void interact(Animal animal, boolean isitgood) {
    	if(isitgood) {
    		animal.getProprete().increase(3);
    		animal.getDiscipline().increase(2);
    		animal.getObeissance().increase(1);
    	}
    }
 }

