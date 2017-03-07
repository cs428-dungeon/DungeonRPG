package edu.byu.rpg.entities.enemies.offense.attacks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Pool;
import edu.byu.rpg.RpgGame;
import edu.byu.rpg.entities.base.Actor;
import edu.byu.rpg.entities.enemies.offense.base.EnemyAttack;
import edu.byu.rpg.physics.Body;
import edu.byu.rpg.physics.Collideable;
import edu.byu.rpg.physics.World;
import edu.byu.rpg.tools.Utils;

/**
 * Starter ranged attack for enemies.  A basic bullet with no special abilities.
 */
public class BouncingEnemyBullet extends EnemyAttack {

    Texture bulletTexture;
    private float TimeToLive;
    private float maxTimeToLive = 5f;

    public BouncingEnemyBullet(RpgGame game, World world, Pool<EnemyAttack> pool) {
        super(game, world, new Body(0, 0, 8, 8), pool, World.Type.ENEMY_BULLET);
        bulletTexture = game.assets.getTexture("basic_bullet");
        this.body.maxSpeed = 5.5f;
        this.TimeToLive = maxTimeToLive;
        body.hitSolid = false;
    }

    @Override
    public void draw(float delta, SpriteBatch batch) {
        batch.draw(bulletTexture, body.position.x, body.position.y);
    }

    @Override
    public void update(float delta) {
        body.updateVelocity(delta);

        float prevX = body.position.x;
        float prevY = body.position.y;

        moveX(delta);
        moveY(delta);
        TimeToLive -= delta;
        // check for collisions
        if (world.collideCheck(World.Type.PLAYER,body)) {

            // damage enemies if hit
            Collideable player = world.collide(World.Type.PLAYER, body);
            if (player != null) {
                player.takeDamage(this.getDamage());
            }
            // step backwards as long as bullet is still colliding
            while (collideCheck() && body.position.x != prevX && body.position.y != prevY) {
                body.position.x = Utils.approach(body.position.x, prevX, 1);
                body.position.y = Utils.approach(body.position.y, prevY, 1);
            }
            body.hitSolid = false;
            // destroy this bullet
            pop();
        }
        if(body.hitSolid == true){
            float x = (float)Math.random() * 10.0f;
            x *= (Math.random() > 0.5) ? -1 : 1;

            float y = (float)Math.random() * 10.0f;
            y *= (Math.random() > 0.5) ? -1 : 1;

            body.acceleration.set(x, y);
            body.velocity.set(0,0);
            body.hitSolid = false;
        }
        //check if the bullet has been alive for too long, if it has destroy it and reset the timer.
        else if (TimeToLive < 0) {
            pop();
            TimeToLive = maxTimeToLive;
            body.hitSolid = false;
        }
    }
    /**
     * Subroutine of {@link BouncingEnemyBullet#update(float)}, used for moving in the x-direction
     * without overlapping solids.
     *
     * @param delta The time since the last frame.
     */
    private void moveX(float delta) {
        float prevX = body.position.x;
        body.position.x += body.velocity.x * delta * Body.PIXELS_PER_METER;

        // check for collision, and move back by steps if necessary
        while (world.collideCheck(World.Type.SOLID, body)) {
            body.hitSolid = true;
            body.position.x = Utils.approach(body.position.x, prevX, 1);
        }
    }

    /**
     * Subroutine of {@link BouncingEnemyBullet#update(float)}, used for moving in the y-direction
     * without overlapping solids.
     *
     * @param delta The time since the last frame.
     */
    private void moveY(float delta) {
        float prevY = body.position.y;
        body.position.y += body.velocity.y * delta * Body.PIXELS_PER_METER;

        // check for collision and move back if necessary
        while (world.collideCheck(World.Type.SOLID, body)) {
            body.hitSolid = true;
            body.position.y = Utils.approach(body.position.y, prevY, 1);
        }
    }
}
