package edu.byu.rpg.entities.base;

import edu.byu.rpg.physics.Body;
import edu.byu.rpg.physics.ICollideable;
import edu.byu.rpg.physics.World;

/**
 * Solids are basically just static physics bodies used to represent solid level geometry.
 * They are automatically added to {@linkplain edu.byu.rpg.physics.World the physics world}
 * that you pass in to the constructor.
 */
public class Solid implements ICollideable {

    /** Local instance of physics body. */
    public Body body;

    public Solid(World world, Body body) {
        world.add(World.Type.SOLID, this);
        this.body = body;
    }

    @Override
    public boolean collide(Body otherBody) {
        return body.collide(otherBody);
    }

    /**
     * Solid level geometry doesn't take any damage, so this function doesn't do anything.
     * @param damage The amount of damage to inflict on this object.
     */
    @Override
    public void takeDamage(float damage) {}
}
