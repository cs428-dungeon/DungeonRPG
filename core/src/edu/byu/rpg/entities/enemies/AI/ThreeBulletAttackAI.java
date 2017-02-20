package edu.byu.rpg.entities.enemies.AI;

import edu.byu.rpg.entities.enemies.weapons.WeaponType;
import edu.byu.rpg.entities.enemies.weapons.attacks.EnemyBulletWeapon;
import edu.byu.rpg.entities.enemies.weapons.base.EnemyWeapon;
import edu.byu.rpg.physics.Body;
import edu.byu.rpg.physics.World;

/**
 * Created by Andrew on 2/14/2017.
 */
public class ThreeBulletAttackAI implements AttackAI {

    private float attackSpeed = 2.0f;
    private float attackDamage = 2.0f;
    private WeaponType weaponType = WeaponType.BULLET;
    private EnemyWeapon weapon;

    public ThreeBulletAttackAI(){
    }

    @Override
    public void scale(float scaleAmount) {
        attackSpeed = attackSpeed * scaleAmount;
        attackDamage = attackDamage * scaleAmount;
        weapon.scale(scaleAmount);
}

    @Override
    public void attack(Body enemyBody, World world) {

        if (weapon == null) return;

        weapon.setCooldownTime(0.0f);
        // get bullet direction and influence by player velocity
        float middleXDir = world.xDistanceToPlayer(enemyBody);
        float middleYDir = world.yDistanceToPlayer(enemyBody);
        float rightXDir = world.xDistanceToPlayer(enemyBody);
        float rightYDir = world.yDistanceToPlayer(enemyBody);
        float leftXDir = world.xDistanceToPlayer(enemyBody);
        float leftYDir = world.yDistanceToPlayer(enemyBody);

        // get center of hitbox
        float x = enemyBody.getCenterX();
        float y = enemyBody.position.y + enemyBody.size.y;

        // set up XDir and YDir for the right and left bullets
        if(middleXDir > 0){
            rightYDir+= Math.abs(0.5f * middleXDir);
            leftYDir-=Math.abs(0.5f * middleXDir);
        } else {
            rightYDir-=Math.abs(0.5f * middleXDir);
            leftYDir+=Math.abs(0.5f * middleXDir);
        }
        if(middleYDir > 0){
            rightXDir+=Math.abs(0.5f * middleYDir);
            leftXDir-=Math.abs(0.5f * middleYDir);
        } else {
            rightXDir-=Math.abs(0.5f * middleYDir);
            leftXDir+=Math.abs(0.5f * middleYDir);
        }
        //fire middle bullet
        weapon.fire(x, y, middleXDir, middleYDir);
        //fire right bullet
        weapon.fire(x, y, rightXDir, rightYDir);
        //fire left bullet
        weapon.fire(x, y, leftXDir, leftYDir);
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
        this.weapon = weapon;
    }
}
