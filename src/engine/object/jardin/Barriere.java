package engine.object.jardin;

import engine.map.Cell;
import engine.map.RoomType;
import engine.mobile.Animal;
import engine.object.GameObject;

public class Barriere extends GameObject {
    public Barriere(Cell position){
        super("Barriere", position, RoomType.JARDIN);
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
