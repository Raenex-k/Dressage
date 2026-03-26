package engine.mobile;

import engine.map.Cell;

public class Maitre {

    private Cell position;

    public Maitre(Cell start){
        this.position = start;
    }

    public Cell getPosition(){ return position; }
    public void setPosition(Cell c) {
    	position = c;
    }

    public void deplacerVersAnimal(Animal animal){
        this.position = animal.getPosition(); // simplification
    }

    public String frapper(){
        return "Ne fais plus ça!";
    }

    public String direNonStop(){
        return"Non ! Stop !";
    }

    public String direPunition(){
        return "Tu es puni !";
    }
}