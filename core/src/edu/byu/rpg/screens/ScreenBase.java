package edu.byu.rpg.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import edu.byu.rpg.RpgGame;
import edu.byu.rpg.systems.DestroySystem;
import edu.byu.rpg.systems.DrawSystem;
import edu.byu.rpg.systems.UpdateSystem;

/**
 * The base class for all of our screens.
 */
public abstract class ScreenBase implements Screen {

    /** Local instance of game (for access to the game's {@link RpgGame#assets}, {@link RpgGame#batch}, {@link RpgGame#engine}) **/
    protected RpgGame game;

    /** Local instance of {@link DrawSystem} (so that we can force-sort the entities */
    private DrawSystem drawSystem;

    /** The 2D camera. */
    protected OrthographicCamera camera;
    /** The 320x240 viewport */
    protected FitViewport viewport;

    /**
     * This object's constructor re-initializes the {@link RpgGame#engine}, which clears all entities any time we switch
     * screens. You should load any entities to the game's engine after calling this constructor. This function also
     * initializes {@link ScreenBase#camera} and {@link ScreenBase#viewport}.
     *
     * @param game Our main game class.
     */
    public ScreenBase(RpgGame game) {
        this.game = game;
        game.engine = new Engine();

        game.engine.addSystem(new UpdateSystem());
        drawSystem = new DrawSystem(game);
        game.engine.addSystem(drawSystem);
        game.engine.addSystem(new DestroySystem(game));

        camera = new OrthographicCamera();
        viewport = new FitViewport(RpgGame.VIRTUAL_WIDTH, RpgGame.VIRTUAL_HEIGHT, camera);
    }

    /**
     * This is where all of our core game engine logic and drawing should happen.  By default,
     * it simply clears the screen to black and sorts {@link ScreenBase#drawSystem}'s elements.
     * @param delta Time since last frame.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        drawSystem.forceSort();
    }

    /**
     * This function is automatically called whenever the screen is resized.  It resets the viewport to fill the
     * game window.
     * @param width The new width of the game window.
     * @param height The new height of the game window.
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    /**
     * This is where you should dispose of any objects created for a screen that need to be manually
     * disposed of.  Basically, if your screen is using any LibGDX object has a dispose method, you should override
     * this method and then call that object's dispose method here.
     */
    @Override
    public void dispose() {}

    /* Unimplemented overridden methods */

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
