package edu.byu.rpg.entities.enemies.AI;

import edu.byu.rpg.entities.enemies.weapons.base.EnemyWeapon;

/**
 * The basis for an object that represents the AI that dictates how an enemny functions
 * An object that implements {@link EnemyAI) can handle scaling an enemy through different
 * levels and difficulties of the game
 */

public interface EnemyAI {

    /**
     * Contains logic that will scale the moving parts of an AI through different AIypes.
     * @param scaleAmount the amount to scale monster stats by.
     */
    public void scale(float scaleAmount);

}
