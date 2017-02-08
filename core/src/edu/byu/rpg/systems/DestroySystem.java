package edu.byu.rpg.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import edu.byu.rpg.RpgGame;
import edu.byu.rpg.components.DestroyComponent;

/**
 * This system removes any entities from {@link RpgGame#engine} which are flagged for removal.  An entity
 * is flagged for removal if a {@link DestroyComponent} has been added to it.
 */
public class DestroySystem extends IteratingSystem {

    /** Reference to {@link RpgGame}, so that this system can access {@link RpgGame#engine}. */
    private RpgGame game;

    /**
     * Assigns this system a priority of 2, meaning it will be executed last during each update loop (after
     * {@link UpdateSystem} and {@link DrawSystem}).
     * @param game Our main game class.
     */
    public DestroySystem(RpgGame game) {
        super(Family.all(DestroyComponent.class).get(), 2);
        this.game = game;
    }

    /**
     * This function removes an entity from {@link RpgGame#engine}, essentially "destroying" it.
     * @param entity The entity to remove from {@link RpgGame#engine} (this is automatically passed to the system by
     * {@link com.badlogic.ashley.core.Engine}).
     * @param deltaTime The time between frames.
     */
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        game.engine.removeEntity(entity);
    }
}
