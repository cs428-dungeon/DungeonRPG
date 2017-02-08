package edu.byu.rpg.entities.base;

import edu.byu.rpg.RpgGame;

/**
 * Objects that implement Updatable can be acted on by {@link UpdatableEntity}
 */
public interface Updatable {
    /**
     * This is the update function for this entity.  It will be called once on every iteration of the
     * {@link RpgGame#engine}'s core update loop.
     * @param delta The time since the last frame.
     */
    void update(float delta);
}
