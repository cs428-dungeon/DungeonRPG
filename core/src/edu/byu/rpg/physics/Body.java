package edu.byu.rpg.physics;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import edu.byu.rpg.tools.Utils;

/**
 * The axis-aligned bounding box used by any entities who need to check for collisions.
 *
 * @see <a href="http://mattmakesgames.tumblr.com/post/127890619821/towerfall-physics">Matt Thorson's Blog</a>
 */
public class Body {

    /** Scaling factor, used to convert our physics constants from meters to pixels. */
    public static float PIXELS_PER_METER = 16f;

    /** The width and height of this body's hitbox in pixels. */
    public Vector2 size;
    /** The world x and y coordinates of this body in pixels. */
    public Vector2 position;

    /**
     * The x and y coordinates of this body in relation to the object's origin (in pixels).
     * This property allows us to easily have hitboxes that are different sizes from the sprite animation.
     * For example, we'll want the Player's hitbox to be a little bit smaller than the spritesheet dimension.
     */
    public Vector2 offset;

    /** The x and y velocity of this body in m/s. */
    public Vector2 velocity;
    /** The x and y acceleration of this body in m/s-squared. */
    public Vector2 acceleration;
    /** The x and y friction of this body. Not a friction constant, just a "negative" acceleration.
     * Default is 3 m/s-squared. */
    public float friction;
    /** The maximum rate at which this body will move in any direction.  The default is 5.8 m/s. */
    public float maxSpeed;

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
     * Applies friction and acceleration to velocity.
     * @param deltaTime The time since the last frame.
     */
    public void updateVelocity(float deltaTime) {
        // apply acceleration, cap at max speed
        velocity.x += acceleration.x * deltaTime * PIXELS_PER_METER;
        velocity.y += acceleration.y * deltaTime * PIXELS_PER_METER;
        if (velocity.len() > maxSpeed) {
            velocity.setLength(maxSpeed);
        }

        // only apply friction if player isn't accelerating.
        if (acceleration.len() == 0) {
            velocity.setLength(Utils.approach(velocity.len(), 0, friction * deltaTime * PIXELS_PER_METER));
        }
    }

    /**
     * Applies velocity to position.  (Does not do any collision checking, update is instantaneous.)
     * @param deltaTime The time since the last frame
     */
    public void updatePosition(float deltaTime) {
        position.x += velocity.x * deltaTime * PIXELS_PER_METER;
        position.y += velocity.y * deltaTime * PIXELS_PER_METER;
    }

    /**
     * Checks if this body is overlapping another body.
     * @param otherBody The other {@link Body} to check for overlap with.
     * @return True if overlapping, false if not overlapping.
     */
    public boolean collide(Body otherBody) {
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
        maxSpeed = 5.8f;
        velocity = new Vector2();
        acceleration = new Vector2();
        friction = 3f;
    }

}
