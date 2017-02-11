package edu.byu.rpg;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import edu.byu.rpg.audio.AudioManager;
import edu.byu.rpg.screens.PlayScreen;
import edu.byu.rpg.tools.Assets;

/**
 * This is the main class for our game.  It contains the single {@link SpriteBatch}, {@link Engine}, and
 * {@link Assets} instances that will be used throughout the rest of our project.
 */
public class RpgGame extends Game {

	/** The viewport width for our game. */
	public static int VIRTUAL_WIDTH = 320;
	/** The viewport height for our game. */
	public static int VIRTUAL_HEIGHT = 240;

	/** The single asset loader that will be used to load all assets, and retrieve them throughout the game */
	public Assets assets;

	/** The single audio manager that will be used to manipulate all audio usage throughout the game */
	public AudioManager audioManager;

	/** The single sprite batch that will be used to draw all throughout the game.*/
	public SpriteBatch batch;

	/** The single {@link com.badlogic.ashley.core.Engine} instance that will be used for core game logic all throughout the game.*/
	public Engine engine;

	/** Initializes {@link RpgGame#batch}, {@link RpgGame#engine} and {@link RpgGame#assets}*/
	@Override
	public void create () {
		batch = new SpriteBatch();
		assets = new Assets();
		// TODO: When we're ready to draw a loading/splash screen, this is where we'll do it.
		assets.load();
		audioManager = new AudioManager(assets);
		// TODO: When we have a menu screen, we'll launch into that instead of going straight to PlayScreen
		audioManager.startMusic("opening"); // only here for testing
		setScreen(new PlayScreen(this));
		audioManager.stopMusic(); // only here for testing
	}

	/**
	 * Disposes of {@link RpgGame#assets} and {@link RpgGame#batch} to prevent memory leaks.  This is called
	 * automatically by LibGDX.
	 */
	@Override
	public void dispose () {
		assets.dispose();
		batch.dispose();
	}
}
