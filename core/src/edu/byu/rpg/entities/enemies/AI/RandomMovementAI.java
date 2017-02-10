package edu.byu.rpg.entities.enemies.AI;

import edu.byu.rpg.physics.Body;

/**
 * Created by Andrew on 2/8/2017.
 */
public class RandomMovementAI implements MovementAI {

    private float movementSpeed;

    public RandomMovementAI(float movementSpeed){
        this.movementSpeed = movementSpeed;

    }
    @Override
    public void scale(float scaleAmount) {

    }

    @Override
    public void move(Body body) {

    }

}
