package edu.byu.rpg.screens;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import edu.byu.rpg.RpgGame;
import edu.byu.rpg.entities.EntityStaging;

/**
 * This is the main game play screen for our game.
 */
public class PlayScreen extends ScreenBase {

    /** Automatically draws any tiles (not objects) from our Tiled map to the screen. */
    private OrthogonalTiledMapRenderer mapRenderer;

    /**
     * Loads the player into the first room of the dungeon.
     *
     * @param game Our main game class.
     */
    public PlayScreen(final RpgGame game) {
        super(game);
        loadMap("0");
    }

    /**
     * In addition to {@linkplain ScreenBase#render(float) clearing the screen}, this function
     * updates our camera, renders our Tiled map to the screen, and updates/draws all entities
     * registered to {@link RpgGame#engine}.
     *
     * @param delta Time since the last frame.
     */
    @Override
    public void render(float delta) {
        super.render(delta);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        mapRenderer.setView(camera);
        mapRenderer.render();

        game.batch.begin();
        game.engine.update(delta);
        game.batch.end();
    }

    // TODO: Refactor map loading/generating logic into its own class.

    /**
     * This function loads any objects from our Tiled map into the game's world.
     *
     * @param name The name of the map (without filepath or extension).
     */
    private void loadMap(String name) {
        TiledMap map = game.assets.getMap(name);
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        // TODO: Load all Tiled objects into the game world.
        // TODO: Remove EntityStaging object when done testing engine loop.
        new EntityStaging(game);
    }
}