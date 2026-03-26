package engine.object.cuisine;

import engine.map.Cell;
import engine.map.RoomType;
import engine.mobile.Animal;
import engine.object.GameObject;

public class TableManger extends GameObject {
    public TableManger(Cell position){
        super("TableManger", position, RoomType.CUISINE);
    }

    public void interact(Animal animal, boolean isitgood) {
    	if(isitgood) {
    		animal.getDiscipline().increase(2);
    		animal.getProprete().increase(1);
    	}else {
    		animal.getDiscipline().decrease(2);
    	}
    }
}
