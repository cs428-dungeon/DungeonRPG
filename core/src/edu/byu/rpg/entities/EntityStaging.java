package edu.byu.rpg.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import edu.byu.rpg.RpgGame;
import edu.byu.rpg.entities.base.DrawableEntity;

/**
 * To be deleted.  This is just used for testing core engine update loop.
 */
public class EntityStaging extends DrawableEntity {

    private final String TAG = this.getClass().getSimpleName();
    private int count = 0;

    Texture playerTexture;

    public EntityStaging(RpgGame game) {
        super(game);
        playerTexture = game.assets.getTexture("player_stand");
    }

    @Override
    public void update(float delta) {
        count++;
        Gdx.app.debug(TAG, "Current frame count is " + count);

        if (count >= 150) {
            Gdx.app.debug(TAG, "Self-destructing because frame count is greater than 10");
            destroy();
        }
    }

    @Override
    public void draw(float delta, SpriteBatch batch) {
        batch.draw(playerTexture, count, count);
        Gdx.app.debug(TAG, "Drawing player at x:" + count + ", y:" + count);
    }
}
