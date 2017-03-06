package edu.byu.rpg.entities.enemies.offense.attacks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Pool;
import edu.byu.rpg.RpgGame;
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
    private boolean hitWall = false;

    public BouncingEnemyBullet(RpgGame game, World world, Pool<EnemyAttack> pool) {
        super(game, world, new Body(0, 0, 8, 8), pool, World.Type.ENEMY_BULLET);
        bulletTexture = game.assets.getTexture("basic_bullet");
        this.body.maxSpeed = 5.5f;
        this.TimeToLive = maxTimeToLive;
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
        body.updatePosition(delta);
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
            hitWall = false;
            // destroy this bullet
            pop();
        }
        //redirect the bullet at a new angle.
        if(world.collideCheck(World.Type.SOLID, body)){
            float x = -body.velocity.x;
            float y = -body.velocity.y;

            body.velocity.set(x,y);

            float xAccel = x + 2.0f * x;
            float yAccel = y + 2.0f * y;

            body.acceleration.set(xAccel, yAccel);
        }
        //check if the bullet has been alive for too long, if it has destroy it and reset the timer.
        else if (TimeToLive < 0) {
            pop();
            TimeToLive = maxTimeToLive;
            hitWall = false;
        }
    }
}