package edu.byu.rpg.map;

import java.util.HashMap;

/**
 * Created by zlohner on 2/17/17.
 */
public class WorldMap {

    private HashMap<Coordinates, Room> rooms;

    public WorldMap() {
        rooms = new HashMap<Coordinates, Room>();
    }

    public Room getRoom(Coordinates coordinates) {
        return rooms.get(coordinates);
    }

    public void addRoom(Coordinates coordinates, Room room) {
        rooms.put(coordinates, room);
    }

    public boolean coordinateLoaded(Coordinates coordinates) {
        return rooms.containsKey(coordinates);
    }
}
