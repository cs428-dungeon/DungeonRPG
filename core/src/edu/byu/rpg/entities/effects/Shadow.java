package edu.byu.rpg.entities.effects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import edu.byu.rpg.RpgGame;
import edu.byu.rpg.entities.base.DrawableEntity;
import edu.byu.rpg.physics.Body;

/**
 * Shadows don't do anything but follow objects around.
 * They have a very high zIndex, so they are drawn first.
 */
public class Shadow extends DrawableEntity {

    /** Local instance of body that this shadow will follow. */
    private Body body;

    /** The texture image of this shadow */
    private Texture texture;

    /** X-position of the shadow */
    private float x;
    /** Y-position of the shadow */
    private float y;

    /** Sets the body that this shadow will follow.
     * @param game Our main game class
     * @param texture the shadow texture (there are different sizes to choose from)
     * @param body The body that this shadow will follow.
     */
    public Shadow(RpgGame game, Texture texture, Body body) {
        super(game);
        this.texture = texture;
        this.body = body;
        drawComponent.zIndex = 1000;
    }

    /**
     * Follows {@link Shadow#body}
     * @param delta The time since the last frame.
     */
    @Override
    public void update(float delta) {
        x = body.position.x;
        y = body.position.y;
    }

    /**
     * Draws the shadow
     * @param delta The time since last frame.
     * @param batch The {@link SpriteBatch} used to draw ({@link RpgGame#batch}, automatically passed)
     */
    @Override
    public void draw(float delta, SpriteBatch batch) {
        batch.draw(texture, x, y);
    }
}
