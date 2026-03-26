package engine.object.cuisine;

import engine.map.Cell;
import engine.map.RoomType;
import engine.mobile.Animal;
import engine.object.GameObject;

public class Refrigerateur extends GameObject {
    public Refrigerateur(Cell position){
        super("Refrigerateur", position, RoomType.CUISINE);
    }

    public void interact(Animal animal, boolean isitgood) {
    	if(isitgood) {
    		animal.getDiscipline().increase(2);
    	}else {
    		animal.getDiscipline().decrease(2);
    	}
    }
}
