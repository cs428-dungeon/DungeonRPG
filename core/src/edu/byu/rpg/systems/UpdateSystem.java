package edu.byu.rpg.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import edu.byu.rpg.components.UpdateComponent;

/**
 * This system handles the game's core engine loop.  When added to {@link edu.byu.rpg.RpgGame#engine}, it will process
 * each entity's update logic by calling {@linkplain edu.byu.rpg.entities.base.UpdatableEntity#update(float) the update
 * method} on that entity.
 *
 * @see <a href="https://github.com/libgdx/ashley/wiki/Built-in-Entity-Systems#iteratingsystem">LibGDX Ashley Iterating Systems</a>
 */
public class UpdateSystem extends IteratingSystem {

    /**
     * Automatically sets the priority to 0, meaning this system will be evaluated first on each update loop.
     */
    public UpdateSystem() {
        super(Family.all(UpdateComponent.class).get(), 0);
    }

    /**
     * Executes update logic on every entity that is registered with {@link edu.byu.rpg.RpgGame#engine}.
     * @param entity A single entity to update.  This is automatically passed by {@link com.badlogic.ashley.core.Engine}.
     * @param deltaTime The time since the last frame.
     */
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        UpdateComponent updateComponent = Mappers.um.get(entity);
        updateComponent.updatable.update(deltaTime);
    }
}
