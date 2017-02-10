package edu.byu.rpg.entities.enemies.AI;

import edu.byu.rpg.physics.Body;

/**
 * The basis for an object that represents specifically the AI of how an enemy moves.
 * An object that implements {@link MovementAI) will be able to calculate and change position of the {@link Body}
 * of an object in the {@Link World}
 */
public interface MovementAI extends EnemyAI {

    /**
     * changes the X and Y coordinates for the Body passed in.
     * @param enemyBody The {@link Body} that corresponds to where the enemy is at the moment move is called.
     * @param playerBody The {@Link Body} that corresponds to where the player is at the moment move is called.
     */
    public void move(Body enemyBody, Body playerBody);
}
