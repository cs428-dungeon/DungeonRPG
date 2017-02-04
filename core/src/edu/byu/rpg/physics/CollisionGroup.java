package edu.byu.rpg.physics;

import java.util.ArrayList;

/**
 * A list of {@link Body} instances, with extra methods added for collision checks.
 * Similar entities (all player bullets, all enemies, all spikes, etc.) are meant
 * to be stored in collision groups so that we can quickly check to see if, for
 * example, a player has collided with any enemy, or if any enemy has collided with
 * any player bullet.
 */
public class CollisionGroup extends ArrayList<Body> {

    /**
     * Returns any {@link Body} in this group which is colliding with <tt>otherBody</tt>.
     * @param otherBody A physics body which you are checking against this group.
     * @return A {@link Body} that overlaps <tt>otherBody</tt>.  Null if nothing in this
     * group is overlapping <tt>otherBody</tt>.
     */
    public Body getCollidingBody(Body otherBody) {
        for (int i = 0; i < size(); i++) {
            Body body = get(i);
            if (body.collide(otherBody)) {
                return body;
            }
        }
        return null;
    }

    /**
     * Checks for a collision against this group without returning the colliding body.
     * @param otherBody A physics body which you are checking against this group.
     * @return <tt>true</tt> if <tt>otherBody</tt> is overlapping some {@link Body} in this group, <tt>false</tt> if no.
     */
    public boolean checkForCollision(Body otherBody) {
        return getCollidingBody(otherBody) != null;
    }
}
