package edu.byu.rpg.entities.enemies.AI.Movement;

import edu.byu.rpg.physics.Body;
import edu.byu.rpg.physics.World;

/**
 * Created by Andrew on 2/20/2017.
 */
public class FollowMovementAI implements MovementAI {
    private float movementSpeed = 2.0f;
    private final float radius = 300.0f;

    public FollowMovementAI(){
    }
    @Override
    public void scale(float scaleAmount) {
        movementSpeed = movementSpeed * scaleAmount;
    }

    @Override
    public void move(Body enemyBody, World world, float delta) {

        float xDistanceToPlayer = world.xDistanceToPlayer(enemyBody);
        float yDistanceToPlayer = world.yDistanceToPlayer(enemyBody);

        float distanceToPlayer = world.DistanceToPlayer(enemyBody);

        if(distanceToPlayer <= radius){
            enemyBody.acceleration.set(xDistanceToPlayer, yDistanceToPlayer);
        }
        else{
            enemyBody.acceleration.set(0,0);
        }

        if (world.collideCheck(World.Type.ENEMY, enemyBody)) {
            //calculateMoves(enemyBody, world);
        }
    }


    @Override
    public float getMovementSpeed() {
        return 0;
    }
}
