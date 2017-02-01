package edu.byu.rpg.entities.base;

import edu.byu.rpg.RpgGame;
import edu.byu.rpg.physics.Body;
import edu.byu.rpg.physics.World;
import edu.byu.rpg.tools.Utils;

/**
 * Actors are our main game objects.  Actors can overlap each other, but they never overlap any solids.
 *
 * @see <a href="http://mattmakesgames.tumblr.com/post/127890619821/towerfall-physics">Matt Thorson's Blog</a>
 */
public abstract class Actor extends DrawableEntity {

    /** This actor's physics body. */
    public Body body;

    /** Whether or not this body should collide with level geometry. Defaults to true.*/
    public boolean isSolid;

    /** Local instance of {@link World}, used for collision checking. */
    protected World world;

    /**
     * Set the physics body for this actor, and adds it to {@link RpgGame#engine} as a drawable entity.
     *
     * @param game Our main game class.
     * @param world The physics world (for collisions).
     * @param body The physics body/hitbox for this actor.
     */
    public Actor(RpgGame game, World world, Body body) {
        super(game);
        this.body = body;
        this.world = world;
        isSolid = true;
    }

    /**
     * Updates velocity and position based on acceleration and friction, so that moving your
     * game object is as easy as setting {@link Body#acceleration} and {@link Body#friction}.  Also updates
     * {@link DrawableEntity#drawComponent}'s {@link edu.byu.rpg.components.DrawComponent#zIndex} so that entities
     * higher up on the screen will be drawn behind entities that are near the bottom of the screen, creating
     * an illusion of depth.
     *
     * This method should be called at the end of any game object's overridden {@link UpdatableEntity#update(float)}
     * method.
     *
     * @param delta The time since the last frame.
     */
    @Override
    public void update(float delta) {
        body.updateVelocity(delta);

        if (isSolid) {
            // update position using velocity (checking for solids in 1px steps)
            moveX(delta);
            moveY(delta);
        } else {
            body.updatePosition(delta);
        }

        // update z-index (for depth illusion)
        drawComponent.zIndex = (int)body.position.x;
    }

    /**
     * Subroutine of {@link Actor#update(float)}, used for moving in the x-direction
     * without overlapping solids.
     *
     * @param delta The time since the last frame.
     */
    private void moveX(float delta) {
        float prevX = body.position.x;
        body.position.x += body.velocity.x * delta * Body.PIXELS_PER_METER;

        // check for collision, and move back by steps if necessary
        while (world.collideWithSolid(body) != null) {
            body.position.x = Utils.approach(body.position.x, prevX, 1);
        }
    }

    /**
     * Subroutine of {@link Actor#update(float)}, used for moving in the y-direction
     * without overlapping solids.
     *
     * @param delta The time since the last frame.
     */
    private void moveY(float delta) {
        float prevY = body.position.y;
        body.position.y += body.velocity.y * delta * Body.PIXELS_PER_METER;

        // check for collision and move back if necessary
        while (world.collideWithSolid(body) != null) {
            body.position.y = Utils.approach(body.position.y, prevY, 1);
        }
    }


}
