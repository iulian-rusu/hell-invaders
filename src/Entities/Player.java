package Entities;

import Assets.PlayerAssets;
import Audio.AudioManager;
import Entities.CollidableEntities.Enemies.Enemy;
import Entities.CollidableEntities.Projectiles.Projectile;
import Entities.CollidableEntities.Projectiles.ProjectileFactory;
import Entities.CollidableEntities.Projectiles.ProjectileType;
import EventSystem.Events.CombatEvent;
import EventSystem.Events.GameEvent;
import EventSystem.Observer;
import GUI.GUIStatusBar;
import Game.Game;

import java.awt.*;

public class Player extends Entity implements Observer {
    //singleton class that implements a player controller
    //player position constants
    public static final int PLAYER_H = 150;
    public static final int PLAYER_W = 125;
    public static final int PLAYER_X = 35;
    public static final int PLAYER_Y = 420;

    //health and healthbar constants
    public static int GET_DEFAULT_HEALTH() {
        return 100 + (4 - Game.DIFFICULTY) * 50;
    }

    public static final int HEALTHBAR_HEIGHT = 20;
    public static final int HEALTHBAR_WIDTH = 250;
    public static final int HEALTHBAR_Y = 770;
    public static final int HEALTHBAR_X = 70;

    //mana constants
    public static int GET_DEFAULT_MANA() {
        return 100 + (3 - Game.DIFFICULTY) * 25;
    }

    public static final int MANA_COST_PER_SHOOT = 10;
    public static final int MANA_REGEN_CHUNK = 3;
    public static final int MANA_REGEN_PERIOD = 30; //how many frames between mana regens
    public static final int MANABAR_Y = HEALTHBAR_Y + HEALTHBAR_HEIGHT + 10;
    //experience constants
    public static final double EXPERIENCE_DEPENDENCY_EXPONENT = 1.5;

    public static int GET_DEFAULT_EXPERIENCE_GAIN() {
        return (10 + 5 * Game.DIFFICULTY);
    }

    private static Player instance = null;

    private GUIStatusBar<Player> healthBar;
    private GUIStatusBar<Player> manaBar;
    private int health = GET_DEFAULT_HEALTH();
    private int mana = GET_DEFAULT_MANA();
    private int level;
    private ProjectileType currentProjetile = ProjectileType.ARCANE;
    private int projectileDamage;
    private int numProjectiles;
    private int critChance;
    private int experience;

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
        projectileDamage = 20;
        critChance = 0;
        currentProjetile = ProjectileType.FIRE;
        experience = 0;
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
    }

    @Override
    public void Draw(Graphics g) {
        g.drawImage(PlayerAssets.player_frames[(frameCount / 8) % 4], PLAYER_X, PLAYER_Y, PLAYER_W,PLAYER_H,null);
        healthBar.Draw(g);
        manaBar.Draw(g);
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
                new Point(PLAYER_X + 100, PLAYER_Y+10), to, projectileDamage, numProjectiles, critChance);
    }

    @Override
    public void OnNotify(GameEvent e) {
        if (e.GetType() != GameEvent.GameEventType.CombatEvent) {
            return;
        }
        switch ((CombatEvent) e) {
            case MONSTER_DEATH:
                experience += GET_DEFAULT_EXPERIENCE_GAIN() * (int) (Math.pow(1 + level, EXPERIENCE_DEPENDENCY_EXPONENT));
                break;
            case MONSTER_ATTACK:
                health -= Enemy.GET_DEFAULT_DAMAGE();
        }
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

    public static Integer ProvideHealthData(Player p) {
        return p.health;
    }

    public static Integer ProvideManaData(Player p) {
        return p.mana;
    }
}
