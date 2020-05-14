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
        Graphics2D g2d = (Graphics2D) g;
        g2d.rotate(angle);
        // Change coordinates with rotation matrix to account for g2d rotation
        int xTransform = (int) (hitBox.x * Math.cos(-angle) - (hitBox.y + textureBox.height * 0.5) * Math.sin(-angle) - textureBox.width * 0.5);
        int yTransform = (int) (hitBox.x * Math.sin(-angle) + (hitBox.y + textureBox.height * 0.5) * Math.cos(-angle) - textureBox.height * 0.25);
        g2d.drawImage(ProjectileAssets.frostProjectiles[(frameCount / 20) % 3], xTransform, yTransform, textureBox.width, textureBox.height, null);
        g2d.rotate(-angle);
    }
}
