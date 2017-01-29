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
     * This method is called once at the beginning of the game to load all assets.  (Loading screen?)
     */
    public void load() {
        // load all textures
        loadTexture("player_stand");

        // load maps
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("maps/0.tmx", TiledMap.class);
    }

    /**
     * Subroutine of `load()`, used for loading images.
     * @param name The name of the image file (without path or extension).
     */
    public void loadTexture(String name) {
        manager.load("img/" + name + ".png", Texture.class);
    }

    /**
     * Used to retrieve a pre-loaded Texture.
     * @param name The name of the image file (without path or extension).
     */
    public Texture getTexture(String name) {
        return manager.get("img/" + name + ".png", Texture.class);
    }


}
