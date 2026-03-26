package engine.object.chambre;


import engine.map.Cell;
import engine.map.RoomType;
import engine.mobile.Animal;
import engine.object.GameObject;

public class Panier extends GameObject {
    public Panier(Cell position){
        super("Panier", position, RoomType.CHAMBRE);
    }

    public void interact(Animal animal, boolean isitgood) {
    	if(isitgood) {
    		animal.getDiscipline().increase(2);
    		animal.getProprete().increase(2);
    	}
    }
}
