package engine.object.cuisine;

import engine.map.Cell;
import engine.map.RoomType;
import engine.mobile.Animal;
import engine.object.GameObject;

public class Placard extends GameObject {
    public Placard(Cell position){
        super("Placard", position, RoomType.CUISINE);
    }

    public void interact(Animal animal, boolean isitgood) {
    	if(isitgood) {
    		animal.getDiscipline().increase(2);
    		animal.getProprete().increase(1);
    	}else {
    		animal.getDiscipline().decrease(3);
    		animal.getProprete().decrease(1);
    	}
    }
    
}
