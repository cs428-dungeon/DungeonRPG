package edu.byu.rpg.entities.enemies.AI.Attacks;

import edu.byu.rpg.entities.enemies.offense.WeaponType;
import edu.byu.rpg.entities.enemies.offense.weapons.EnemyBulletWeapon;
import edu.byu.rpg.entities.enemies.offense.base.EnemyWeapon;
import edu.byu.rpg.physics.Body;
import edu.byu.rpg.physics.World;

/**
 * Created by Andrew on 3/1/2017.
 */
public class EightShotAttackAI implements AttackAI {
    private float attackSpeed = 2.0f;
    private float attackDamage = 2.0f;
    private WeaponType weaponType = WeaponType.BULLET;
    private EnemyBulletWeapon weapon;
    private float attackClock;
    private float velocity = 2.0f;

    public EightShotAttackAI(){
        attackClock = attackSpeed;
    }

    @Override
    public void scale(float scaleAmount) {
        //scale up attack speed and damage
        attackSpeed = attackSpeed * scaleAmount;
        attackDamage = attackDamage * scaleAmount;
        velocity = velocity * scaleAmount;
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

        // get center of hitbox
        float x = enemyBody.getCenterX();
        float y = enemyBody.position.y + enemyBody.size.y;

        // fire weapon
        //0 degrees
        weapon.fire(x, y, 0,velocity);
        //45 degrees
        weapon.fire(x, y, velocity, velocity);
        //90 degrees
        weapon.fire(x, y, velocity, 0);
        //135 degrees
        weapon.fire(x, y, velocity, -velocity);
        //180 degrees
        weapon.fire(x, y, 0, -velocity);
        //225 degrees
        weapon.fire(x, y, -velocity, -velocity);
        //270 degrees
        weapon.fire(x, y, -velocity, 0);
        //315 degrees
        weapon.fire(x, y, -velocity, velocity);

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
    public void setWeapon(EnemyWeapon weapon){this.weapon = (EnemyBulletWeapon)weapon;}
}
