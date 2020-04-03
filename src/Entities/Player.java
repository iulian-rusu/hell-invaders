package Entities;

import Assets.PlayerAssets;
import Assets.ProjectileAssets;
import Audio.AudioManager;
import Entities.Projectiles.Projectile;
import Entities.Projectiles.ProjectileFactory;
import Entities.Projectiles.ProjectileType;
import Events.SFXEvent;

import java.awt.*;

public class Player extends Entity {
    public static final int PLAYER_H = 140;
    public static final int PLAYER_W = 120;
    public static final int PLAYER_X = 35;
    public static final int PLAYER_Y = 420;
    public static final int DEFAULT_HEALTH = 100;
    public static final int DEFAULT_MANA = 100;

    private int health = DEFAULT_HEALTH;
    private int mana = DEFAULT_MANA;
    private int level=1;
    private ProjectileType currentProjetile = ProjectileType.FIRE;
    private int projectileDamage = 20;
    private int numProjectiles = 2;
    private int critChance = 0;

    public Player() {
        frameCount = -1;
        AddObserver(AudioManager.GetInstance());
    }

    @Override
    public void Update() {
        super.Update();
    }

    @Override
    public void Draw(Graphics g) {
        g.drawImage(PlayerAssets.player_frames[(frameCount / 8) % 4], PLAYER_X, PLAYER_Y, null);
    }

    public Projectile[] FireProjectile(Point p) {
        Point to = new Point(p.x, p.y - Projectile.PROJECTILE_HEIGHT / 2);
        NotifyAllObservers(new SFXEvent(SFXEvent.PLAY_SPELL_SHOOT));
        return ProjectileFactory.MakeProjectile(currentProjetile,
                new Point(PLAYER_X + 100, PLAYER_Y), to, projectileDamage, numProjectiles, critChance);
    }

    public void SetProjectileDamage(int d) {
        projectileDamage = d;
    }

    public void SetNumProjectiles(int d) {
        numProjectiles = d;
    }

    public void SetCritChange(int d) {
        critChance = d;
    }

    public void SetProjectileType(ProjectileType t) {
        currentProjetile = t;
    }
}
