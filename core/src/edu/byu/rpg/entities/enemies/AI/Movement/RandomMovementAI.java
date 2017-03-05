package edu.byu.rpg.entities.enemies.AI.Movement;

import edu.byu.rpg.entities.base.Actor;
import edu.byu.rpg.entities.enemies.AI.Movement.MovementAI;
import edu.byu.rpg.physics.Body;
import edu.byu.rpg.physics.World;
import edu.byu.rpg.tools.Utils;

/**
 * Created by Andrew on 2/8/2017.
 */
public class RandomMovementAI implements MovementAI {

    private float movementSpeed = 2.0f;

    private float dirClock;

    public RandomMovementAI(){
        dirClock = 0;
    }

    @Override
    public void scale(float scaleAmount) {
        movementSpeed = movementSpeed  * scaleAmount;

    }

    @Override
    public void move(Body enemyBody, World world, float delta) {

        if (dirClock < 0) {
            calculateMoves(enemyBody, world);
            dirClock = movementSpeed;
        } else {
            dirClock -= delta;
        }
        // check for collisions with other enemies, change direction if hit.
        // (this prevents overlap)
        if (world.collideCheck(World.Type.ENEMY, enemyBody)) {
            calculateMoves(enemyBody, world);
        }
        enemyBody.hitSolid = false;
    }

    public void calculateMoves(Body enemyBody, World world)
    {
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
