package edu.byu.rpg.entities.enemies.AI.Attacks;

import edu.byu.rpg.entities.enemies.AI.Attacks.AttackAI;
import edu.byu.rpg.entities.enemies.weapons.WeaponType;
import edu.byu.rpg.entities.enemies.weapons.attacks.EnemyHomingBulletWeapon;
import edu.byu.rpg.entities.enemies.weapons.base.EnemyWeapon;
import edu.byu.rpg.physics.Body;
import edu.byu.rpg.physics.World;

/**
 * Created by Andrew on 2/14/2017.
 */
public class HomingBulletAttackAI implements AttackAI {

    private float attackSpeed = 2.0f;
    private float attackDamage = 1.0f;
    private WeaponType weaponType = WeaponType.HOMING_BULLET;
    private EnemyHomingBulletWeapon weapon;
    private float attackClock;

    public HomingBulletAttackAI(){
        attackClock = attackSpeed;
    }

    @Override
    public void scale(float scaleAmount) {
        attackSpeed = attackSpeed * scaleAmount;
        attackDamage = attackDamage * scaleAmount;
        weapon.scale(scaleAmount);

    }

    @Override
    public void attack(Body enemyBody, World world, float delta) {
        if(attackClock < 0){
            executeAttack(enemyBody, world);
            attackClock = attackSpeed;
        } else {
            attackClock -= delta;
        }
    }

    public void executeAttack(Body enemyBody, World world){
        if (weapon == null) return;

        //get the X distance to the player
        float XDir =  world.xDistanceToPlayer(enemyBody);
        //get the Y distance to the player
        float YDir = world.yDistanceToPlayer(enemyBody);

        // get center of hitbox
        float x = enemyBody.getCenterX();
        float y = enemyBody.position.y + enemyBody.size.y;

        // set up XDir and YDir for the right and left bullets
        //fire middle bullet
        weapon.fire(x, y, XDir, YDir);
    }
    @Override
    public float getAttackSpeed() {
        return attackSpeed;
    }

    @Override
    public float getAttackDamage() {
        return attackDamage;
    }

    @Override
    public WeaponType getWeaponType() {
        return weaponType;
    }

    @Override
    public void setWeapon(EnemyWeapon weapon) {
        this.weapon = (EnemyHomingBulletWeapon)weapon;
    }

}
