package edu.byu.rpg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import edu.byu.rpg.RpgGame;
import edu.byu.rpg.entities.ActorStaging;
import edu.byu.rpg.entities.base.Solid;
import edu.byu.rpg.physics.Body;
import edu.byu.rpg.physics.World;

/**
 * This is the main game play screen for our game.
 */
public class PlayScreen extends ScreenBase {

    /** Physics world, which contains all the collide-able actors in this level. */
    private World world;

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
        world = new World();
        TiledMap map = game.assets.getMap(name);
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        // TODO: Load all Tiled objects into the game world.
        // TODO: Remove ActorStaging object when done testing engine loop.
        new ActorStaging(game, world, 32, 32);

        try {
            // load solid level geometry
            for (MapObject rectMapObj : map.getLayers().get("solids").getObjects().getByType(RectangleMapObject.class)) {
                // get rectangle and make solid level geometry out of it
                Rectangle rect = ((RectangleMapObject) rectMapObj).getRectangle();
                new Solid(world, new Body((int) rect.x, (int) rect.y, (int) rect.width, (int) rect.height));
            }
        } catch (NullPointerException e) {
            Gdx.app.debug(this.getClass().getSimpleName(), e.getMessage());
        }
    }
}