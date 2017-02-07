package edu.byu.rpg.entities.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import edu.byu.rpg.RpgGame;
import edu.byu.rpg.entities.base.Actor;
import edu.byu.rpg.entities.player.weapons.base.PlayerWeapon;
import edu.byu.rpg.entities.player.weapons.basic.BasicWeapon;
import edu.byu.rpg.input.InputManager;
import edu.byu.rpg.physics.Body;
import edu.byu.rpg.physics.Collideable;
import edu.byu.rpg.physics.World;

/**
 * The player-controlled character.
 */
public class Player extends Actor implements Collideable {

    //TODO: replace with animation manager class when built.
    //TODO: separate animations - one for feet and one for upper body
    private Texture playerTexture;

    // physics constants
    private final float ACCEL_CONST = 2.5f;

    // taking damage, flashing, being invincible, etc.
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
        playerTexture = game.assets.getTexture("player_stand");

        // damage variables
        invincibleClock = 0;
        flashClock = 0;
        flashing = false;

        // equip basic weapon
        equipWeapon(new BasicWeapon(game, world));
    }

    @Override
    public void update(float delta) {
        // get input and update velocity, then position
        body.acceleration.x = ACCEL_CONST * InputManager.getLeftXAxis();
        body.acceleration.y = ACCEL_CONST * InputManager.getLeftYAxis();

        // check for collisions with enemies, traps, etc.
        handleEnemyCollisions();

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
        float y = body.getCenterY();

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
            batch.draw(playerTexture, body.position.x, body.position.y);
        }
    }

    /**
     * Handles collisions with enemies, traps, etc.  Subroutine of {@link Player#update(float)}.
     */
    private void handleEnemyCollisions() {
        // if invincible, return
        if (invincibleClock > 0) return;

        // check for collisions with enemies, and get hurt if hit.
        if (world.collideCheck(World.Type.ENEMY, body)) {
            // TODO: need to figure out the damage based on enemy's stats
            takeDamage(1);
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
     * Reduce health by specified damage amount, and become invincible for a bit.
     * @param damage The amount of damage to inflict on this object.
     */
    @Override
    public void takeDamage(float damage) {
        // TODO: subtract health here when that gets added.
        invincibleClock = invincibleTime;
        flashing = true;
        // "bounce" off the enemy
        body.velocity.scl(-1f);
    }
}
