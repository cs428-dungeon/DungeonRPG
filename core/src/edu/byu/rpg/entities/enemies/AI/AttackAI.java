package edu.byu.rpg.entities.enemies.AI;

import edu.byu.rpg.entities.enemies.weapons.WeaponType;
import edu.byu.rpg.entities.enemies.weapons.base.EnemyWeapon;
import edu.byu.rpg.physics.Body;
import edu.byu.rpg.physics.World;

/**
 * The basis for an object that represents specifically the AI of how an enemy attacks.
 * An object that implements {@link AttackAI) will be able to calculate attacks and launch them from the {@link Body}
 * of an object
 */
public interface AttackAI extends EnemyAI {

    /**
     * causes an attack to origionate from the Body specified
     * @param enemyBody The {@link Body} that corresponds to where the enemy is at the moment move is called.
     * @param world The {@Link World} that corresponds to the world that contains other entities you might interact with.
     */
    public void attack(Body enemyBody, World world, EnemyWeapon weapon);

    /**
     * @return return's a float corresponding to the attack speed of this attack.
     */
    public float getAttackSpeed();

    /**
     * @return returns a float corresponding to the damage that this attack does.
     */
    public float getAttackDamage();

    /**
     *
     * @return returns a {@link WeaponType} that corresponds to the weapon that this attackAI relies on.
     */
    public WeaponType getWeaponType();
}
