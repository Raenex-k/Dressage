package engine.strategy;

import engine.mobile.Animal;
import engine.mobile.Maitre;
import engine.map.Cell;

public class PunitionRecompenseManager {

    private PunitionRecompenseType actionCourante;

    public PunitionRecompenseManager() {
        this.actionCourante = null;
    }

    // Déterminer punition ou récompense et appliquer l'effet
    public void calculerAction(PunitionRecompenseType type, Maitre maitre, Animal animal) {
        if (type == null) return;

        actionCourante = type;

        switch (type) {

            case DIRE_STOP:
            case REPRIMANDE:
            case ENCOURAGER:
                // le maître reste immobile
                // seule l'image ou l'animation change
                break;

            case FRAPPER:
            case CARESSER:
                // le maître se déplace vers l'animal
                Cell posAnimal = animal.getPosition();
                maitre.setPosition(posAnimal);
                break;
        }
    }

    public PunitionRecompenseType getActionCourante() {
        return actionCourante;
    }

    // Reset après animation
    public void reset(Maitre maitre, Cell positionInitiale) {
        actionCourante = null;
        maitre.setPosition(positionInitiale);
    }

    public void setAction(PunitionRecompenseType type) {
        this.actionCourante = type;
    }

    public void clearAction() {
        this.actionCourante = null;
    }
}