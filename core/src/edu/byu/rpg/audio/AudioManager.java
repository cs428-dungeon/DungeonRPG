package edu.byu.rpg.audio;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Timer;
import edu.byu.rpg.tools.Assets;

import java.util.HashSet;

/**
 * All sound effect and music manipulation will be done through this class.
 * Contains an {@link Assets} instance used to retrieve music and sound effect
 * files, a number of Music instances used to keep track of the background music
 * and sound effects, and several floats used to manage volume control
 */
public class AudioManager {

    /** The Assets instance to use */
    private Assets assets;
    /** The music currently playing in the background */
    private Music music;
    /** All currently playing sound effects */
    private HashSet<Music> sounds;
    /** The default volume */
    private float DEFAULT_VOLUME = 50;
    /** The current volume */
    private float volume;
    /** The time in seconds that music will take to fade */
    private float FADE_TIME = 3;
    /** The number of times per second that music volume will be updated while fading */
    private float FADE_RATE = 100;

    /**
     * Sets {@link AudioManager#assets} and {@link AudioManager#volume}.
     * @param assets The {@link Assets} instance to use
     */
    public AudioManager(Assets assets) {
        this.assets = assets;
        setVolume(DEFAULT_VOLUME);
        sounds = new HashSet<Music>();
    }

    /**
     * Used to play a sound effect.
     * @param name The name of the sound file (without path or extension)
     */
    public void playSound(String name) {
        Music sound = assets.getSound(name);
        if(!sounds.contains(sound)) {
            sound.setVolume(volume);
            sounds.add(sound);
            sound.setOnCompletionListener(new Music.OnCompletionListener() {
                @Override
                public void onCompletion(Music sound) {
                    sounds.remove(sound);
                }
            });
            sound.play();
        }
    }

    /**
     * Used to start playing background music.
     * @param name The name of the music file (without path or extension)
     * @param looping Whether or not to loop the music
     */
    public void playMusic(String name, boolean looping) {
        if(music == null) {
            music = assets.getMusic(name);
            music.setLooping(looping);
            music.setVolume(volume);
            music.setOnCompletionListener(new Music.OnCompletionListener() {
                @Override
                public void onCompletion(Music m) {
                    if(!m.isLooping()) {
                        music = null;
                    }
                }
            });
            music.play();
        }
    }

    /**
     * Used to fade out the currently playing background music.
     * @param fadeTime The amount of time it takes for the music to fade out.
     * @param fadeRate How smoothly the music will fade out.
     */
    public void stopMusic(float fadeTime, float fadeRate) {
        if(music != null) {
            final float volumeChange = volume / (fadeTime * fadeRate);
            final float changeInterval = 1 / fadeRate;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    if (music.getVolume() > volumeChange)
                        music.setVolume(music.getVolume() - volumeChange);
                    else {
                        music.stop();
                        music = null;
                        this.cancel();
                    }
                }
            }, 0f, changeInterval);
        }
    }

    /** A wrapper for calling stopMusic with the default fadeTime and fadeRate */
    public void stopMusic() {
        stopMusic(FADE_TIME, FADE_RATE);
    }

    /**
     * Used to change the volume.
     * @param volume The new volume, from 0 to 100.
     */
    public void setVolume(float volume) {
        this.volume = volume / 100f;
    }
}
