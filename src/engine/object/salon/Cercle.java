package engine.object.salon;

import engine.map.Cell;
import engine.map.RoomType;
import engine.mobile.Animal;
import engine.object.GameObject;

public class Cercle extends GameObject {
    public Cercle(Cell position){
        super("Cercle", position, RoomType.SALON);
    }

    public void interact(Animal animal, boolean isitgood) {
    	if(isitgood) {
    		animal.getDiscipline().increase(2);
    		animal.getProprete().increase(1);
    	}
    }
}
