package edu.byu.rpg.map;

import edu.byu.rpg.RpgGame;
import edu.byu.rpg.physics.World;
import edu.byu.rpg.screens.PlayScreen;

/**
 * Created by zlohner on 2/17/17.
 */
public class Room {

    private int level;
    private Coordinates coordinates;
    private PlayScreen playscreen;

    public Room(int level, Coordinates coordinates, PlayScreen playscreen) {
        this.level = level;
        this.coordinates = coordinates;
        this.playscreen = playscreen;
    }

    public static Room createRoom(int level, Coordinates coordinates, final RpgGame game) {
        PlayScreen screen = new PlayScreen(game);
        //TODO: load the correct map
        screen.loadMap("floor1/1");
        return new Room(level, coordinates, screen);
    }

    public void activate() {
        this.playscreen.activate();
    }

    public void deactivate() {
        this.playscreen.deactivate();
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public PlayScreen getPlayscreen() {
        return playscreen;
    }

    public void setPlayscreen(PlayScreen playscreen) {
        this.playscreen = playscreen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        if (level != room.level) return false;
        return coordinates != null ? coordinates.equals(room.coordinates) : room.coordinates == null;

    }

}
