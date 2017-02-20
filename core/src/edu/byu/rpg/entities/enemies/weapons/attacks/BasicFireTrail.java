package edu.byu.rpg.entities.enemies.weapons.attacks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Pool;
import edu.byu.rpg.RpgGame;
import edu.byu.rpg.entities.enemies.weapons.base.EnemyAttack;
import edu.byu.rpg.physics.Body;
import edu.byu.rpg.physics.World;

/**
 * Created by Andrew on 2/15/2017.
 */
public class BasicFireTrail extends EnemyAttack {

    Texture bulletTexture;

    public BasicFireTrail(RpgGame game, World world, Pool<EnemyAttack> pool) {
        super(game, world, new Body(0, 0, 8, 8), pool, World.Type.ENEMY_TRAIL);
        bulletTexture = game.assets.getTexture("basic_bullet");
        this.body.maxSpeed = 0;
        setTimeToLive(4.0f);

    }

    @Override
    public void draw(float delta, SpriteBatch batch) {
        batch.draw(bulletTexture, body.position.x, body.position.y);
    }

    @Override
    public boolean collideCheck(){
        return (world.collideCheck(World.Type.PLAYER, body)
                || world.collideCheck(World.Type.ENEMY_TRAIL, body)
                || world.collideCheck(World.Type.SOLID, body));
        }
    }


