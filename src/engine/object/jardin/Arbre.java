package engine.object.jardin;

import engine.map.Cell;
import engine.map.RoomType;
import engine.mobile.Animal;
import engine.object.GameObject;

public class Arbre extends GameObject {
    public Arbre(Cell position){
        super("Arbre", position, RoomType.JARDIN);
    }

    public void interact(Animal animal, boolean isitgood) {
    	if(isitgood) {
    		animal.getDiscipline().increase(1);
    	}else {
    		animal.getDiscipline().decrease(1);
    	}
    }
}
