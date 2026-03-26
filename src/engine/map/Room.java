package engine.map;

import engine.object.GameObject;

public class Room {

    private int lines;
    private int columns;
    private Cell[][] cells;

    public Room(int lines, int columns) {
        this.lines = lines;
        this.columns = columns;
        this.cells = new Cell[lines][columns];

        initGrid();
    }

    private void initGrid() {
        for(int i=0;i<lines;i++){
            for(int j=0;j<columns;j++){
                RoomType type;
                if(i < lines/2 && j < columns/2)
                    type = RoomType.SALON;
                else if(i < lines/2 && j >= columns/2)
                    type = RoomType.CUISINE;
                else if(i >= lines/2 && j < columns/2)
                    type = RoomType.JARDIN;
                else
                    type = RoomType.CHAMBRE;

                cells[i][j] = new Cell(i,j,type);
            }
        }
    }

    public Cell[][] getCells(){
        return cells;
    }

    // Place un objet sur sa cellule
    public void addObject(GameObject obj){
        Cell pos = obj.getPosition();
        cells[pos.getLine()][pos.getColumn()].setObject(obj);
    }
    

}