package edu.byu.rpg.components;

import com.badlogic.ashley.core.Component;
import edu.byu.rpg.entities.base.Drawable;

/**
 * This class is the drawable counterpart to {@link UpdateComponent}.  Objects that are registered
 * with {@link edu.byu.rpg.RpgGame#engine} that have this component will be automatically drawn
 * with each game engine loop.
 */
public class DrawComponent implements Component {

    /** The entity whose draw logic should be executed each frame */
    public Drawable drawable;

    /** The layer that {@link DrawComponent#drawable} should be drawn on.  Higher z-index objects
     * will be drawn first (in the back).  This values is by default 0.
     */
    public int zIndex;

    public DrawComponent(Drawable drawable) {
        this.drawable = drawable;
        zIndex = 0;
    }
}
