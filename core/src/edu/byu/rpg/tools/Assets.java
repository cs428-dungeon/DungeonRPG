package edu.byu.rpg.tools;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
        loadTexture("player/body_stand_down");
        loadTexture("player/body_walk_down");
        loadTexture("player/legs_stand_down");
        loadTexture("player/legs_walk_down");
        loadTexture("player/shadow");

        loadTexture("scarab");
        loadTexture("basic_bullet");

        loadTexture("effects/shadow_64");

        // load maps
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("maps/0.tmx", TiledMap.class);

        // load music
        loadMusic("opening");

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
     * Subroutine of {@link Assets#load()}, used for loading sound effects.
     * @param name The name of the OGG file (without path or extension).
     */
    public void loadSound(String name) {
        manager.setLoader(Sound.class, new SoundLoader(new InternalFileHandleResolver()));
        manager.load("audio/sound/" + name + ".ogg", Sound.class);
    }

    /**
     * Used to retrieve a pre-loaded OGG sound effect.
     * @param name The name of the sound file (without path or extension).
     * @return The pre-loaded sound.
     */
    public Sound getSound(String name) { return manager.get("audio/sound/" + name + ".ogg");}

    /**
     * Subroutine of {@link Assets#load()}, used for loading music.
     * @param name The name of the OGG file (without path or extension).
     */
    public void loadMusic(String name) {
        manager.setLoader(Music.class, new MusicLoader(new InternalFileHandleResolver()));
        manager.load("audio/music/" + name + ".ogg", Music.class);
    }

    /**
     * Used to retrieve pre-loaded OGG music.
     * @param name The name of the music file (without path or extension).
     * @return The pre-loaded music.
     */
    public Music getMusic(String name) { return manager.get("audio/music/" + name + ".ogg");}

    /**
     * This class disposes of all our loaded assets.  It should be called within the `dispose()` function of our
     * main game class, which is called automatically when the game window exits.
     */
    public void dispose() {
        manager.dispose();
    }
}
