package engine.object.jardin;

import engine.map.Cell;
import engine.map.RoomType;
import engine.mobile.Animal;
import engine.object.GameObject;

public class JouetExterieur extends GameObject {
    public JouetExterieur(Cell position){
        super("JouetExterieur", position, RoomType.JARDIN);
    }

    public void interact(Animal animal, boolean isitgood) {
    	if(isitgood) {
    		animal.getDiscipline().increase(2);
    		animal.getProprete().increase(1);
    		animal.getObeissance().increase(1);
    	}
    }
}
