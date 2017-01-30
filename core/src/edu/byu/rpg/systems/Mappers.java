package edu.byu.rpg.systems;

import com.badlogic.ashley.core.ComponentMapper;
import edu.byu.rpg.components.DrawComponent;
import edu.byu.rpg.components.UpdateComponent;

/**
 * This is a class full of static {@link com.badlogic.ashley.core.ComponentMapper} instances for use within our game
 * engine systems.
 *
 * @see <a href="https://github.com/libgdx/ashley/wiki/How-to-use-Ashley#retrieving-components-with-componentmapper">Ashley Component Mappers</a>
 */
public class Mappers {
    /**
     * The component mapper for {@link UpdateComponent}.
     */
    public static ComponentMapper<UpdateComponent> um = ComponentMapper.getFor(UpdateComponent.class);

    /**
     * The component mapper for {@link DrawComponent}.
     */
    public static ComponentMapper<DrawComponent> dm = ComponentMapper.getFor(DrawComponent.class);
}
