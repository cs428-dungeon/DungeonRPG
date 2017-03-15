package edu.byu.rpg;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Audio;
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
	public static int VIRTUAL_WIDTH = 640;
	/** The viewport height for our game. */
	public static int VIRTUAL_HEIGHT = 480;

	/** The single asset loader that will be used to load all assets, and retrieve them throughout the game */
	public Assets assets;

	/** The single audio manager that will be used to play and manage any and all audio*/
	public AudioManager audio;

	/** The single sprite batch that will be used to draw all throughout the game.*/
	public SpriteBatch batch;

	/** The single {@link com.badlogic.ashley.core.Engine} instance that will be used for core game logic all throughout the game.*/
	public Engine engine;

	/** Initializes {@link RpgGame#batch}, {@link RpgGame#engine} and {@link RpgGame#assets}*/
	@Override
	public void create () {
		batch = new SpriteBatch();
		assets = new Assets();
		audio = new AudioManager(assets);
		// TODO: When we're ready to draw a loading/splash screen, this is where we'll do it.
		assets.load();
		// TODO: When we have a menu screen, we'll launch into that instead of going straight to PlayScreen
		setScreen(new PlayScreen(this));
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
