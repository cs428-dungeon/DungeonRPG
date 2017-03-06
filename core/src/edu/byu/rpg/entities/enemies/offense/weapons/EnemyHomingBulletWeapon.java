package edu.byu.rpg.entities.enemies.offense.weapons;

import com.badlogic.gdx.utils.Pool;
import edu.byu.rpg.RpgGame;
import edu.byu.rpg.entities.enemies.offense.attacks.EnemyHomingBullet;
import edu.byu.rpg.entities.enemies.offense.base.EnemyAttack;
import edu.byu.rpg.entities.enemies.offense.base.EnemyWeapon;
import edu.byu.rpg.physics.World;

import java.util.ArrayList;

/**
 * Created by Andrew on 2/14/2017.
 */
public class EnemyHomingBulletWeapon extends EnemyWeapon {

    private ArrayList<EnemyAttack> attackType;

    public EnemyHomingBulletWeapon(final RpgGame game, final World world) {
        super(game);
        this.setCooldownTime(0.0f);
        attackPool = new Pool<EnemyAttack>() {
            @Override
            protected EnemyAttack newObject() {
                return new EnemyHomingBullet(game, world, attackPool);
            }
        };
    }

    @Override
    protected void attack(float x, float y, float xDir, float yDir) {
        EnemyAttack attack = attackPool.obtain();
        attack.init(x, y, xDir, yDir, damage);
    }

    public void scale(float scaleAmount){
        final EnemyAttack attack = attackPool.obtain();
        attack.setMaxSpeed(attack.getMaxSpeed() * scaleAmount);
        attackPool = new Pool<EnemyAttack>() {
            @Override
            protected EnemyAttack newObject() {
                return attack;
            }
        };
    }
}
