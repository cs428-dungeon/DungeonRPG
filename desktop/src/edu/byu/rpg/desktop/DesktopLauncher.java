package edu.byu.rpg.desktop;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import edu.byu.rpg.RpgGame;

/**
 * This is the main desktop launcher for our game.  On construction, it creates an instance of our RpgGame class and
 * runs it.
 *
 * All the game's external configurations options (i.e. screen resolution) go in the constructor of this class.
 */
public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		// Set screen dimensions
		config.width = 1920;
		config.height = 1080;
		// config.fullscreen = true

        // Create our application using a new RpgGame instance, and attach it to the static Gdx.app object
        Gdx.app = new LwjglApplication(new RpgGame(), config);

        // Set the log level ("debug" for dev, "info" for testing, "none" for production)
        Gdx.app.setLogLevel(Application.LOG_NONE);
    }
}
