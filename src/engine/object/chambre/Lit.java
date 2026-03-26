package engine.object.chambre;

import engine.map.Cell;
import engine.map.RoomType;
import engine.mobile.Animal;
import engine.object.GameObject;

public class Lit extends GameObject {
    public Lit(Cell position){
        super("Lit", position, RoomType.CHAMBRE);
    }

    public void interact(Animal animal, boolean isitgood) {
    	if(isitgood) {
    		animal.getDiscipline().increase(1);
    	}else {
    		animal.getDiscipline().decrease(2);
    	}
    }
    
}
