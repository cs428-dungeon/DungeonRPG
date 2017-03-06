package edu.byu.rpg.entities.enemies.AI.Attacks;

import edu.byu.rpg.entities.enemies.offense.WeaponType;
import edu.byu.rpg.entities.enemies.offense.weapons.EnemyBulletWeapon;
import edu.byu.rpg.entities.enemies.offense.base.EnemyWeapon;
import edu.byu.rpg.physics.Body;
import edu.byu.rpg.physics.World;

/**
 * Created by Andrew on 2/8/2017.
 */
public class BulletAttackAI implements AttackAI {
    private float attackSpeed = 2.0f;
    private float attackDamage = 2.0f;
    private WeaponType weaponType = WeaponType.BULLET;
    private EnemyBulletWeapon weapon;
    private float attackClock;

    public BulletAttackAI(){
        attackClock = attackSpeed;
    }

    @Override
    public void scale(float scaleAmount) {
        //scale up attack speed and damage
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

        // get bullet direction and influence by player velocity
        float xDir = world.xDistanceToPlayer(enemyBody);
        float yDir = world.yDistanceToPlayer(enemyBody);

        // get center of hitbox
        float x = enemyBody.getCenterX();
        float y = enemyBody.position.y + enemyBody.size.y;

        // fire weapon
        weapon.fire(x, y, xDir, yDir);
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
    public void setWeapon(EnemyWeapon weapon){this.weapon =(EnemyBulletWeapon)weapon;}
}
