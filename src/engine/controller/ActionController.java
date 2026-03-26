package engine.controller;

import java.util.Random;

import config.AppConfiguration;
import engine.map.Room;
import engine.map.RoomType;

public class ActionController {

    private Room room;
    private Random random = new Random();

    public ActionController(Room room) { this.room = room; }

    public RoomType choisirTypeCellulePourAction() {
        int r = random.nextInt(100);
        RoomType roomType;

        if (r < AppConfiguration.SALON_PROB) roomType = RoomType.SALON;
        else if (r < AppConfiguration.SALON_PROB + AppConfiguration.CUISINE_PROB) roomType = RoomType.CUISINE;
        else if (r < AppConfiguration.SALON_PROB + AppConfiguration.CUISINE_PROB + AppConfiguration.JARDIN_PROB) roomType = RoomType.JARDIN;
       else roomType = RoomType.CHAMBRE;

        return roomType;
    }

    // Exemple simple d'action "bonne" ou "mauvaise"
    public boolean choisirSiActionBonne() {
        return random.nextBoolean(); // 50% chance bonne ou mauvaise
    }
}