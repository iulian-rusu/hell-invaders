package Entities.CollidableEntities.Projectiles;

import java.awt.*;

/**
 * @brief Class that creates and returns a collection of projectiles when a spell cast event happens.
 */
public class ProjectileFactory {
    /**
     * Creates ad returns a collection of projectiles that were shot.
     * <p>
     * The player has the option to shoot multiple projectiles at the same time.
     * Each projectile will fly at a slight angle offset relative to the line connecting the point of the player and the target point.
     * This factory method is also used for enemy projectiles. The number of enemy projectiles shot at once depends on game difficulty.
     *
     * @param type           The type of the projectile to be created.
     * @param from           The Point from which the projectiles will be shot.
     * @param to             The point towards which the projectiles were shot.
     * @param damage         The damage dealt be each projectile.
     * @param numProjectiles The number of projectiles shot at once.
     * @param critChance     The critical hit chance of each projectile.
     * @return A Java array containing all the projectiles.
     */
    public static Projectile[] MakeProjectile(ProjectileType type, Point from, Point to, long damage, int numProjectiles, int critChance) {
        Projectile[] ans = new Projectile[numProjectiles];
        double[] angles = new double[numProjectiles];
        double[] vx = new double[numProjectiles];
        double[] vy = new double[numProjectiles];

        double dx = to.x - from.x;
        double dy = to.y - from.y;
        double middleAngle = Math.atan(dy / dx);

        double half;
        if (numProjectiles % 2 == 1) {
            half = Math.floorDiv(numProjectiles, 2);
        } else {
            half = (numProjectiles - 1) / 2.0;
        }
        // Each projectile will be at a slight offset from the center one
        for (int i = 0; i < numProjectiles; ++i) {
            double dangle = (half - i) * 0.05;
            angles[i] = dangle + middleAngle;
            vx[i] = Projectile.DEFAULT_VELOCITY * Math.cos(angles[i]);
            vy[i] = Projectile.DEFAULT_VELOCITY * Math.sin(angles[i]);
        }

        switch (type) {
            case FIRE:
                for (int i = 0; i < numProjectiles; ++i) {
                    ans[i] = new FireProjectile(from.x, from.y, vx[i], vy[i], angles[i], damage, critChance);
                }
                return ans;
            case FROST:
                for (int i = 0; i < numProjectiles; ++i) {
                    ans[i] = new FrostProjectile(from.x, from.y, vx[i], vy[i], angles[i], damage, critChance);
                }
                return ans;
            case ARCANE:
                for (int i = 0; i < numProjectiles; ++i) {
                    ans[i] = new ArcaneProjectile(from.x, from.y, vx[i], vy[i], angles[i], damage, critChance);
                }
                return ans;
            case ENEMY:
                for (int i = 0; i < numProjectiles; ++i) {
                    ans[i] = new EnemyProjectile(from.x, from.y, -vx[i], -vy[i], angles[i], damage, 0);
                }
                return ans;
            default:
                return null;
        }
    }
}
