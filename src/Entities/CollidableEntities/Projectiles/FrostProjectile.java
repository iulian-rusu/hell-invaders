package Entities.CollidableEntities.Projectiles;

import Assets.ProjectileAssets;
import Entities.CollidableEntities.Enemies.Enemy;

import java.awt.*;

public class FrostProjectile extends Projectile {

    public static final int SLOW_FRAME_COUNT=120;
    public static final double SLOW_PERCENTAGE=0.3;

    public FrostProjectile(int x, int y, double xVelocity, double yVelocity, int damage, int critChance) {
        super(x, y, xVelocity, yVelocity, damage, critChance);
    }

    @Override
    public void DealDamage(Enemy e) {
        super.DealDamage(e);
        e.GetSlowed();
    }

    @Override
    public void Draw(Graphics g) {
        g.drawImage(ProjectileAssets.frost_projectile, x, y, hitBox.width, hitBox.height, null);
    }
}
