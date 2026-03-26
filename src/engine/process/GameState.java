package engine.process;

import engine.map.Room;
import engine.mobile.Animal;
import engine.mobile.Maitre;

public class GameState {

    private Room room;
    private Animal animal;
    private Maitre maitre;

    public GameState(Room room, Animal animal, Maitre maitre){
        this.room = room;
        this.animal = animal;
        this.maitre = maitre;
    }

    public Room getRoom(){ return room; }
    public Animal getAnimal(){ return animal; }
    public Maitre getMaitre(){ return maitre; }
    
    public void setRoom(Room room) {
        this.room = room;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public void setMaitre(Maitre maitre) {
        this.maitre = maitre;
    }
    
}