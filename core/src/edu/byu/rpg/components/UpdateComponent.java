package edu.byu.rpg.components;

import com.badlogic.ashley.core.Component;
import edu.byu.rpg.entities.base.UpdatableEntity;

/**
 * This component is added to any entity with update logic (any {@link UpdatableEntity}).  Objects
 * that are registered with {@link edu.byu.rpg.RpgGame#engine} that have this component will be automatically
 * updated with each game engine loop.
 */
public class UpdateComponent implements Component {

    /** The entity whose update logic should be executed in the game engine's update loop */
    public UpdatableEntity updatable;

    public UpdateComponent(UpdatableEntity updatable) {
        this.updatable = updatable;
    }

}
