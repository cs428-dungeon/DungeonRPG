package edu.byu.rpg.entities.base;

import edu.byu.rpg.RpgGame;
import edu.byu.rpg.components.DrawComponent;

/**
 * This class is an extension of {@link UpdatableEntity}, So any object that implements this base class
 * will be plugged directly into {@link RpgGame#engine}'s update loop.  {@link DrawableEntity} objects,
 * in addition to being updated, will also be drawn at the end of each update loop.
 */
public abstract class DrawableEntity extends UpdatableEntity implements Drawable {

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
}
