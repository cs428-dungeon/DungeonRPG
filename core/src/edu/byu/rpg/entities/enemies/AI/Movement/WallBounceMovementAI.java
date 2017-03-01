package edu.byu.rpg.entities.enemies.AI.Movement;

import edu.byu.rpg.physics.Body;
import edu.byu.rpg.physics.World;

/**
 * Created by Andrew on 2/20/2017.
 */
public class WallBounceMovementAI implements MovementAI {
    private float movementSpeed = 2.0f;

    public WallBounceMovementAI(){
    }

    @Override
    public void scale(float scaleAmount) {
        movementSpeed = movementSpeed  * scaleAmount;
    }

    @Override
    public void move(Body enemyBody, World world, float delta) {
        calculateMoves(enemyBody,world);

        // check for collisions with other enemies, change direction if hit.
        // (this prevents overlap)
        if (world.collideCheck(World.Type.ENEMY, enemyBody)) {
            calculateMoves(enemyBody, world);
        }

    }
    public void calculateMoves(Body enemyBody, World world){
        if ((enemyBody.acceleration.x == 0 && enemyBody.acceleration.y == 0) || enemyBody.hitSolid){
            float x = (float) Math.random() * movementSpeed;
            x *= (Math.random() > 0.5) ? -1 : 1;

            float y = (float) Math.random() * movementSpeed;
            y *= (Math.random() > 0.5) ? -1 : 1;

            enemyBody.acceleration.set(x, y);
            //reset the hitSolid flag in the body
            enemyBody.hitSolid = false;
        }
    }

    @Override
    public float getMovementSpeed() {
        return this.movementSpeed;
    }
}
