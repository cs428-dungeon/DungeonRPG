package edu.byu.rpg.entities.enemies.AI.Attacks;

import edu.byu.rpg.entities.enemies.AI.Attacks.AttackAI;
import edu.byu.rpg.entities.enemies.weapons.WeaponType;
import edu.byu.rpg.entities.enemies.weapons.base.EnemyWeapon;
import edu.byu.rpg.physics.Body;
import edu.byu.rpg.physics.World;

/**
 * Created by Andrew on 2/15/2017.
 */
public class FireTrailAI implements AttackAI {
    private float attackSpeed = .5f;
    private float attackDamage = 1.0f;
    private WeaponType weaponType = WeaponType.TRAIL;
    private EnemyWeapon weapon;
    private float attackClock;

    public FireTrailAI(){
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
        if (weapon == null) return;//if needed to reset cooldown time.
        // get bullet direction and influence by player velocity
        float XDir = enemyBody.velocity.x;
        float YDir = enemyBody.velocity.y;

        // get center of hitbox
        float x = enemyBody.getCenterX();
        float y = enemyBody.position.y + enemyBody.size.y;

        //place firetrail.
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
        this.weapon = weapon;

    }
}
