package edu.byu.rpg.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import edu.byu.rpg.RpgGame;

import java.util.HashMap;

/**
 * Controller for a set of animations.  Represents a single sprite with different animation states.
 */
public class AnimationManager {

    /** Local instance of {@link RpgGame} for access to {@link RpgGame#batch} */
    private final RpgGame game;

    /** Map of animation objects. */
    private HashMap<String, Animation<TextureRegion>> animations;
    /** The current animation's asset key (filename without path or ext). */
    private String currentAnimation;

    /** Current animation time elapsed. */
    private float totalTime;
    /** Flag for whether or not current animation should loop. */
    private boolean loop;

    /** Whether or not the animation should face right or be flipped. */
    private boolean faceRight;

    /** Sets local instance of {@link RpgGame} and resets the animations map. */
    public AnimationManager(final RpgGame game) {
        this.game = game;
    }

    /**
     * Parses a spritesheet into an animation and adds it to {@link AnimationManager#animations}
     * @param key The key of the img resource (the filename without path and ext).
     * @param rows How many rows make up the spritesheet.
     * @param cols How many columns make up the spritesheet.
     * @param fps The frames per second for this animation.
     */
    public void add(String key, int rows, int cols, int fps) {
        // get the texture
        Texture texture = game.assets.getTexture(key);
        if (texture == null) {
            Gdx.app.log(this.getClass().getSimpleName(),
                    "Warning!  There was no texture found at: " + key +
                            ".  Animation was not added.");
            return;
        }

        // split texture into 2D array
        int width = texture.getWidth() / cols;
        int height = texture.getHeight() / rows;
        TextureRegion[][] tmp = TextureRegion.split(texture, width, height);

        // convert 2d to 1d array
        TextureRegion[] frames = new TextureRegion[cols * rows];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                frames[index++] = tmp[i][j];
            }
        }

        // create animation object
        Animation<TextureRegion> animation = new Animation<TextureRegion>(1f/fps, frames);

        // add to animation map
        animations.put(key, animation);
    }

    /**
     * Plays the animation specified by <tt>key</tt>.  If that animation is already playing, nothing happens.
     * @param key The key of the img resource (the filename without path and ext).
     * @param loop <tt>true</tt> if this animation should loop, <tt>false</tt> if it should stop animating on the
     *             last frame.
     */
    public void play(String key, boolean loop) {
        // initial set (first time play() is called)
        if (currentAnimation == null) {
            currentAnimation = key;
            totalTime = 0;
            this.loop = loop;
            return;
        }

        // set "current animation" key only if current animation is not already playing
        if (!currentAnimation.equals(key)) {
            // set animation key to new animation
            currentAnimation = key;
            // reset totalTime
            totalTime = 0;
            // set looping
            this.loop = loop;
        }
    }

    /**
     * Draws the current animation of this {@link AnimationManager}.  This method is not called automatically, so you'll
     * need to call it within another object's draw function.
     * @param deltaTime The time since the last frame.
     * @param x The x-position to draw the current animation at.
     * @param y The y-position to draw the current animation at.
     */
    public void draw(float deltaTime, int x, int y) {
        // get current animation and update its frame time
        Animation<TextureRegion> animation = animations.get(currentAnimation);
        if (animation == null) {
            Gdx.app.debug(this.getClass().getSimpleName(),
                    "Warning!  There is no animation called: " + currentAnimation + ".  Nothing drawn.");
            return;
        }
        totalTime += deltaTime;

        // get current frame
        TextureRegion frame = animation.getKeyFrame(totalTime, loop);

        // draw animation
        float modX = faceRight ? x : x + frame.getRegionWidth() - 2;
        float xScale = faceRight ? 1 : -1;
        game.batch.draw(frame, modX, y, 0, 0, frame.getRegionWidth(), frame.getRegionHeight(), xScale, 1, 0);
    }

    /**
     * Tells whether the current animation has completed playing.
     * @return <tt>true</tt> if finished, else <tt>false</tt>.
     */
    public boolean isFinished() {
        try {
            return animations.get(currentAnimation).isAnimationFinished(totalTime);
        } catch (NullPointerException e) {
            Gdx.app.log(this.getClass().getSimpleName(),
                    "There is no animation named " + currentAnimation + ".");
            return false;
        }
    }

    /**
     * Flips the animation to the left.
     */
    public void faceLeft() {
        faceRight = false;
    }

    /**
     * Flips the animation to the right.
     */
    public void faceRight() {
        faceRight = true;
    }

    /**
     * Clears all animations from this manager, and resets the time and {@link AnimationManager#faceRight}
     */
    public void reset() {
        animations = new HashMap<String, Animation<TextureRegion>>();
        totalTime = 0;
        faceRight = true;
    }
}
