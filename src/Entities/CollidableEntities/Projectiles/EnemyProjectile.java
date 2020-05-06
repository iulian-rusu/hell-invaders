package Entities.CollidableEntities.Projectiles;

import Assets.Images.ProjectileAssets;
import Entities.Player;
import Game.GlobalReferences;

import java.awt.*;

public class EnemyProjectile extends Projectile {

    public EnemyProjectile(int x, int y, double xVelocity, double yVelocity, double angle, long damage, int critChance) {
        super(x, y, xVelocity, yVelocity, angle, damage, critChance);
    }

    @Override
    public void Update() {
        super.Update();
        if (x <= Player.PLAYER_X + Player.PLAYER_W + hitBox.width) {
            isActive = false;
            GlobalReferences.player.TakeDamage(damage);
        }
    }

    @Override
    public void Draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.rotate(angle);
        //change coordinates to account for g2d rotation
        int xTransform = (int) ((x + textureBox.width * 0.5) * Math.cos(-angle) - (y + textureBox.height * 0.5) * Math.sin(-angle) - textureBox.width * 0.5);
        int yTransform = (int) ((x + textureBox.width * 0.5) * Math.sin(-angle) + (y + textureBox.height * 0.5) * Math.cos(-angle) - textureBox.height * 0.5);
        g2d.drawImage(ProjectileAssets.enemyProjectiles[(frameCount / 15) % 2], xTransform, yTransform, textureBox.width, textureBox.height, null);
        g2d.rotate(-angle);
    }
}
