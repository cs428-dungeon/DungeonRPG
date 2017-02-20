package edu.byu.rpg.entities.enemies.AI.Movement;

import edu.byu.rpg.entities.enemies.AI.EnemyAI;
import edu.byu.rpg.physics.Body;
import edu.byu.rpg.physics.World;

/**
 * The basis for an object that represents specifically the AI of how an enemy moves.
 * An object that implements {@link MovementAI) will be able to calculate and change position of the {@link Body}
 * of an object in the {@Link World}
 */
public interface MovementAI extends EnemyAI {

    /**
     * changes the X and Y coordinates for the Body passed in.
     * @param enemyBody The {@link Body} that corresponds to where the enemy is at the moment move is called.
     * @param world The {@Link World} that world that contains all the other entities that can be used in calculations.
     */
    public void move(Body enemyBody, World world);

    /**
     *
     * @return a float corresponding to how fast the enemy using this AI moves.
     */
    public float getMovementSpeed();
}
