package Entities.CollidableEntities.Projectiles;

import Assets.Images.ProjectileAssets;
import GUI.Text.GUITextComponent;

import java.awt.*;

/**
 * @brief Implements a projectile type that critically hits for four times the normal damage.
 */
public class ArcaneProjectile extends Projectile {
    public static final int CRIT_AMPLIFIER = 4;///< Overrides the critical amplifier in the base class.

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
    public ArcaneProjectile(int x, int y, double xVelocity, double yVelocity, double angle, long damage, int critChance) {
        super(x, y, xVelocity, yVelocity, angle, damage, critChance);
    }

    @Override
    public GUITextComponent GetCombatText() {
        GUITextComponent ans = super.GetCombatText();
        ans.SetColor(hasCrit ? Color.RED : Color.WHITE);
        return ans;
    }

    @Override
    protected long GetCriticalDamage(long damage) {
        return damage * CRIT_AMPLIFIER;
    }

    @Override
    public void Draw(Graphics g) {
        DrawTexture(ProjectileAssets.arcaneProjectiles[frameCount / 10], g);
    }
}
