package edu.byu.rpg.entities.enemies.bosses;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import edu.byu.rpg.RpgGame;
import edu.byu.rpg.entities.base.Actor;
import edu.byu.rpg.entities.effects.Shadow;
import edu.byu.rpg.entities.enemies.AI.Attacks.AttackAI;
import edu.byu.rpg.entities.enemies.AI.Attacks.AttackType;
import edu.byu.rpg.entities.enemies.AI.Movement.MovementAI;
import edu.byu.rpg.entities.enemies.AI.Movement.MovementType;
import edu.byu.rpg.entities.enemies.controllers.AIController;
import edu.byu.rpg.entities.enemies.offense.WeaponType;
import edu.byu.rpg.entities.enemies.offense.weapons.EnemyBouncingBulletWeapon;
import edu.byu.rpg.entities.enemies.offense.weapons.EnemyBulletWeapon;
import edu.byu.rpg.entities.enemies.offense.weapons.EnemyHomingBulletWeapon;
import edu.byu.rpg.entities.enemies.offense.weapons.EnemyTrailWeapon;
import edu.byu.rpg.entities.enemies.offense.base.EnemyWeapon;
import edu.byu.rpg.graphics.AnimationManager;
import edu.byu.rpg.physics.Body;
import edu.byu.rpg.physics.Collideable;
import edu.byu.rpg.physics.World;

/**
 * Enemy for testing out collisions/hurting player
 */
public class Babi extends Actor implements Collideable {

    private AnimationManager anims;
    private Shadow shadow;

    private float attackTime;
    private float attackClock;
    private AIController aiController;
    private EnemyWeapon eightShotWeapon;
    private EnemyWeapon homingBulletWeapon;
    private EnemyWeapon fireTrailWeapon;
    private AttackAI eightShot;
    private AttackAI homing;
    private AttackAI firetrail;
    private MovementAI movementAI;

    private float health;

    public Babi(RpgGame game, World world, int x, int y, AIController aiController) {
        super(game, world, new Body(x, y, 11, 8, 45, 16));
        // add to enemies collision group
        world.add(World.Type.ENEMY, this);

        // init body
        body.maxSpeed = 2f;

        // init animations and shadow
        anims = new AnimationManager(game);
        anims.add("scarab_stand", 1, 7, 10);
        shadow = new Shadow(game, game.assets.getTexture("effects/shadow_64"), body);

        this.aiController = aiController;

        // setup random direction
        this.movementAI = aiController.getMovementAI(MovementType.BOUNCE);

        eightShot = aiController.getAttackAI(AttackType.EIGHT_SHOT);
        homing = aiController.getAttackAI(AttackType.HOMING_BULLET);
        firetrail = aiController.getAttackAI(AttackType.FIRE_TRAIL);
        //set up the attack AI and get attack speed and damage.
        attackTime = attackClock =  2.0f;
        //equip weapon
        eightShotWeapon = new EnemyBulletWeapon(game, world);
        homingBulletWeapon = new EnemyHomingBulletWeapon(game, world);
        fireTrailWeapon = new EnemyTrailWeapon(game, world);
        eightShotWeapon.setDamage(eightShot.getAttackDamage());
        homingBulletWeapon.setDamage(homing.getAttackDamage());
        fireTrailWeapon.setDamage(firetrail.getAttackDamage());
        eightShot.setWeapon(eightShotWeapon);
        eightShot.scale(1.0f, 2.0f, 1.5f);
        homing.setWeapon(homingBulletWeapon);
        homing.scale(1.0f, 1.5f, 3.0f);
        firetrail.setWeapon(fireTrailWeapon);
        //set weapon damage


        // init health
        health = 30;
    }

    @Override
    public void update(float delta) {
        // update position, etc.
        super.update(delta);
        movementAI.move(body, world, delta);
        eightShot.attack(body, world, delta);
        homing.attack(body, world, delta);
        firetrail.attack(body, world, delta);
        // set animation
        if (body.velocity.x > 0) {
            anims.faceRight();
        } else {
            anims.faceLeft();
        }
        anims.play("scarab_stand", true);
    }


    public void equipWeapon(AttackAI attackAI, EnemyWeapon weapon) {
        if (weapon != null) {
            weapon.destroy();
        }
        switch(attackAI.getWeaponType()){
            case BULLET: weapon = new EnemyBulletWeapon(game, world);
                break;
            case HOMING_BULLET: weapon = new EnemyHomingBulletWeapon(game, world);
                break;
            case TRAIL:  weapon = new EnemyTrailWeapon(game, world);
                break;
            case BOUNCING_BULLET: weapon = new EnemyBouncingBulletWeapon(game, world);
        }
        attackAI.setWeapon(weapon);
    }

    @Override
    public void draw(float delta, SpriteBatch batch) {
        anims.draw(delta, body.position.x, body.position.y);
    }

    @Override
    public float getDamage() {
        return 1;
    }

    @Override
    public void takeDamage(float damage) {
        // take damage here.
        health -= damage;

        if (health <= 0) {
            destroy();
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        shadow.destroy();
        world.remove(this);
    }
}
