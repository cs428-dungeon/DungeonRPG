package edu.byu.rpg.entities.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import edu.byu.rpg.RpgGame;
import edu.byu.rpg.audio.AudioManager;
import edu.byu.rpg.entities.base.Actor;
import edu.byu.rpg.entities.effects.Shadow;
import edu.byu.rpg.entities.player.weapons.base.PlayerWeapon;
import edu.byu.rpg.entities.player.weapons.basic.BasicWeapon;
import edu.byu.rpg.graphics.AnimationManager;
import edu.byu.rpg.input.InputManager;
import edu.byu.rpg.physics.Body;
import edu.byu.rpg.physics.Collideable;
import edu.byu.rpg.physics.World;

/**
 * The player-controlled character.
 */
public class Player extends Actor implements Collideable {

    // animations and graphics
    private AnimationManager torsoAnims;
    private AnimationManager legsAnims;
    private Shadow shadow;

    // audio
    private String walkingSound = "footstep";

    // physics constants
    private final float ACCEL_CONST = 2.5f;

    // taking damage, flashing, being invincible, etc.
    private float health;
    private final float invincibleTime = 1.5f;
    private float invincibleClock;
    private final float flashTime = 0.05f;
    private float flashClock;
    private boolean flashing;

    // player weapon
    private PlayerWeapon weapon;

    public Player(RpgGame game, World world, int x, int y) {
        super(game, world, new Body(x, y, 8, 0, 16, 16));
        // add to player collision group
        world.add(World.Type.PLAYER, this);

        // damage variables
        health = 3;
        invincibleClock = 0;
        flashClock = 0;
        flashing = false;

        // equip basic weapon
        equipWeapon(new BasicWeapon(game, world));

        // init animations
        legsAnims = new AnimationManager(game);
        torsoAnims = new AnimationManager(game);

        legsAnims.add("player/legs_stand_down", 1, 1, 0);
        legsAnims.add("player/legs_walk_down", 1, 8, 10);

        torsoAnims.add("player/body_stand_down", 1, 1, 0);
        torsoAnims.add("player/body_walk_down", 1, 8, 10);

        // create a shadow
        shadow = new Shadow(game, game.assets.getTexture("player/shadow"), body);
    }

    @Override
    public void update(float delta) {
        // get input and update velocity, then position
        body.acceleration.x = ACCEL_CONST * InputManager.getLeftXAxis();
        body.acceleration.y = ACCEL_CONST * InputManager.getLeftYAxis();

        // check for collisions with enemies, traps, etc.
        handleEnemyCollisions();

        // play animations
        if (body.velocity.len() > 0) {
            game.audio.playSound(walkingSound);
            legsAnims.play("player/legs_walk_down", true);
            torsoAnims.play("player/body_walk_down", true);
        } else {
            legsAnims.play("player/legs_stand_down", true);
            torsoAnims.play("player/body_stand_down", true);
        }

        // right stick = bullets
        float rightXAxis = InputManager.getRightXAxis();
        float rightYAxis = InputManager.getRightYAxis();
        if (Math.abs(rightXAxis) > 0 || Math.abs(rightYAxis) > 0) {
            // fireBullets bullet in the direction of the input
            fireWeapon(rightXAxis, rightYAxis);
        }

        super.update(delta);
    }

    /**
     * Subroutine of update, used to fire a weapon.
     * @param xInput The x-axis input (0-1).
     * @param yInput The y-axis input (0-1).
     */
    private void fireWeapon(float xInput, float yInput) {
        if (weapon == null) return;

        // get bullet direction and influence by player velocity
        float xDir = xInput;
        float yDir = yInput;

        // get center of hitbox
        float x = body.getCenterX();
        float y = body.position.y + body.size.y;

        // fire weapon
        weapon.fire(x, y, xDir, yDir);
    }

    @Override
    public void draw(float delta, SpriteBatch batch) {
        // if invincible, set flashing variable
        if (invincibleClock > 0) {
            invincibleClock -= delta;
            if (flashClock > 0) {
                flashClock -= delta;
            } else {
                flashClock = flashTime;
                flashing = !flashing;
            }
        } else {
            flashing = false;
        }

        if (!flashing) {
            // draw legs first, then torso
            legsAnims.draw(delta, body.position.x, body.position.y);
            torsoAnims.draw(delta, body.position.x, body.position.y);
        }
    }

    /**
     * Handles collisions with enemies, traps, etc.  Subroutine of {@link Player#update(float)}.
     */
    private void handleEnemyCollisions() {
        // if invincible, return
        if (invincibleClock > 0) return;

        // check for collisions with enemies, and get hurt if hit.
        Collideable enemy = world.collide(World.Type.ENEMY, body);
        if (enemy != null) {
            takeDamage(enemy.getDamage());
        }
    }

    /**
     * Sets the current weapon object, destroying any existing equipped weapons.
     * Useful when the Player walks over a powerup, or buys a new weapon from a shop.
     * @param newWeapon The new weapon to equip.
     */
    public void equipWeapon(PlayerWeapon newWeapon) {
        if (weapon != null) {
            weapon.destroy();
        }
        this.weapon = newWeapon;
    }

    /**
     * By default, player doesn't deal damage to things by touching them. (Possible upgrade idea for later???)
     * @return 0, because player doesn't hurt stuff by default.
     */
    @Override
    public float getDamage() {
        return 0;
    }

    /**
     * Reduce health by specified damage amount, and become invincible for a bit.
     * @param damage The amount of damage to inflict on this object.
     */
    @Override
    public void takeDamage(float damage) {
        // take damage
        health -= damage;

        // "bounce" off the enemy
        body.velocity.scl(-1f);

        // die
        if (health <= 0) {
            die();
        }
        // go invincible
        invincibleClock = invincibleTime;
        flashing = true;
    }

    /**
     * Kills the player.  Called when player's health is below or equal to 0
     */
    private void die() {
        // TODO: create some death sequence/animation, don't call destroy until after it's complete.
        destroy();
    }

    @Override
    public void destroy() {
        super.destroy();
        world.remove(this);
        shadow.destroy();
    }
}
