package Entities.CollidableEntities.Projectiles;

import Assets.Images.ProjectileAssets;
import Entities.CollidableEntities.Enemies.Enemy;

import java.awt.*;

/**
 * @brief Implements a projectile type that slows enemies hit.
 */
public class FrostProjectile extends Projectile {
    public static final int SLOW_FRAME_COUNT = 120;///< The number of frames while the target is slowed.
    public static final double SLOW_PERCENTAGE = 0.7;///< The amount by which each projectile slows. Does not stack.

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
    public FrostProjectile(int x, int y, double xVelocity, double yVelocity, double angle, long damage, int critChance) {
        super(x, y, xVelocity, yVelocity, angle, damage, critChance);
        textureBox.width = PROJECTILE_WIDTH * 2;
        textureBox.height = PROJECTILE_HEIGHT * 3 / 4;
    }

    @Override
    public void DealDamage(Enemy e) {
        super.DealDamage(e);
        e.GetSlowed();
    }

    @Override
    public void Draw(Graphics g) {
        DrawTexture(ProjectileAssets.frostProjectiles[(frameCount / 20) % 3], g);
    }
}
