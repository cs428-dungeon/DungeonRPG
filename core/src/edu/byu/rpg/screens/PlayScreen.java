package edu.byu.rpg.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import edu.byu.rpg.RpgGame;

/**
 * This is the main game play screen for our game.
 */
public class PlayScreen implements Screen {

    // Local instance of game (for SpriteBatch ref)
    private final RpgGame game;

    // Core update and draw logic is handled by this engine
    private Engine engine;

    private OrthographicCamera camera;
    private FitViewport viewport;
    private OrthogonalTiledMapRenderer mapRenderer;

    public PlayScreen(final RpgGame game) {
        this.game = game;
        engine = new Engine();

        camera = new OrthographicCamera();
        viewport = new FitViewport(RpgGame.VIEW_WIDTH, RpgGame.VIEW_HEIGHT, camera);

        loadMap("0");

        // TODO: add any systems to the core game engine here
    }


    /**
     * This is where all of our core game engine logic and drawing happens.
     * @param delta Time since the last frame.
     */
    @Override
    public void render(float delta) {
        // reset screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // update camera
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        // set map renderer view and draw map
        mapRenderer.setView(camera);
        mapRenderer.render();

        // update core game engine
        game.batch.begin();
        engine.update(delta);
        game.batch.end();
    }

    // TODO: Refactor map loading/generating logic into its own class.
    /**
     * This function loads any objects from our tilemap into the game's world.
     * @param name The name of the map (without filepath or extension).
     */
    private void loadMap(String name) {
        TiledMap map = game.assets.getMap(name);
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        // TODO: Load all Tiled objects into the game world.
    }

    /**
     * This function is called automatically whenever the screen is resized.
     * @param width The new width of the game window.
     * @param height The new height of the game window.
     */
    @Override
    public void resize(int width, int height) {
        // reset the viewport
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
    }

    /**
     * This is where we dispose of any objects created in this class that need to be manually disposed of.  Basically,
     * if an object has a `dispose()` method, you should be calling it here.
     */
    @Override
    public void dispose() {

    }

    //////////////////////////////////////
    // Unimplemented overridden methods //
    //////////////////////////////////////

    @Override
    public void show() {
        // TODO: Need to so some research to understand difference between "show" and "resume"
    }

    @Override
    public void pause() {
        // TODO: Pause logic here for when game loses focus?
    }

    @Override
    public void resume() {
        // TODO: Resume logic here for when game window goes into focus?  Need to look into this.
    }

    @Override
    public void hide() {
        // TODO: Need to do some research to understand the difference between "pause" and "hide"
    }
}
