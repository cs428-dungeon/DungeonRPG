package edu.byu.rpg.entities.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import edu.byu.rpg.RpgGame;
import edu.byu.rpg.entities.base.Actor;
import edu.byu.rpg.entities.effects.Shadow;
import edu.byu.rpg.physics.Body;
import edu.byu.rpg.physics.Collideable;
import edu.byu.rpg.physics.World;

/**
 * Enemy for testing out collisions/hurting player
 */
public class Scarab extends Actor implements Collideable {

    private Texture scarabTexture;
    private Shadow shadow;

    public Scarab(RpgGame game, World world, int x, int y) {
        super(game, world, new Body(x, y, 11, 8, 45, 16));
        // add to enemies collision group
        world.add(World.Type.ENEMY, this);
        scarabTexture = game.assets.getTexture("scarab");
        shadow = new Shadow(game, game.assets.getTexture("effects/shadow_64"), body);
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
        Gdx.app.debug(this.getClass().getSimpleName(), "Just took damage.");
    }
}
