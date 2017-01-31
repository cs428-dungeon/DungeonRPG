package edu.byu.rpg.physics;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * The axis-aligned bounding box used by any entities who need to check for collisions.
 *
 * @see <a href="http://mattmakesgames.tumblr.com/post/127890619821/towerfall-physics">Matt Thorson's Blog</a>
 */
public class Body {

    /** The width and height of this body's hitbox. */
    public Vector2 size;
    /** The world x and y coordinates of this body. */
    public Vector2 position;

    /**
     * The x and y coordinates of this body in relation to the object's origin.
     * This property allows us to easily have hitboxes that are different sizes from the sprite animation.
     * For example, we'll want the Player's hitbox to be a little bit smaller than the spritesheet dimension.
     */
    public Vector2 offset;

    /** The x and y velocity of this body. */
    public Vector2 velocity;
    /** The x and y acceleration of this body. */
    public Vector2 acceleration;
    /** The x and y friction of this body. */
    public Vector2 friction;

    /**
     * Sets the dimensions of this body, with no offset.  All other properties default to 0.
     * @param x The world x-position of the body.
     * @param y The world y-position of the body.
     * @param width The width of the body.
     * @param height The height of the body.
     */
    public Body(int x, int y, int width, int height) {
        position = new Vector2(x, y);
        size = new Vector2(width, height);
        offset = Vector2.Zero;

        reset();
    }

    /**
     * Sets the dimensions of this body, with the specified hitbox offset.  All other properties default to 0.
     * @param x The world x-position of the body.
     * @param y The world y-position of the body.
     * @param ox The hitbox x-offset.
     * @param oy The hitbox y-offset.
     * @param width The width of the hitbox.
     * @param height The height of the hitbox.
     */
    public Body(int x, int y, int ox, int oy, int width, int height) {
        position = new Vector2(x, y);
        size = new Vector2(width, height);
        offset = new Vector2(ox, oy);

        reset();
    }

    /**
     * Checks if this body is overlapping another body.
     * @param otherBody The other {@link Body} to check for overlap with.
     * @return True if overlapping, false if not overlapping.
     */
    public boolean overlaps(Body otherBody) {
        Rectangle rect = new Rectangle(position.x + offset.x, position.y + offset.y, size.x, size.y);
        Rectangle otherRect = new Rectangle(
                otherBody.position.x + otherBody.offset.x,
                otherBody.position.y + otherBody.offset.y,
                otherBody.size.x,
                otherBody.size.y
        );

        return rect.overlaps(otherRect);
    }

    /**
     * Resets all movement to 0.  Does not affect position, dimensions, or hitbox offset.
     */
    public void reset() {
        velocity = Vector2.Zero;
        acceleration = Vector2.Zero;
        friction = Vector2.Zero;
    }

}
