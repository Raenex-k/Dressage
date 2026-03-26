package engine.object;

import engine.map.Cell;
import engine.map.RoomType;
import engine.mobile.Animal;

public abstract class GameObject {

    protected String name;
    protected Cell position;
    protected RoomType allowedRoom;

    public GameObject(String name, Cell position, RoomType allowedRoom){
        this.name = name;
        this.position = position;
        this.allowedRoom = allowedRoom;
    }

    public Cell getPosition(){ return position; }
    public String getName(){ return name; }

    public boolean isAllowedInRoom(RoomType type){
        return allowedRoom == type;
    }

}