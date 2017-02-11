package edu.byu.rpg.audio;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Timer;
import edu.byu.rpg.tools.Assets;

/**
 * The audio manager for the game. All sound effect and music manipulation will be done through this class. Contains an {@link Assets} instance
 * used to retrieve music and sound effect files, a Music instance used to keep track of the background music, and floats used to manage system and music volume control
 */
public class AudioManager {

    /** The asset loader used to retrieve music and sound files */
    private Assets assets;
    /** The music currently playing in the background */
    private Music music;
    private static int DEFAULT_VOLUME = 50;
    /** The system volume */
    private float volume;
    /** The time in seconds that music will take to fade*/
    private float FADE_TIME = 3;
    /** The number of times per second music volume will be updated while fading*/
    private float FADE_RATE = 100;

    /**
     * Sets {@link AudioManager#assets} and {@link AudioManager#volume}.
     * @param assets The {@link Assets} instance to use.
     */
    public AudioManager(Assets assets) {
        this.assets = assets;
        setVolume(DEFAULT_VOLUME);
    }

    /**
     * Used to play a sound effect.
     * @param name The name of the sound file (without path or extension).
     */
    public void playSound(String name) {
        Sound sound = assets.getSound(name);
        sound.play(volume);
    }

    /**
     * Used to start playing background music.
     * @param name The name of the sound file (without path or extension).
     */
    public void startMusic(String name) {
        music = assets.getMusic(name);
        music.setLooping(true);
        music.setVolume(volume);
        music.play();
    }

    /**
     * Used to fade out the currently playing background music.
     */
    public void stopMusic() {
        final float volumeChange = volume / (FADE_TIME * FADE_RATE);
        final float changeInterval = 1 / FADE_RATE;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (music.getVolume() > volumeChange)
                    music.setVolume(music.getVolume() - volumeChange);
                else {
                    music.stop();
                    this.cancel();
                }
            }
        }, 0f, changeInterval);
    }

    /**
     * Used to change the system volume.
     * @param volume The new volume, from 0 to 100.
     */
    public void setVolume(int volume) {
        this.volume = (float)volume / 100f;
    }
}
