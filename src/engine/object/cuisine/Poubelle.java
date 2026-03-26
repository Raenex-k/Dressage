package engine.object.cuisine;

import engine.map.Cell;
import engine.map.RoomType;
import engine.mobile.Animal;
import engine.object.GameObject;

public class Poubelle extends GameObject {
    public Poubelle(Cell position){
        super("Poubelle", position, RoomType.CUISINE);
    }

    public void interact(Animal animal, boolean isitgood) {
    	if(isitgood) {
    		animal.getDiscipline().increase(1);
    		animal.getProprete().increase(3);
    		animal.getObeissance().increase(1);
    	}else {
    		animal.getDiscipline().decrease(2);
    		animal.getProprete().decrease(4);
    	}
    }
}

