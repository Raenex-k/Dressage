package engine.object.chambre;

import engine.map.Cell;
import engine.map.RoomType;
import engine.mobile.Animal;
import engine.object.GameObject;

public class Chaussures extends GameObject {
    public Chaussures(Cell position){
        super("Chaussures", position, RoomType.CHAMBRE);
    }

    public void interact(Animal animal, boolean isitgood) {
    	if(isitgood) {
    		animal.getDiscipline().increase(3);
    	}else {
    		animal.getDiscipline().decrease(2);
    		animal.getObeissance().decrease(2);
    		animal.getObeissance().decrease(3);
    	}
    }
}
