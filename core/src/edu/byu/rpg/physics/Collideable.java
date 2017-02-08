package edu.byu.rpg.physics;

/**
 * The basis for an object that can be stored and processed by {@link World}.
 * An object that implements {@link Collideable} can handle post-collision logic,
 * such as flinching/taking damage, and so forth.
 */
public interface Collideable {

    /**
     * Checks whether or not this collideable is intersecting another hitbox.
     * @param otherBody The other {@link Body} to check for collisions.
     * @return <tt>true</tt> if colliding, <tt>false</tt> if not.
     */
    public boolean collide(Body otherBody);

    /**
     * Damages this object by <tt>damage</tt> amount.
     * @param damage The amount of damage to inflict on this object.
     */
    public void takeDamage(float damage);

}
