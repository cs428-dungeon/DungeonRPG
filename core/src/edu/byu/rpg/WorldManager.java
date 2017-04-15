package edu.byu.rpg;

import edu.byu.rpg.entities.player.Player;
import edu.byu.rpg.map.Coordinates;
import edu.byu.rpg.map.Room;
import edu.byu.rpg.map.WorldMap;

/**
 * Created by zlohner on 2/27/17.
 */
public class WorldManager {

    public final int LOAD_RADIUS = 3;

    private final RpgGame game;
    private GameState gameState;
    private WorldMap worldMap;
    private Coordinates currentCoordinates;
    private Room currentRoom;
    private Player player;
    private int currentLevel;

    public WorldManager(final RpgGame game) {
        this.game = game;
        this.worldMap = null;
        this.currentRoom = null;
        this.gameState = GameState.MENU_STATE;
    }

    public void startGame(int level) {
        this.currentLevel = level;
        this.gameState = GameState.PLAY_STATE;
        this.initializeFloorMap(level);
        this.enterRoom(this.currentCoordinates);
    }

    public void initializeFloorMap(int level) {
        this.worldMap = new WorldMap();
        this.currentCoordinates = new Coordinates(0, 0);
        this.currentRoom = this.loadRoom(new Coordinates(0, 0));
        this.loadRoomsInRadius(this.LOAD_RADIUS);
    }

    public void loadRoomsInRadius(int radius) {
        for (int X = -radius; X <= radius; X++) {
            for (int Y = -radius; Y <= radius; Y++) {
                if (Math.abs(Math.abs(X) + Math.abs(Y)) <= radius) {
                    loadRoom(
                        new Coordinates(
                            this.currentRoom.getCoordinates().X() + X,
                            this.currentRoom.getCoordinates().Y() + Y
                        )
                    );
                }
            }
        }
    }

    public void enterRoom(Coordinates coordinates) {
        this.currentRoom.deactivate();
        this.currentRoom = this.loadRoom(coordinates);
        this.currentRoom.activate();
        this.loadRoomsInRadius(this.LOAD_RADIUS);
        this.game.setScreen(currentRoom.getPlayscreen());
    }

    public Room loadRoom(Coordinates coordinates) {
        if (!this.worldMap.coordinateLoaded(coordinates)) {
            this.worldMap.addRoom(coordinates, Room.createRoom(this.currentLevel, coordinates, this.game));
        }
        return this.worldMap.getRoom(coordinates);
    }

}
