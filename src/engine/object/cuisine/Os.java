package engine.object.cuisine;

import engine.map.Cell;
import engine.map.RoomType;
import engine.mobile.Animal;
import engine.object.GameObject;

public class Os extends GameObject {
    public Os(Cell position){
        super("Os", position, RoomType.CUISINE);
    }

    public void interact(Animal animal, boolean isitgood) {
    	if(isitgood) {
    		animal.getProprete().increase(3);
    		animal.getDiscipline().increase(2);
    		animal.getObeissance().increase(1);
    	}
    }
 }

