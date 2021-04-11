package Entities.CollidableEntities.Projectiles;

import Assets.Images.ProjectileAssets;
import Entities.Player;

import java.awt.*;

/**
 * @brief Implements the most basic projectile type with no additional effects.
 */
public class FireProjectile extends Projectile {
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
    public FireProjectile(int x, int y, double xVelocity, double yVelocity, double angle, long damage, int critChance) {
        super(x, y, xVelocity, yVelocity, angle, damage, critChance);
        textureBox.width = PROJECTILE_WIDTH * 2;
        textureBox.height = PROJECTILE_HEIGHT * 3 / 4;
    }

    @Override
    public void Draw(Graphics g) {
        DrawTexture(ProjectileAssets.fireProjectiles[(frameCount / 15) % 2], g);
    }
}
