package edu.byu.rpg.entities.enemies.offense.attacks;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Pool;
import edu.byu.rpg.RpgGame;
import edu.byu.rpg.entities.enemies.offense.base.EnemyAttack;
import edu.byu.rpg.graphics.AnimationManager;
import edu.byu.rpg.physics.Body;
import edu.byu.rpg.physics.World;

/**
 * Created by Andrew on 2/15/2017.
 */
public class BasicFireTrail extends EnemyAttack {

    /** Animation manager for this fireball */
    private AnimationManager animations;

    public BasicFireTrail(RpgGame game, World world, Pool<EnemyAttack> pool) {
        super(game, world, new Body(6, 3, 8, 10), pool, World.Type.ENEMY_TRAIL);
        animations = new AnimationManager(game);
        animations.add("blue_flame", 1, 7, 12);
        animations.play("blue_flame", true);
        this.body.maxSpeed = 0;
        setTimeToLive(4.0f);
    }

    /**
     * Calls {@link EnemyAttack#update(float)}, but also updates the
     * drawing layer for this fireball to give it depth.
     * @param delta The time since the last frame.
     */
    @Override
    public void update(float delta) {
        super.update(delta);
        drawComponent.zIndex = (int)body.position.y;
    }

    /**
     * Invokes the draw method on {@link BasicFireTrail#animations}.
     * @param delta The time since last frame.
     * @param batch The {@link SpriteBatch} used to draw ({@link RpgGame#batch}, automatically passed)
     */
    @Override
    public void draw(float delta, SpriteBatch batch) {
        animations.draw(delta, body.position.x, body.position.y);
    }

    @Override
    public boolean collideCheck(){
        return (world.collideCheck(World.Type.PLAYER, body)
                || world.collideCheck(World.Type.ENEMY_TRAIL, body)
                || world.collideCheck(World.Type.SOLID, body));
        }
    }


