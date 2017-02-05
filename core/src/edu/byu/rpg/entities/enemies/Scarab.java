package edu.byu.rpg.entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import edu.byu.rpg.RpgGame;
import edu.byu.rpg.entities.base.Actor;
import edu.byu.rpg.physics.Body;
import edu.byu.rpg.physics.ICollideable;
import edu.byu.rpg.physics.World;

/**
 * Enemy for testing out collisions/hurting player
 */
public class Scarab extends Actor implements ICollideable {

    private Texture scarabTexture;

    public Scarab(RpgGame game, World world, int x, int y) {
        super(game, world, new Body(x, y, 11, 8, 45, 16));
        // add to enemies collision group
        world.add(World.Type.ENEMY, this);
        scarabTexture = game.assets.getTexture("scarab");
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void draw(float delta, SpriteBatch batch) {
        batch.draw(scarabTexture, body.position.x, body.position.y);
    }

    @Override
    public void takeDamage(float damage) {
        // take damage here.
    }
}
