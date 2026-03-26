package engine.mobile;

import engine.map.Cell;

public class AnimalFactory {
	public static Animal create(AnimalType type, String name, Cell pos){

        switch(type){
            case CHAT: return new Chat(name,pos);
            case CHIEN: return new Chien(name,pos);
            default: throw new IllegalArgumentException("Type inconnu");
        }
    }
}
