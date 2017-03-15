package edu.byu.rpg.entities.enemies.AI.Movement;

import edu.byu.rpg.physics.Body;
import edu.byu.rpg.physics.World;

/**
 * This class does nothing. It is used for monsters that are stationary. it is passed in as a movementAI and then does nothing.
 */
public class StationaryMovementAI implements MovementAI {
    @Override
    public void scale(float scaleAmount) {

    }

    @Override
    public void move(Body enemyBody, World world, float delta) {

    }

    @Override
    public float getMovementSpeed() {
        return 0;
    }
}
