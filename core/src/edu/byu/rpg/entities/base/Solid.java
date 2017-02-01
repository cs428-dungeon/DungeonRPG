package edu.byu.rpg.entities.base;

import edu.byu.rpg.physics.Body;
import edu.byu.rpg.physics.World;

/**
 * Solids are basically just static physics bodies used to represent solid level geometry.
 * They are automatically added to {@linkplain edu.byu.rpg.physics.World the physics world}
 * that you pass in to the constructor.
 */
public class Solid {

    /** Local instance of physics body. */
    public Body body;

    public Solid(World world, Body body) {
        world.addSolid(this);
        this.body = body;
    }
}
