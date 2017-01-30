package edu.byu.rpg.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import edu.byu.rpg.RpgGame;
import edu.byu.rpg.components.DrawComponent;

import java.util.Comparator;

/**
 * This class hasn't been implemented yet...
 */
//TODO: needs to inherit from SortedIteratingSystem
public class DrawSystem extends SortedIteratingSystem {

    /** Private instance of {@link RpgGame#batch} for drawing */
    private SpriteBatch batch;

    /**
     * Automatically sets up the comparator for sorting, and sets the priority to 1, meaning
     * this system will be updated after {@link UpdateSystem} and before {@link DestroySystem}.
     * Also stores a private instance of {@link RpgGame#batch} for drawing.
     * @param game Our main game class.
     */
    public DrawSystem(RpgGame game) {
        super(Family.all(DrawComponent.class).get(), new ZComparator(), 1);
        batch = game.batch;
    }

    /**
     * Draws each entity to the screen by calling {@link edu.byu.rpg.entities.base.DrawableEntity#draw(float, SpriteBatch)}.
     * @param entity A single entity to draw (passed automatically to this function by {@link DrawSystem}.
     * @param deltaTime The time since last frame.
     */
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        DrawComponent drawComponent = Mappers.dm.get(entity);
        drawComponent.drawable.draw(deltaTime, batch);
    }

    /**
     * This is the custom comparator class used for sorting {@link DrawSystem}
     */
    private static class ZComparator implements Comparator<Entity> {
        @Override
        public int compare(Entity e1, Entity e2) {
            return (int)Math.signum(Mappers.dm.get(e1).zIndex - Mappers.dm.get(e2).zIndex);
        }
    }
}
