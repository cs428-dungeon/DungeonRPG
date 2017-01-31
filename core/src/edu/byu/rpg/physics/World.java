package edu.byu.rpg.physics;

import java.util.ArrayList;

/**
 * Contains all of our game objects as lists of collision groups.
 * You can use this class to check for collisions by object type.
 */
public class World {

    /** All of our static solid level geometry. */
    public ArrayList<Body> solids;

    /**
     * Initializes all collision group arrays as empty.
     */
    public World() {
        solids = new ArrayList<Body>();
    }
}
