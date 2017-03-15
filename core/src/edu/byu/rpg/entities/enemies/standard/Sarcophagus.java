package edu.byu.rpg.entities.enemies.standard;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import edu.byu.rpg.RpgGame;
import edu.byu.rpg.entities.base.Actor;
import edu.byu.rpg.entities.effects.Shadow;
import edu.byu.rpg.entities.enemies.AI.Attacks.AttackAI;
import edu.byu.rpg.entities.enemies.AI.Movement.MovementAI;
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
 * its a rolling sarcophagous
 */
public class Sarcophagus extends Actor implements Collideable {

    private AnimationManager anims;
    private Shadow shadow;

    private float attackTime = 1.0f;
    private float attackClock;
    private MovementAI movementAI;

    private AttackAI attackAI;
    private EnemyWeapon weapon;

    private float health;
    private float level;

    public Sarcophagus(RpgGame game, World world, int x, int y, MovementAI movementAI, AttackAI attackAI) {
        super(game, world, new Body(x, y, 11, 8, 45, 16));
        // add to enemies collision group
        world.add(World.Type.ENEMY, this);

        //TODO: figure out a way to provide level on initialization once the world manager is complete.
        level = 1.0f;

        // init body
        body.maxSpeed = 2f;


        // init animations and shadow
        anims = new AnimationManager(game);
        anims.add("scarab_stand", 1, 7, 10);
        shadow = new Shadow(game, game.assets.getTexture("effects/shadow_64"), body);

        //set up the attack AI and get attack speed and damage.
        this.attackAI = attackAI;
        attackTime = attackClock =  attackAI.getAttackSpeed();
        //equip weapon
        equipWeapon(attackAI.getWeaponType());
        //set weapon damage
        weapon.setDamage(attackAI.getAttackDamage());
        //TODO: Change these numbers to some algorithm based off of the enemy level.
        attackAI.scale(2.0f,2.0f, 3.0f);


        // setup random direction
        this.movementAI = movementAI;


        // init health
        health = 5;
    }

    @Override
    public void update(float delta) {
        // update position, etc.
        super.update(delta);
        movementAI.move(body, world, delta);
        attackAI.attack(body, world, delta);
        // set animation
        if (body.velocity.x > 0) {
            anims.faceRight();
        } else {
            anims.faceLeft();
        }
        anims.play("scarab_stand", true);
    }

    /**
     * Sets the current weapon object, destroying any existing equipped weapons.
     * Useful when the Player walks over a powerup, or buys a new weapon from a shop.
     * @param weaponType The new weapontype to equip.
     */
    public void equipWeapon(WeaponType weaponType) {
        if (weapon != null) {
            weapon.destroy();
        }
        switch(weaponType){
            case BULLET: this.weapon = new EnemyBulletWeapon(game, world);
                break;
            case HOMING_BULLET: this.weapon = new EnemyHomingBulletWeapon(game, world);
                break;
            case TRAIL:  this.weapon = new EnemyTrailWeapon(game, world);
                break;
            case BOUNCING_BULLET: this.weapon = new EnemyBouncingBulletWeapon(game, world);
        }
        attackAI.setWeapon(this.weapon);
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
