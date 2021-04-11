package Entities.CollidableEntities.Projectiles;

import Assets.Images.ProjectileAssets;
import Entities.Player;
import Game.GlobalReferences;

import java.awt.*;

/**
 * @brief Implements the type of projectiles shot by an enemy.
 */
public class EnemyProjectile extends Projectile {
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
    public EnemyProjectile(int x, int y, double xVelocity, double yVelocity, double angle, long damage, int critChance) {
        super(x, y, xVelocity, yVelocity, angle, damage, critChance);
    }

    @Override
    public void Update() {
        super.Update();
        if (x <= Player.PLAYER_X + Player.PLAYER_W + hitBox.width) {
            isActive = false;
            GlobalReferences.GetPlayer().TakeDamage(damage);
        }
    }

    @Override
    public void Draw(Graphics g) {
        DrawTexture(ProjectileAssets.enemyProjectiles[(frameCount / 15) % 2], g);
    }
}
