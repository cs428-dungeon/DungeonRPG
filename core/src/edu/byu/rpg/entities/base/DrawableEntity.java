package edu.byu.rpg.entities.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import edu.byu.rpg.RpgGame;
import edu.byu.rpg.components.DrawComponent;

/**
 * This class is an extension of {@link UpdatableEntity}, So any object that implements this base class
 * will be plugged directly into {@link RpgGame#engine}'s update loop.  {@link DrawableEntity} objects,
 * in addition to being updated, will also be drawn at the end of each update loop.
 */
public abstract class DrawableEntity extends UpdatableEntity {

    /**
     * Stored instance of {@link DrawComponent}, so that {@link DrawComponent#zIndex} can be updated dynamically.
     */
    protected DrawComponent drawComponent;

    /**
     * Automatically registers this entity with {@link RpgGame#engine}, and also adds a {@link DrawComponent}
     * to this entity.
     * @param game Our main game class.
     */
    public DrawableEntity(RpgGame game) {
        super(game);
        drawComponent = new DrawComponent(this);
        add(drawComponent);
    }

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
    public abstract void draw(float delta, SpriteBatch batch);
}
