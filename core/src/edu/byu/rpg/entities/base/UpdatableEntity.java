package edu.byu.rpg.entities.base;

import com.badlogic.ashley.core.Entity;
import edu.byu.rpg.RpgGame;
import edu.byu.rpg.components.DestroyComponent;
import edu.byu.rpg.components.UpdateComponent;

/**
 * Any game object that implements this base class can is plugged directly into {@link RpgGame#engine}'s core update loop,
 * meaning it will have its {@link UpdatableEntity#update(float delta)} function called by the engine with each iteration of the engine's update
 * loop.
 */
public abstract class UpdatableEntity extends Entity {

    /**
     * Automatically registers this entity with {@link RpgGame#engine}
     * @param game Our main game class.
     */
    public UpdatableEntity(RpgGame game) {
        add(new UpdateComponent(this));
        game.engine.addEntity(this);
    }

    /**
     * This function destroys this entity by adding a {@link DestroyComponent} to it.
     */
    public void destroy() {
        add(new DestroyComponent());
        // TODO: Consider also removing from the physics world so we don't have memory leaks.
    }

    /**
     * This is the update function for this entity.  It will be called once on every iteration of the
     * {@link RpgGame#engine}'s core update loop.
     * @param delta The time since the last frame.
     */
    public abstract void update(float delta);
}
