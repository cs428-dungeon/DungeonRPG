package edu.byu.rpg.entities.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import edu.byu.rpg.RpgGame;

/**
 * Objects that implement Drawable can be acted on by {@link edu.byu.rpg.components.DrawComponent}
 */
public interface Drawable {
    /**
     * This is the draw function for this entity.  It will be called automatically on every iteration
     * of {@link RpgGame#engine}'s core update loop.
     *
     * Don't worry about calling {@link SpriteBatch#begin()} or {@link SpriteBatch#end()}, because
     * those functions should both be called within {@link edu.byu.rpg.screens.ScreenBase#render(float)}
     * before and after {@link RpgGame#engine} is updated.
     * @param delta The time since last frame.
     * @param batch The {@link SpriteBatch} used to draw ({@link RpgGame#batch}, automatically passed
     *              to this entity by {@link edu.byu.rpg.systems.DrawSystem}).
     */
    void draw(float delta, SpriteBatch batch);
}
