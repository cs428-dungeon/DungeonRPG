package edu.byu.rpg.entities.player.weapons.basic;

import com.badlogic.gdx.utils.Pool;
import edu.byu.rpg.RpgGame;
import edu.byu.rpg.entities.player.weapons.base.PlayerBullet;
import edu.byu.rpg.entities.player.weapons.base.PlayerWeapon;
import edu.byu.rpg.physics.World;

/**
 * Starter Weapon for the player.  Shoots {@link BasicBullet}s
 */
public class BasicWeapon extends PlayerWeapon {

    private String fireSound = "bullet";

    public BasicWeapon(final RpgGame game, final World world) {
        super(game);
        bulletPool = new Pool<PlayerBullet>() {
            @Override
            protected PlayerBullet newObject() {
                return new BasicBullet(game, world, bulletPool);
            }
        };
    }

    @Override
    protected void fireBullets(float x, float y, float xDir, float yDir) {
        PlayerBullet bullet = bulletPool.obtain();
        game.audio.playSound(fireSound);
        bullet.init(x, y, xDir, yDir, damage);
    }
}
