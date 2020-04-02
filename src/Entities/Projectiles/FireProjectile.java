package Entities.Projectiles;

import Assets.ProjectileAssets;

import java.awt.*;

public class FireProjectile extends Projectile {

    public FireProjectile(int x, int y, double xVelocity, double yVelocity, double angle, int damage) {
        super(x, y, xVelocity, yVelocity, angle, damage);
    }

    @Override
    public void Draw(Graphics g) {
        g.drawImage(ProjectileAssets.fire_projectile, (int)x, (int)y, hitBox.width, hitBox.height, null);
    }
}
