package edu.byu.rpg.entities.enemies.weapons.base;

import com.badlogic.gdx.utils.Pool;
import edu.byu.rpg.RpgGame;
import edu.byu.rpg.entities.base.UpdatableEntity;
import edu.byu.rpg.entities.enemies.weapons.attacks.EnemyHomingBullet;

/**
 * Contains an attack pool and any logic needed to fire off attacks from the enemy.
 */
public abstract class EnemyWeapon extends UpdatableEntity {

    /** This weapon's pool of {@link EnemyAttack}s */
    protected Pool<EnemyAttack> attackPool;

    /**
     * Cooldown time.  {@link EnemyWeapon#cooldownClock} is reset to this
     * whenever the weapon is fired.  Defaults to 0.35.
     */
    private float cooldownTime;
    /** Cooldown clock.  If greater than 0, weapon will not fire attacks. */
    private float cooldownClock;

    /** The amount of damage each attack does. Defaults to 1 */
    protected float damage;

    /**
     * Adds this weapon to {@link RpgGame#engine} for automatic updating.
     * Inherited constructors <strong>must</strong> initialize the
     * {@link EnemyWeapon#attackPool} or else we'll get problems.
     * @param game Our main game class.
     */
    public EnemyWeapon(RpgGame game) {
        super(game);
        this.cooldownTime = 0.35f;
        damage = 1;
    }

    /**
     * Sets the cooldown time for this weapon.  Useful if the player gets
     * an upgrade that increases rate of fire attacks.
     * @param cooldownTime The new cooldown time.
     */
    public void setCooldownTime(float cooldownTime) {
        this.cooldownTime = cooldownTime;
    }

    /**
     * Gets the cooldown time for this weapon.  Useful if you want to
     * decrease the cooldown time by a fraction, for example when the enemy
     * scales up to increases rate of fire by a certain percentage.
     * @return The current cooldown time for this weapon.
     */
    public float getCooldownTime() {
        return cooldownTime;
    }

    /**
     * Sets the damage for each attack of this weapon.  Useful if the enemy
     * gets an upgrade.
     * @param damage The new damage stat.
     */
    public void setDamage(float damage) {
        this.damage = damage;
    }

    /**
     * Gets the damage for each attck of this weapon.  Useful for upgrading
     * weapons, for example when the player gets an upgrade that increases damage
     * by a certain percentage.
     * @return Current damage stat.
     */
    public float getDamage() {
        return damage;
    }

    /**
     * Checks cooldown and then fires if cooldown has is at 0.  This function
     * is called whenever the enemy attacks
     * @param x The starting x-position for the attack.
     * @param y The starting y-position for the attack.
     * @param xDir The x-component of the direction vector the player is firing in.
     * @param yDir The y-component of the direction vector the player is firing in.
     */
    public void fire(float x, float y, float xDir, float yDir) {
        if (cooldownClock > 0) {
            return;
        } else {
            cooldownClock = cooldownTime;
        }

        attack(x, y, xDir, yDir);
    }

    /**
     * launches the attack (or attacks).  This function is called automatically by
     * {@link EnemyWeapon#attack(float, float, float, float)}
     * @param x The starting x-position for the attack.
     * @param y The starting y-position for the attack.
     * @param xDir The x-component of the direction vector the player is firing in.
     * @param yDir The y-component of the direction vector the player is firing in.
     */
    protected abstract void attack(float x, float y, float xDir, float yDir);

    /**
     * Does nothing more than update cooldown timers.
     * @param delta The time since the last frame.
     */
    @Override
    public void update(float delta) {
        if (cooldownClock >= 0) {
            cooldownClock -= delta;
        }
    }
}
