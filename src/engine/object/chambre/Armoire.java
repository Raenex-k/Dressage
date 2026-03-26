package engine.object.chambre;

import engine.map.Cell;
import engine.map.RoomType;
import engine.mobile.Animal;
import engine.object.GameObject;

public class Armoire extends GameObject {
    public Armoire(Cell position){
        super("Armoire", position, RoomType.CHAMBRE);
    }

}
