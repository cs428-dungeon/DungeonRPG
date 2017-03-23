package edu.byu.rpg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import edu.byu.rpg.RpgGame;
import edu.byu.rpg.entities.base.Solid;
import edu.byu.rpg.entities.enemies.AI.Attacks.AttackType;
import edu.byu.rpg.entities.enemies.AI.Movement.MovementType;
import edu.byu.rpg.entities.enemies.bosses.BossType;
import edu.byu.rpg.entities.enemies.standard.MonsterType;
import edu.byu.rpg.entities.enemies.controllers.AIController;
import edu.byu.rpg.entities.enemies.controllers.EnemyController;
import edu.byu.rpg.entities.player.Player;
import edu.byu.rpg.input.InputManager;
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

    /** Local instance of player, used for camera following. */
    private Player player;

    /** Map width and height, for camera clamping. Initialize to view size, but
     * these will be reset when the map is loaded/generated. */
    private int mapWidth = RpgGame.VIRTUAL_WIDTH;
    private int mapHeight = RpgGame.VIRTUAL_HEIGHT;

    /**
     * Loads the player into the first room of the dungeon.
     *
     * @param game Our main game class.
     */
    public PlayScreen(final RpgGame game) {
        super(game);
        loadMap("floor1/0");
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

        moveCamera(delta);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        mapRenderer.setView(camera);
        mapRenderer.render();

        game.batch.begin();
        game.engine.update(delta);
        game.batch.end();
    }

    /**
     * Clamps the camera to the edge of the map, if the camera is out-of-bounds.  Also clamps camera to integer value,
     * so that we don't have sub-pixel movement.
     */
    private void clampCamera() {
        if (camera.position.x > mapWidth - (camera.viewportWidth / 2)) {
            camera.position.x = mapWidth - (camera.viewportWidth / 2);
        } else if (camera.position.x < camera.viewportWidth / 2) {
            camera.position.x = camera.viewportWidth / 2;
        }
        if (camera.position.y > mapHeight - (camera.viewportHeight / 2)) {
            camera.position.y = mapHeight - (camera.viewportHeight / 2);
        } else if (camera.position.y < camera.viewportHeight / 2) {
            camera.position.y = camera.viewportHeight / 2;
        }
//
//        camera.position.x = Math.round(camera.position.x);
//        camera.position.y = Math.round(camera.position.y);
    }

    /**
     * Smoothly moves the {@link PlayScreen#camera} to follow the {@link PlayScreen#player}.
     * @param delta The time between frames.
     */
    private void moveCamera(float delta) {
        // Get offset from the where player is aiming so that the camera "leads" the player.
        float offsetX = InputManager.getRightXAxis() * 30f;
        float offsetY = InputManager.getRightYAxis() * 30f;

        // move camera smoothly using a linear interpolation constant (lerp)
        float lerp = 4f * delta;
        Vector2 playerPos = player.body.position;
        camera.position.x += (playerPos.x + offsetX - camera.position.x) * lerp;
        camera.position.y += (playerPos.y + offsetY - camera.position.y) * lerp;

        // clamp camera position to edges of map
        clampCamera();
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

        // get width and height of map in pixels (for camera clamping)
        MapProperties props = map.getProperties();
        int tileMapWidth = props.get("width", Integer.class);
        int tileMapHeight = props.get("height", Integer.class);
        int tilePxWidth = props.get("tilewidth", Integer.class);
        int tilePxHeight = props.get("tileheight", Integer.class);
        mapWidth = tileMapWidth * tilePxWidth;
        mapHeight = tileMapHeight * tilePxHeight;

        // Loads objects from tiled map
        try {
            // load player
            for (TiledMapTileMapObject playerTile : map.getLayers().get("player").getObjects().getByType(TiledMapTileMapObject.class)) {
                player = new Player(game, world, (int)playerTile.getX(), (int)playerTile.getY());

                // center camera on player right away, and clamp to edges of room
                camera.position.x = player.body.position.x;
                camera.position.y = player.body.position.y;
                clampCamera();
            }

            // TODO: need to create an enemy controller object that spawns a random enemy, given map location;
            //create an AIController and give it an attackAI and a movementAI;
            AIController aiController = new AIController();
            aiController.addMovementAI(MovementType.RANDOM);
            aiController.addMovementAI(MovementType.BOUNCE);
            aiController.addMovementAI(MovementType.FOLLOW);
            aiController.addMovementAI(MovementType.STATIONARY);
            aiController.addAttackAI(AttackType.ONE_BULLET);
            aiController.addAttackAI(AttackType.THREE_BULLET);
            aiController.addAttackAI(AttackType.HOMING_BULLET);
            aiController.addAttackAI(AttackType.FIRE_TRAIL);
            aiController.addAttackAI(AttackType.EIGHT_SHOT);
            aiController.addAttackAI(AttackType.BOUNCING_BULLET);
            // create enemyController with the enemy tiles in it.
            EnemyController enemyController = new EnemyController(aiController, map.getLayers().get("enemies").getObjects().getByType(TiledMapTileMapObject.class));
            // add the types of monsters the enemyController should be able to spawn.
            enemyController.addEnemy(MonsterType.SCARAB);
            // spawn the random monsters
            enemyController.spawnRandomMonsters(game, world);
            //enemyController.spawnBoss(game, world, map.getLayers().get("enemies").getObjects().getByType(TiledMapTileMapObject.class).first(), BossType.BABI);

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