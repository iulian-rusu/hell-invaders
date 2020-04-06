package Entities.CollidableEntities.Projectiles;

import Assets.ProjectileAssets;

import java.awt.*;

public class FireProjectile extends Projectile {

    public FireProjectile(int x, int y, double xVelocity, double yVelocity, int damage, int crithChance) {
        super(x, y, xVelocity, yVelocity, damage, crithChance);
    }

    @Override
    public void Draw(Graphics g) {
        g.drawImage(ProjectileAssets.fire_projectiles[frameCount / 30], x, y, hitBox.width, hitBox.height, null);
    }
}
