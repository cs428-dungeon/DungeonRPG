package edu.byu.rpg.physics;

import edu.byu.rpg.entities.player.Player;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Contains all of our game objects as lists of collision groups.
 * You can use this class to check for collisions by object type.
 */
public class World {

    /** Defines collision group classes. */
    public static enum Type { SOLID, PLAYER, PLAYER_BULLET, ENEMY, ENEMY_BULLET, ENEMY_TRAIL }

    /** Private map of type to collision group. */
    private HashMap<Type, ArrayList<Collideable>> groups;

    /**
     * Initializes the collision groups map.
     */
    public World() {
        groups = new HashMap<Type, ArrayList<Collideable>>();
        // create new collision group for each type.
        for (Type type : Type.values()) {
            groups.put(type, new ArrayList<Collideable>());
        }
    }

    /**
     * Adds a collideable object to the group of the specified type.
     * @param type The type of group you want to add this object to.
     * @param collideable The object to add to the collision group.
     */
    public void add(Type type, Collideable collideable) {
        ArrayList<Collideable> group = groups.get(type);
        group.add(collideable);
    }

    /**
     * Removes a collideable object from the group of the specified type.
     * @param type The type of group you want to remove this object from.
     * @param collideable The object to remove from the collision group.
     */
    public void remove(Type type, Collideable collideable) {
        ArrayList<Collideable> group = groups.get(type);
        group.remove(collideable);
    }

    /**
     * Removes a collideable object from any group that they are a part of.
     * @param collideable The object to remove from the world.
     */
    public void remove(Collideable collideable) {
        for (Type type : Type.values()) {
            remove(type, collideable);
        }
    }

    /**
     * Checks for collisions against the group of the specified type.
     * @param type The type of the group you want to check for collisions against.
     * @param otherBody The {@link Body} object you want to check against the group.
     * @return The first {@link Collideable} to overlap <tt>otherBody</tt>, or <tt>null</tt>.
     */
    public Collideable collide(Type type, Body otherBody) {
        ArrayList<Collideable> group = groups.get(type);
        for (Collideable collideable : group) {
            if (collideable.collide(otherBody)) return collideable;
        }
        return null;
    }

    /**
     * Checks for collisions against the group of the specified type.
     * @param type The type of the group you want to check for collisions against.
     * @param otherBody The {@link Body} object you want to check against the group.
     * @return <tt>true</tt> if a collision happened, <tt>false</tt> if no.
     */
    public boolean collideCheck(Type type, Body otherBody) {
        return (collide(type, otherBody) != null);
    }

    /**
     * Checks for collisions against the group of the specified type.
     * @param otherBody The {@link Body} the body you want to get the distance to the player.
     * @return <tt>true</tt> if a collision happened, <tt>false</tt> if no.
     */
    public float xDistanceToPlayer(Body otherBody){
        ArrayList<Collideable> group = groups.get(Type.PLAYER);
        for (Collideable collideable : group) {
            Player player = (Player)collideable;
            return player.body.getCenterX() - otherBody.getCenterX();
        }
        return 0.0f;
    }

    public float yDistanceToPlayer(Body otherBody) {
        ArrayList<Collideable> group = groups.get(Type.PLAYER);
        for (Collideable collideable : group) {
            Player player = (Player)collideable;
            return player.body.getCenterY() - otherBody.getCenterY();
        }
        return 0.0f;
    }
//    /**
//     * Checks for collisions against all the specified groups.  <strong>Note:</strong> This
//     * function will only return the first <tt>collideable</tt> to intersect <tt>otherBody</tt>,
//     * and will execute collision checking against the groups in the order specified by the
//     * <tt>types</tt> parameter.
//     * @param types An array of types to check against.
//     * @param otherBody The {@link Body} object you want to check against the groups.
//     * @return The first {@link Collideable} to overlap <tt>otherBody</tt>, or <tt>null</tt>.
//     */
//    public Collideable collide(Type[] types, Body otherBody) {
//        for (int i = 0; i < types.length; i++) {
//            Collideable collisionObj = collide(types[i], otherBody);
//            if (collisionObj != null) return collisionObj;
//        }
//        return null;
//    }
}
