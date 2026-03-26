package engine.object.chambre;

import engine.map.Cell;
import engine.map.RoomType;
import engine.mobile.Animal;
import engine.object.GameObject;

public class Lampe extends GameObject {
    public Lampe(Cell position){
        super("Lampe", position, RoomType.CHAMBRE);
    }

    
    public void interact(Animal animal , boolean isitgood){
    	if(isitgood) {
    		animal.getDiscipline().increase(2);
    		animal.getProprete().increase(1);
    	}else {
    		animal.getDiscipline().decrease(3);
    		animal.getProprete().decrease(1);
    	}
    }
}
