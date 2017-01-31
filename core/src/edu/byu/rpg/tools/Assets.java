package edu.byu.rpg.tools;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * This is the main asset loader for our game.  Textures, maps, fonts, and audio will all be loaded here once,
 * and can be retrieved throughout the game.
 */
public class Assets {

    // local instance of asset manager
    public AssetManager manager;

    public Assets() {
        manager = new AssetManager();
    }

    /**
     * This method is called once at the beginning of the game to load all assets.  It will block program execution
     * until all assets have finished loading, so be sure to draw a loading/splash screen before calling this method.
     */
    public void load() {
        // load all textures
        loadTexture("player_stand");
        loadTexture("shadow_32");

        // load maps
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("maps/0.tmx", TiledMap.class);

        // Block any further logic until the manager has finished loading.
        manager.finishLoading();
    }

    /**
     * Subroutine of {@link Assets#load()}, used for loading images.
     * @param name The name of the PNG file (without path or extension).
     */
    public void loadTexture(String name) {
        manager.load("img/" + name + ".png", Texture.class);
    }

    /**
     * Used to retrieve a pre-loaded Texture.
     * @param name The name of the image file (without path or extension).
     * @return The pre-loaded Texture.
     */
    public Texture getTexture(String name) {
        return manager.get("img/" + name + ".png", Texture.class);
    }

    /**
     * Subroutine of {@link Assets#load()}, used for loading Tiled maps.
     * @param name The name of the TMX file (without path or extension).
     */
    public void loadMap(String name) {
        manager.load("maps/" + name + ".tmx", TiledMap.class);
    }

    /**
     * Used to retrieve a pre-loaded TMX map.
     * @param name The name of the map file (without path or extension).
     * @return The pre-loaded map.
     */
    public TiledMap getMap(String name) {
        return manager.get("maps/" + name + ".tmx");
    }

    /**
     * This class disposes of all our loaded assets.  It should be called within the `dispose()` function of our
     * main game class, which is called automatically when the game window exits.
     */
    public void dispose() {
        manager.dispose();
    }
}
