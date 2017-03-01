package edu.byu.rpg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import edu.byu.rpg.RpgGame;
import edu.byu.rpg.entities.base.Solid;
import edu.byu.rpg.entities.enemies.AI.Attacks.AttackType;
import edu.byu.rpg.entities.enemies.AI.Movement.MovementType;
import edu.byu.rpg.entities.enemies.MonsterType;
import edu.byu.rpg.entities.enemies.controllers.AIController;
import edu.byu.rpg.entities.enemies.controllers.EnemyController;
import edu.byu.rpg.entities.player.Player;
import edu.byu.rpg.physics.Body;
import edu.byu.rpg.physics.World;

/**
 * This is the main game play screen for our game.
 */
public class PlayScreen extends ScreenBase {

    /** Physics world, which contains all the collide-able actors in this level. */
    private World world;

    /** Automatically draws any tiles (not objects) from our Tiled map to the screen. */
    private OrthogonalTiledMapRenderer mapRenderer;

    private String music = "floor 1";

    /**
     * Loads the player into the first room of the dungeon.
     *
     * @param game Our main game class.
     */
    public PlayScreen(final RpgGame game) {
        super(game);
        loadMap("floor1/1");
    }

    /**
     * In addition to {@linkplain ScreenBase#render(float) clearing the screen}, this function
     * updates our camera, renders our Tiled map to the screen, and updates/draws all entities
     * registered to {@link RpgGame#engine}.
     *
     * @param delta Time since the last frame.
     */
    @Override
    public void render(float delta) {
        super.render(delta);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        mapRenderer.setView(camera);
        mapRenderer.render();

        game.batch.begin();
        game.engine.update(delta);
        game.batch.end();
    }

    // TODO: Refactor map loading/generating logic into its own class.

    /**
     * This function loads any objects from our Tiled map into the game's world.
     *
     * @param name The name of the map (without filepath or extension).
     */
    private void loadMap(String name) {
        world = new World();
        TiledMap map = game.assets.getMap(name);
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        // Loads objects from tiled map
        try {
            // load player
            for (TiledMapTileMapObject playerTile : map.getLayers().get("player").getObjects().getByType(TiledMapTileMapObject.class)) {
                new Player(game, world, (int)playerTile.getX(), (int)playerTile.getY());
            }

            // TODO: need to create an enemy controller object that spawns a random enemy, given map location;
            //create an AIController and give it an attackAI and a movementAI;
            AIController aiController = new AIController();
            //aiController.addMovementAI(MovementType.RANDOM);
            //aiController.addMovementAI(MovementType.BOUNCE);
            aiController.addMovementAI(MovementType.FOLLOW);
            //aiController.addAttackAI(AttackType.ONE_BULLET);
            //aiController.addAttackAI(AttackType.THREE_BULLET);
            //aiController.addAttackAI(AttackType.HOMING_BULLET);
            //aiController.addAttackAI(AttackType.FIRE_TRAIL);
            aiController.addAttackAI(AttackType.EIGHT_SHOT);
            // create enemyController with the enemy tiles in it.
            EnemyController enemyController = new EnemyController(aiController, map.getLayers().get("enemies").getObjects().getByType(TiledMapTileMapObject.class));
            // add the types of monsters the enemyController should be able to spawn.
            enemyController.addEnemy(MonsterType.SCARAB);
            // spawn the random monsters
            enemyController.spawnRandomMonsters(game, world);

            // load solid level geometry
            for (MapObject rectMapObj : map.getLayers().get("solids").getObjects().getByType(RectangleMapObject.class)) {
                // get rectangle and make solid level geometry out of it
                Rectangle rect = ((RectangleMapObject) rectMapObj).getRectangle();
                new Solid(world, new Body((int) rect.x, (int) rect.y, (int) rect.width, (int) rect.height));
            }
            game.audio.playMusic(music, true);

        } catch (NullPointerException e) {
            Gdx.app.debug(this.getClass().getSimpleName(), e.getMessage());
        }
    }

}