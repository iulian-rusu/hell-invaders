package Entities;

import Assets.PlayerAssets;
import Assets.ProjectileAssets;
import Entities.Projectiles.Projectile;
import Entities.Projectiles.ProjectileFactory;
import Entities.Projectiles.ProjectileType;

import java.awt.*;

public class Player extends Entity {
    private static final int PLAYER_X = 35;
    private static final int PLAYER_Y = 420;
    private static final int DEFAULT_HEALTH = 100;
    private static final int DEFAULT_MANA = 100;

    private static ProjectileType currentProjetile = ProjectileType.FIRE;
    private static int currentDamage = 20;
    private int health = DEFAULT_HEALTH;
    private int mana = DEFAULT_MANA;

    public Player() {
        frameCount = -1;
    }

    @Override
    public void Update() {
        super.Update();
    }

    @Override
    public void Draw(Graphics g) {
        g.drawImage(PlayerAssets.player_frames[(frameCount / 8) % 4], PLAYER_X, PLAYER_Y, null);
    }

    public Projectile FireProjectile(Point p) {
        Point to = new Point(p.x, p.y - ProjectileAssets.PROJECTILE_HEIGHT / 2);
        return ProjectileFactory.MakeProjectile(currentProjetile,
                new Point(PLAYER_X + 100, PLAYER_Y ), to, currentDamage);
    }
}
