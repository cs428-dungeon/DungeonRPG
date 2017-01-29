package edu.byu.rpg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import edu.byu.rpg.screens.PlayScreen;
import edu.byu.rpg.tools.Assets;

/**
 * This is the main class for our game.  It contains the single `SpriteBatch` and `Assets` instances that will be used
 * throughout the rest of our project.
 */
public class RpgGame extends Game {

	public static int VIEW_WIDTH = 320;
	public static int VIEW_HEIGHT = 240;

	public Assets assets;
	public SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		assets = new Assets();
		// TODO: When we're ready to draw a loading/splash screen, this is where we'll do it.
		assets.load();

		// TODO: When we have a menu screen, we'll launch into that instead of going straight to PlayScreen
		setScreen(new PlayScreen(this));
	}
	
	@Override
	public void dispose () {
		assets.dispose();
		batch.dispose();
	}
}
