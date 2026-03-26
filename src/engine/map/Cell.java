package engine.map;

import engine.object.GameObject;

public class Cell {

    private final int line;
    private final int column;
    private RoomType roomType;
    private GameObject object;  // l'objet qui se trouve sur cette cellule

    public Cell(int line, int column, RoomType roomType) {
        this.line = line;
        this.column = column;
        this.roomType = roomType;
        this.object = null; // pas d'objet au départ
    }

    public int getLine() { return line; }
    public int getColumn() { return column; }
    public RoomType getRoomType() { return roomType; }

    public GameObject getObject() { return object; }
    public void setObject(GameObject object) { this.object = object; }
}