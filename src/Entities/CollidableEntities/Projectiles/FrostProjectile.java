package Entities.CollidableEntities.Projectiles;

import Assets.Images.ProjectileAssets;
import Entities.CollidableEntities.Enemies.Enemy;

import java.awt.*;

public class FrostProjectile extends Projectile {
    //bonus effect parameters
    public static final int SLOW_FRAME_COUNT = 120;
    public static final double SLOW_PERCENTAGE = 0.3;

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
        //change coordinates to account for g2d rotation
        int xTransform = (int) (hitBox.x * Math.cos(-angle) - (hitBox.y + textureBox.height * 0.5) * Math.sin(-angle)- textureBox.width * 0.5);
        int yTransform = (int) (hitBox.x * Math.sin(-angle) + (hitBox.y + textureBox.height * 0.5) * Math.cos(-angle) - textureBox.height * 0.25);
        g2d.drawImage(ProjectileAssets.frost_projectiles[(frameCount / 20) % 3], xTransform, yTransform, textureBox.width, textureBox.height, null);
        g2d.rotate(-angle);
    }
}
