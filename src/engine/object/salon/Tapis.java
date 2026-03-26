package engine.object.salon;


import engine.map.Cell;
import engine.map.RoomType;
import engine.mobile.Animal;
import engine.object.GameObject;


public class Tapis extends GameObject {
    public Tapis(Cell position){
        super("Tapis", position, RoomType.SALON);
    }

    public void interact(Animal animal, boolean isitgood) {
    	if(isitgood) {
    		animal.getDiscipline().increase(1);
    		animal.getProprete().increase(1);
    	}
    }
}
