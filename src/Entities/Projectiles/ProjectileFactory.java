package Entities.Projectiles;

import java.awt.*;

public class ProjectileFactory {
    public static Projectile MakeProjectile(ProjectileType type, Point from, Point to, int damage) {
        double dx=to.x-from.x;
        double dy=to.y-from.y;
        double mag = Math.sqrt(dx*dx + dy*dy);
        double xVelocity = Projectile.DEFAULT_VELOCITY * dx / mag;
        double yVelocity = Projectile.DEFAULT_VELOCITY * dy / mag;
        double angle = Math.acos( dx / mag);

        switch (type) {
            case FIRE:
                return new FireProjectile(from.x, from.y, xVelocity, yVelocity, angle, damage);
            case FROST:
            case ARCANE:
            default:
                return null;
        }
    }
}
