package Entities.CollidableEntities.Projectiles;

import Assets.Images.ProjectileAssets;

import java.awt.*;

public class FireProjectile extends Projectile {

    public FireProjectile(int x, int y, double xVelocity, double yVelocity, double angle, long damage, int crithChance) {
        super(x, y, xVelocity, yVelocity, angle, damage, crithChance);
        textureBox.width = PROJECTILE_WIDTH * 2;
        textureBox.height = PROJECTILE_HEIGHT * 3 / 4;
    }

    @Override
    public void Draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.rotate(angle);
        //change coordinates to account for g2d rotation
        int xTransform = (int) (hitBox.x * Math.cos(-angle) - (hitBox.y + textureBox.height * 0.5) * Math.sin(-angle)- textureBox.width * 0.5);
        int yTransform = (int) (hitBox.x * Math.sin(-angle) + (hitBox.y + textureBox.height * 0.5) * Math.cos(-angle) - textureBox.height * 0.25);
        g2d.drawImage(ProjectileAssets.fire_projectiles[(frameCount / 15) % 2], xTransform, yTransform, textureBox.width, textureBox.height, null);
        g2d.rotate(-angle);
    }
}
