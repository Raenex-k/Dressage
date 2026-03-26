package engine.object.jardin;


import engine.map.Cell;
import engine.map.RoomType;
import engine.mobile.Animal;
import engine.object.GameObject;


public class Plante extends GameObject{
	 public Plante(Cell position){
	        super("Plante", position, RoomType.JARDIN);
	    }

	    public void interact(Animal animal, boolean isitgood) {
	    	if(isitgood) {
	    		animal.getDiscipline().increase(1);
	    		animal.getProprete().increase(4);
	    		animal.getObeissance().increase(1);
	    	}else {
	    		animal.getDiscipline().decrease(2);
	    		animal.getProprete().decrease(3);
	    	}
	    }
}
