package Entities;

import Assets.Images.PlayerAssets;
import Assets.Audio.AudioManager;
import Entities.CollidableEntities.Enemies.Enemy;
import Entities.CollidableEntities.Projectiles.Projectile;
import Entities.CollidableEntities.Projectiles.ProjectileFactory;
import Entities.CollidableEntities.Projectiles.ProjectileType;
import GameSystems.EventSystem.Events.AudioEvent;
import GameSystems.EventSystem.Events.CombatEvent;
import GameSystems.EventSystem.Events.GameEvent;
import GameSystems.EventSystem.Observer;
import GUI.GUIStatusBar;
import Game.Game;
import GameSystems.UpgradeSystem.ExperiencePanel;

import java.awt.*;

public class Player extends Entity implements Observer {
    //singleton class that implements a player controller
    //player position parameters
    public static final int PLAYER_H = 150;
    public static final int PLAYER_W = 125;
    public static final int PLAYER_X = 35;
    public static final int PLAYER_Y = 420;
    //health and healthbar parameters
    public static int GET_DEFAULT_HEALTH() {
        return 100 + (4 - Game.DIFFICULTY) * 50;
    }
    public static final int HEALTHBAR_HEIGHT = 20;
    public static final int HEALTHBAR_WIDTH = 250;
    public static final int HEALTHBAR_Y = 770;
    public static final int HEALTHBAR_X = 70;
    //mana parameters
    public static int GET_DEFAULT_MANA() { return 100 + (3 - Game.DIFFICULTY) * 25; }
    public static final int MANA_COST_PER_SHOOT = 10;
    public static final int MANA_REGEN_CHUNK = 3;
    public static final int MANA_REGEN_PERIOD = 30; //how many frames between mana regens
    public static final int MANABAR_Y = HEALTHBAR_Y + HEALTHBAR_HEIGHT + 10;
    //experience parameters
    public static final double EXPERIENCE_INCREMENT = 1.11;
    public static int GET_DEFAULT_EXPERIENCE_GAIN() { return (5 + 5 * Game.DIFFICULTY); }
    //combat parameters
    public static final long DEFAULT_DAMAGE=20L;

    private static Player instance = null;

    private final GUIStatusBar<Player> healthBar;
    private final GUIStatusBar<Player> manaBar;
    private int health = GET_DEFAULT_HEALTH();
    private int mana = GET_DEFAULT_MANA();
    private int level;//in the interval [1, 365]
    private ProjectileType currentProjetile;
    private int numProjectiles;
    private int critChance;
    private long projectileDamage;
    private long experience;

    public static Player GetInstance() {
        if (instance == null) {
            instance = new Player();
        }
        return instance;
    }

    private Player() {
        frameCount = -1;
        AddObserver(AudioManager.GetInstance());
        //create healthbar
        healthBar = new GUIStatusBar<>(this, Player::ProvideHealthData);
        healthBar.SetPosition(HEALTHBAR_X, HEALTHBAR_Y);
        healthBar.SetSize(HEALTHBAR_WIDTH, HEALTHBAR_HEIGHT);
        AddObserver(healthBar);
        //create mana bar
        manaBar = new GUIStatusBar<>(this, Player::ProvideManaData);
        manaBar.SetPosition(HEALTHBAR_X, MANABAR_Y);
        manaBar.SetSize(HEALTHBAR_WIDTH, HEALTHBAR_HEIGHT);
        manaBar.SetColor(Color.CYAN);
        AddObserver(manaBar);
        //add experience observer
        AddObserver(ExperiencePanel.GetInstance());

        ResetAllStats();
    }

    public void Init() {
        health = GET_DEFAULT_HEALTH();
        mana = GET_DEFAULT_MANA();
        NotifyAllObservers(CombatEvent.STATUS_BAR_RESET);
    }

    public void ResetAllStats() {
        //used when a new game is started, sets all stats to their base value
        numProjectiles = 1;
        projectileDamage = DEFAULT_DAMAGE;
        critChance = 0;
        currentProjetile = ProjectileType.FIRE;
        experience = 9999L;
        level = 1;
    }

    @Override
    public void Update() {
        super.Update();
        if (mana < GET_DEFAULT_MANA() && frameCount % MANA_REGEN_PERIOD == 0) {
            mana += MANA_REGEN_CHUNK;
            if (mana > GET_DEFAULT_MANA())
                mana = GET_DEFAULT_MANA();
            NotifyAllObservers(CombatEvent.STATUS_BAR_UPDATE);
        }
        if (health <= 0) {
            NotifyAllObservers(CombatEvent.LEVEL_LOSS);
        }
    }

    @Override
    public void Draw(Graphics g) {
        g.drawImage(PlayerAssets.player_frames[(frameCount / 8) % 4], PLAYER_X, PLAYER_Y, PLAYER_W, PLAYER_H, null);
        healthBar.Draw(g);
        manaBar.Draw(g);
    }

    public void TakeDamage(long damage) {
        health -= damage;
        if (health > 0) {
            NotifyAllObservers(AudioEvent.PLAY_OOF);
            NotifyAllObservers(CombatEvent.STATUS_BAR_UPDATE);
        }
    }

    public Projectile[] ShootProjectile(Point p) {
        if (mana < MANA_COST_PER_SHOOT) {
            return null;
        }
        mana -= MANA_COST_PER_SHOOT;
        NotifyAllObservers(currentProjetile.sfxEvent);
        NotifyAllObservers(CombatEvent.STATUS_BAR_UPDATE);
        //calculate coordinates of hitbox to center it at p -> subtract half widht and height from each coordinate
        Point to = new Point(p.x - Projectile.PROJECTILE_WIDTH / 2, p.y - Projectile.PROJECTILE_HEIGHT / 2);
        return ProjectileFactory.MakeProjectile(currentProjetile,
                new Point(PLAYER_X + 100, PLAYER_Y + 10), to, projectileDamage, numProjectiles, critChance);
    }

    @Override
    public void OnNotify(GameEvent e) {
        if (e.GetType() != GameEvent.GameEventType.CombatEvent) {
            return;
        }
        switch ((CombatEvent) e) {
            case ENEMY_DEATH:
                experience += (long) (GET_DEFAULT_EXPERIENCE_GAIN() * (Math.pow(EXPERIENCE_INCREMENT, level - 1)));
                //pass the notification to the experience panel
                NotifyAllObservers(CombatEvent.ENEMY_DEATH);
                break;
            case ENEMY_ATTACK:
                health -= Enemy.GET_DEFAULT_DAMAGE();
        }
    }

    public void SetProjectileDamage(long d) {
        projectileDamage = d;
    }

    public void SetNumProjectiles(int d) {
        numProjectiles = d;
    }

    public void SetCritChance(int d) {
        critChance = d;
    }

    public void SetProjectileType(ProjectileType t) {
        currentProjetile = t;
    }

    public void SetExperience(long exp){ experience=exp;}

    public int GetLevel() { return level; }

    public void SetLevel(int l) {
        level = l;
    }

    public int GetHealth() {
        return health;
    }

    public long GetExperience() {
        return experience;
    }

    public static Long ProvideHealthData(Player p) {
        return (long) p.health;
    }

    public static Long ProvideManaData(Player p) {
        return (long) p.mana;
    }
}
