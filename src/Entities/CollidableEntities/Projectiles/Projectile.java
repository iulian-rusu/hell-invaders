package Entities.CollidableEntities.Projectiles;

import Assets.FontAssets;
import Entities.CollidableEntities.CollidableEntity;
import Entities.CollidableEntities.Enemies.Enemy;
import GUI.Text.GUIText;
import GUI.Text.GUITextComponent;
import GUI.Text.GUITimedDecorator;
import GameSystems.NumberSystem.LargeNumberHandler;

import java.awt.*;
import java.util.Random;

/**
 * @brief Class that implements projectiles.
 */
public abstract class Projectile extends CollidableEntity {
    public static final int PROJECTILE_WIDTH = 30;///< The default width of the projectile texture box.
    public static final int PROJECTILE_HEIGHT = 30;///< The default height of the projectile texture box.

    public static final int CRIT_AMPLIFIER = 2;///< The amount by which a critical hit is amplified.
    public static final int DEFAULT_VELOCITY = 20;///< The magnitute of the default velocity of a projectile.

    protected static final Random RNG = new Random(System.currentTimeMillis());///< Random object to decide which hits will be critical.

    protected double xVelocity;///< The double precision velocity on the x axix.
    protected double yVelocity;///< The double precision velocity on the y axix.
    protected double angle;///< The double precision angle between the velocity vector and the x axis.
    protected double fx;///< The double precision x coordinate of the top-left corner of the hitbox.
    protected double fy;///< The double precision y coordinate of the top-left corner of the hitbox.
    protected long damage;///< The damage dealt by the current projectile.
    protected int critChance;///< The chance to critically strike in percentages.
    protected boolean hasCrit = false;///< Flag which indicates if the current projectile has crit.

    /**
     * Constructor with parameters.
     *
     * @param x          The x coordinate of the top-left corner of the hitbox.
     * @param y          The y coordinate of the top-left corner of the hitbox.
     * @param xVelocity  The double precision velocity on the x axix.
     * @param yVelocity  The double precision velocity on the y axix.
     * @param angle      The double precision angle between the velocity vector and the x axis.
     * @param damage     The long integer damage dealt by the current projectile.
     * @param critChance The chance to critically strike in percentages.
     */
    public Projectile(int x, int y, double xVelocity, double yVelocity, double angle, long damage, int critChance) {
        super(x, y, PROJECTILE_WIDTH, PROJECTILE_HEIGHT, PROJECTILE_WIDTH, PROJECTILE_HEIGHT);
        // Doubles will store the psotiion more precisely
        fx = x;
        fy = y;
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        this.angle = angle;
        this.damage = damage;
        this.critChance = critChance;
        isActive = true;
    }

    /**
     * Called when a projectile collides with an enemy.
     *
     * @param e A reference to the enemy that was hit.
     */
    public void DealDamage(Enemy e) {
        if (Math.abs(RNG.nextInt()) % 100 < critChance) {
            e.TakeDamage(GetCriticalDamage(damage));
            hasCrit = true;
        } else {
            e.TakeDamage(damage);
        }
    }

    /**
     * Called to obtain a reference to a GUITextComponent object which represents the amount of damage dealt by the projectile.
     * The text will become inactice through the use of a GUITimedDecorator after a short delay.
     *
     * @return A reference to the text object.
     * @see GUI.Text.GUITextComponent;
     * @see GUI.Text.GUITimedDecorator;
     */
    public GUITextComponent GetCombatText() {
        long dmg = hasCrit ? GetCriticalDamage(damage) : damage;
        String val = LargeNumberHandler.ParseLongInt(dmg);
        int textX = hitBox.x + RNG.nextInt() % 15;
        int textY = hitBox.y + RNG.nextInt() % 15;
        int size = hasCrit ? 60 : 50;
        //using decorator design pattern to add a timer for the text
        GUITextComponent ans = new GUITimedDecorator(new GUIText(val, textX, textY, size), 30);
        ans.SetFont(FontAssets.mainFontItalic);
        ans.SetColor(hasCrit ? Color.YELLOW : Color.WHITE);
        return ans;
    }

    /**
     * Returns the amout a critical hit deals.
     *
     * @param damage A long representing the normal damage of the projectile.
     * @return A long representing the critical damage.
     */
    protected long GetCriticalDamage(long damage) {
        return damage * CRIT_AMPLIFIER;
    }

    @Override
    public void Update() {
        super.Update();
        fx += xVelocity;
        fy += yVelocity;
        x = (int) Math.round(fx);
        y = (int) Math.round(fy);
    }

    @Override
    public int GetHitBoxYOffset() {
        return 0;
    }
}
