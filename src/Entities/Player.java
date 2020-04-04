package Entities;

import Assets.PlayerAssets;
import Audio.AudioManager;
import Entities.CollidableEntities.Enemies.Enemy;
import Entities.CollidableEntities.Projectiles.Projectile;
import Entities.CollidableEntities.Projectiles.ProjectileFactory;
import Entities.CollidableEntities.Projectiles.ProjectileType;
import EventSystem.Events.AudioEvent;
import EventSystem.Events.CombatEvent;
import EventSystem.Events.GameEvent;
import EventSystem.Observer;
import Game.Game;

import java.awt.*;

public class Player extends Entity implements Observer {
    public static final int PLAYER_H = 140;
    public static final int PLAYER_W = 120;
    public static final int PLAYER_X = 35;
    public static final int PLAYER_Y = 420;
    public static final int DEFAULT_HEALTH = 100;
    public static final int DEFAULT_MANA = 100;
    public static final int DEFAULT_EXPERIENCE_GAIN = (10 + 5 * Game.DIFFICULTY);

    private int health = DEFAULT_HEALTH * (4 - Game.DIFFICULTY);
    private int mana = DEFAULT_MANA;
    private int level;
    private ProjectileType currentProjetile = ProjectileType.FIRE;
    private int projectileDamage = 10;
    private int numProjectiles = 1;
    private int critChance = 0;
    private int experience = 0;

    public Player() {
        level = 0;
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

    public Projectile[] ShootProjectile(Point p) {
        Point to = new Point(p.x, p.y - Projectile.PROJECTILE_HEIGHT / 2);
        NotifyAllObservers(AudioEvent.PLAY_SPELL_SHOOT);
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

    public int GetLevel() {
        return level;
    }

    public void SetLevel(int l) {
        level = l;
    }

    public int GetHealth() {
        return health;
    }

    @Override
    public void OnNotify(GameEvent e) {
        if (e.GetType() != GameEvent.GameEventType.CombatEvent) {
            return;
        }
        switch ((CombatEvent) e) {
            case MONSTER_DEATH:
                experience += DEFAULT_EXPERIENCE_GAIN * (int) (Math.pow(1 + level, 1.5));
                break;
            case MONSTER_ATTACK:
                health -= Enemy.DEFAULT_DAMAGE + 5 * (int)(Math.pow(1 + level, 1.8));
        }
    }
}
