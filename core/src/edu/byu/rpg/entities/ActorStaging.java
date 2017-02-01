package edu.byu.rpg.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import edu.byu.rpg.RpgGame;
import edu.byu.rpg.entities.base.Actor;
import edu.byu.rpg.physics.Body;
import edu.byu.rpg.physics.World;

/**
 * To be deleted.  This is just used for testing core engine update loop.
 */
public class ActorStaging extends Actor {

    private final String TAG = this.getClass().getSimpleName();

    private Texture playerTexture;

    public ActorStaging(RpgGame game, World world, int x, int y) {
        super(game, world, new Body(x, y, 8, 0, 16, 16));
        playerTexture = game.assets.getTexture("player_stand");
    }

    @Override
    public void update(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            Gdx.app.debug(TAG, "Left key pressed.");
            body.acceleration.x = -2.5f;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            Gdx.app.debug(TAG, "Right key pressed.");
            body.acceleration.x = 2.5f;
        } else {
            body.acceleration.x = 0;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            Gdx.app.debug(TAG, "Down key pressed.");
            body.acceleration.y = -2.5f;
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            Gdx.app.debug(TAG, "Up key pressed.");
            body.acceleration.y = 2.5f;
        } else {
            body.acceleration.y = 0;
        }

        super.update(delta);
    }

    @Override
    public void draw(float delta, SpriteBatch batch) {
        batch.draw(playerTexture, body.position.x, body.position.y);
    }
}
