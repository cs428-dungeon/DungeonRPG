package edu.byu.rpg.entities.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import edu.byu.rpg.RpgGame;
import edu.byu.rpg.entities.base.Actor;
import edu.byu.rpg.entities.effects.Shadow;
import edu.byu.rpg.graphics.AnimationManager;
import edu.byu.rpg.physics.Body;
import edu.byu.rpg.physics.Collideable;
import edu.byu.rpg.physics.World;

/**
 * Enemy for testing out collisions/hurting player
 */
public class Scarab extends Actor implements Collideable {

    private AnimationManager anims;
    private Shadow shadow;

    private final float accelConst = 1f;

    private final float dirTime = 0.9f;
    private float dirClock;

    private float health;

    public Scarab(RpgGame game, World world, int x, int y) {
        super(game, world, new Body(x, y, 11, 8, 45, 16));
        // add to enemies collision group
        world.add(World.Type.ENEMY, this);

        // init body
        body.maxSpeed = 2f;

        // init animations and shadow
        anims = new AnimationManager(game);
        anims.add("scarab_stand", 1, 7, 10);
        shadow = new Shadow(game, game.assets.getTexture("effects/shadow_64"), body);

        // setup direction and direction timer.
        dirClock = dirTime;

        // setup random direction
        setRandomAcceleration();

        // init health
        health = 5;
    }

    @Override
    public void update(float delta) {
        // update position, etc.
        super.update(delta);

        // movement timer
        if (dirClock < 0) {
            setRandomAcceleration();
            dirClock = dirTime;
        } else {
            dirClock -= delta;
        }

        // check for collisions with other enemies, change direction if hit.
        // (this prevents overlap)
        if (world.collideCheck(World.Type.ENEMY, body)) {
            setRandomAcceleration();
        }

        // set animation
        if (body.velocity.x > 0) {
            anims.faceRight();
        } else {
            anims.faceLeft();
        }
        anims.play("scarab_stand", true);
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

    private void setRandomAcceleration() {
        float x = (float)Math.random() * accelConst;
        x *= (Math.random() > 0.5) ? -1 : 1;

        float y = (float)Math.random() * accelConst;
        y *= (Math.random() > 0.5) ? -1 : 1;

        body.acceleration.set(x, y);
    }

    @Override
    public void destroy() {
        super.destroy();
        shadow.destroy();
        world.remove(this);
    }
}
