package edu.byu.rpg.entities.enemies.AI.Movement;

import edu.byu.rpg.entities.enemies.AI.Movement.MovementAI;
import edu.byu.rpg.physics.Body;
import edu.byu.rpg.physics.World;

/**
 * Created by Andrew on 2/8/2017.
 */
public class RandomMovementAI implements MovementAI {

    private float movementSpeed = 2.0f;

    public RandomMovementAI(){}

    @Override
    public void scale(float scaleAmount) {
        movementSpeed = movementSpeed  * scaleAmount;

    }

    @Override
    public void move(Body enemyBody, World world) {
        float x = (float)Math.random() * movementSpeed;
        x *= (Math.random() > 0.5) ? -1 : 1;

        float y = (float)Math.random() * movementSpeed;
        y *= (Math.random() > 0.5) ? -1 : 1;

        enemyBody.acceleration.set(x, y);
    }

    @Override
    public float getMovementSpeed() {
        return movementSpeed;
    }

}