package edu.byu.rpg.physics;

import edu.byu.rpg.entities.base.Solid;

import java.util.ArrayList;

/**
 * Contains all of our game objects as lists of collision groups.
 * You can use this class to check for collisions by object type.
 */
public class World {

    /** All of our static solid level geometry. */
    private ArrayList<Solid> solids;

    /**
     * Initializes all collision group arrays as empty.
     */
    public World() {
        solids = new ArrayList<Solid>();
    }

    /**
     * Adds a solid to the "solids" collision group.
     * @param solid The solid body to add.
     */
    public void addSolid(Solid solid) {
        solids.add(solid);
    }

    /**
     * Removes a solid from the "solids" collision group.
     * @param solid The solid body to remove.
     */
    public void removeSolid(Solid solid) {
        solids.remove(solid);
    }

    /**
     * Checks for collisions against solids.
     * @param other The body you're checking against all solids.
     * @return The solid that collides with the body, null if no overlap.
     */
    public Solid collideWithSolid(Body other) {
        for (int i = 0; i < solids.size(); i++) {
            Solid solid = solids.get(i);
            if (solid.body.collide(other)) {
                return solid;
            }
        }

        return null;
    }
}
