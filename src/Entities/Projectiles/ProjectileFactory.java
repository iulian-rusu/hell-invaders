package Entities.Projectiles;

import java.awt.*;

public class ProjectileFactory {
    public static Projectile[] MakeProjectile(ProjectileType type, Point from, Point to, int damage, int numProjectiles, int critChance) {
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

        for (int i = 0; i < numProjectiles; ++i) {
            double dangle = (half - i) * 0.05;
            angles[i] = dangle + middleAngle;
            vx[i] = Projectile.DEFAULT_VELOCITY * Math.cos(angles[i]);
            vy[i] = Projectile.DEFAULT_VELOCITY * Math.sin(angles[i]);
        }

        switch (type) {
            case FIRE:
                for (int i = 0; i < numProjectiles; ++i) {
                    ans[i] = new FireProjectile(from.x, from.y, vx[i], vy[i], damage, critChance);
                }
                return ans;
            case FROST:
            case ARCANE:
            case ENEMY:
            default:
                return null;
        }
    }
}
