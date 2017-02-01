package edu.byu.rpg.entities.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import edu.byu.rpg.RpgGame;
import edu.byu.rpg.entities.base.Actor;
import edu.byu.rpg.input.InputManager;
import edu.byu.rpg.physics.Body;
import edu.byu.rpg.physics.World;

/**
 * The player-controlled character.
 */
public class Player extends Actor {

    //TODO: replace with animation manager class when built.
    //TODO: separate animations - one for feet and one for upper body
    private Texture playerTexture;

    // physics constants
    private final float accel = 2.5f;


    public Player(RpgGame game, World world, int x, int y) {
        super(game, world, new Body(x, y, 8, 0, 16, 16));
        playerTexture = game.assets.getTexture("player_stand");
    }

    @Override
    public void update(float delta) {
        body.acceleration.x = accel * InputManager.getLeftXAxis();
        body.acceleration.y = accel * InputManager.getLeftYAxis();

        super.update(delta);
    }

    @Override
    public void draw(float delta, SpriteBatch batch) {
        batch.draw(playerTexture, body.position.x, body.position.y);
    }
}
