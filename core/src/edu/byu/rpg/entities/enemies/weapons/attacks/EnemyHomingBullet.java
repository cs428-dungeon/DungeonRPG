package edu.byu.rpg.entities.enemies.weapons.attacks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Pool;
import edu.byu.rpg.RpgGame;
import edu.byu.rpg.entities.enemies.weapons.base.EnemyAttack;
import edu.byu.rpg.physics.Body;
import edu.byu.rpg.physics.Collideable;
import edu.byu.rpg.physics.World;
import edu.byu.rpg.tools.Utils;

/**
 * Ranged attack that Homes in on the player for enemies.
 */
public class EnemyHomingBullet extends EnemyAttack {

    private float maxTimeToLive = 5;
    private float TimeToLive;
    Texture bulletTexture;

    public EnemyHomingBullet(RpgGame game, World world, Pool<EnemyAttack> pool) {
        super(game, world, new Body(0, 0, 8, 8), pool, World.Type.ENEMY_BULLET);
        bulletTexture = game.assets.getTexture("basic_bullet");
        this.TimeToLive = maxTimeToLive;
        this.body.maxSpeed = 2.5f;
    }

    @Override
    public void draw(float delta, SpriteBatch batch) {
        batch.draw(bulletTexture, body.position.x, body.position.y);
    }

    @Override
    public void update(float delta){
        body.updateVelocity(delta);

        float prevX = body.position.x;
        float prevY = body.position.y;
        float newX = world.xDistanceToPlayer(this.body);
        float newY = world.yDistanceToPlayer(this.body);
        body.acceleration.x = newX;
        body.acceleration.y = newY;
        body.updatePosition(delta);
        TimeToLive -=delta;
        // check for collisions
        if (collideCheck()) {

            // damage enemies if hit
            Collideable player = world.collide(World.Type.PLAYER, body);
            if (player != null) {
                player.takeDamage(this.getDamage());
            }

            // step backwards as long as bullet is still colliding
            while(collideCheck() && body.position.x != prevX && body.position.y != prevY) {
                body.position.x = Utils.approach(body.position.x, prevX, 1);
                body.position.y = Utils.approach(body.position.y, prevY, 1);
            }

            // destroy this bullet
            pop();
        }
        //check if the bullet has been alive for too long, if it has destroy it and reset the timer.
        else if(TimeToLive < 0){
            pop();
            TimeToLive = maxTimeToLive;
        }
    }
}
