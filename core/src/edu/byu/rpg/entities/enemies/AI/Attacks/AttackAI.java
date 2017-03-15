package edu.byu.rpg.entities.enemies.AI.Attacks;

import edu.byu.rpg.entities.enemies.offense.WeaponType;
import edu.byu.rpg.entities.enemies.offense.base.EnemyWeapon;
import edu.byu.rpg.physics.Body;
import edu.byu.rpg.physics.World;

/**
 * The basis for an object that represents specifically the AI of how an enemy weapons.
 * An object that implements {@link AttackAI) will be able to calculate weapons and launch them from the {@link Body}
 * of an object
 */
public interface AttackAI{

    /**
     * Contains logic that will scale the moving parts of an AI through different AIypes.
     * @param scaleAttackDamage the amount to scale monster stats by.
     * @param scaleAttackSpeed the amount to scale attack speed by.
     * @param scaleAttackVelocity the amount to scale the attack velocity by.
     */
    public void scale(float scaleAttackDamage,float scaleAttackSpeed, float scaleAttackVelocity);
    /**
     * causes an attack to origionate from the Body specified
     * @param enemyBody The {@link Body} that corresponds to where the enemy is at the moment move is called.
     * @param world The {@Link World} that corresponds to the world that contains other entities you might interact with.
     */
    public void attack(Body enemyBody, World world, float delta);

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
    /**
     * set the weapon in the attackAI
     * @param weapon
     */
    public void setWeapon(EnemyWeapon weapon);

}
