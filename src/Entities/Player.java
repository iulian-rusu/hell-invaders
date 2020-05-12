package Entities;

import Assets.Audio.AudioManager;
import Assets.Images.PlayerAssets;
import Entities.CollidableEntities.Enemies.Enemy;
import Entities.CollidableEntities.Projectiles.Projectile;
import Entities.CollidableEntities.Projectiles.ProjectileFactory;
import Entities.CollidableEntities.Projectiles.ProjectileType;
import GUI.GUIStatusBar;
import Game.Game;
import Game.GlobalReferences;
import GameSystems.EventSystem.Events.AudioEvent;
import GameSystems.EventSystem.Events.CombatEvent;
import GameSystems.EventSystem.Events.GameEvent;
import GameSystems.EventSystem.Observer;

import java.awt.*;

/**
 *  @brief Implements the main player of the game.
 */
public class Player extends Entity implements Observer {
    public static final int PLAYER_H = 150;///< The default height of the player.
    public static final int PLAYER_W = 125;///< The default width of the player.
    public static final int PLAYER_X = 35;///< The default x coordinate of the top-left corner of the player.
    public static final int PLAYER_Y = 420;///< The default y coordinate of the top-left corner of the player.

    /**
     * Returns the default health based on the game difficulty.
     *
     * @return An int representing the health of the player.
     */
    public static int GET_DEFAULT_HEALTH() {
        return 100 + (4 - Game.difficulty) * 50;
    }

    public static final int HEALTHBAR_HEIGHT = 20;///< The default healthbar height.
    public static final int HEALTHBAR_WIDTH = 250;///< The default healthbar width.
    public static final int HEALTHBAR_Y = 770;///< The default y coordinate of the top-left corner of the healthbar.
    public static final int HEALTHBAR_X = 70;///< The default x coordinate of the top-left corner of the healthbar.

    /**
     * Returns the default mana based on the game difficulty.
     *
     * @return An int representing the mana of the player.
     */
    public static int GET_DEFAULT_MANA() {
        return 100 + (3 - Game.difficulty) * 25;
    }

    public static final int MANA_COST_PER_SHOOT = 10;///< The default mana spent on each spell cast.
    public static final int MANA_REGEN_CHUNK = 3;///< The default amount of mana regenerated at once.
    public static final int MANA_REGEN_PERIOD = 30; ///< The default delay between mana chunks that regenerate.
    public static final int MANABAR_Y = HEALTHBAR_Y + HEALTHBAR_HEIGHT + 10;///< The default y coordinate of the top-left corner of the manabar.

    public static final double EXPERIENCE_INCREMENT = 1.11;///< The increment in experience gain each level.

    /**
     * Returns the default experience gain based on the game difficulty.
     *
     * @return An int representing the experience gain of the player.
     */
    public static int GET_DEFAULT_EXPERIENCE_GAIN() {
        return (5 + 5 * Game.difficulty);
    }

    public static final long DEFAULT_DAMAGE = 20L;///< The default damage of the player.

    private final GUIStatusBar<Player> healthBar;///< The healthbar of the player.
    private final GUIStatusBar<Player> manaBar;///< The manabar of the player.
    private int health = GET_DEFAULT_HEALTH();///< The current health of the player.
    private int mana = GET_DEFAULT_MANA();///< The current mana of the player.
    private int level;///< The current level of the player. The game supports levels from 1 to 365 inclusive.
    private ProjectileType currentProjetile;///< The current type of spell that the player casts.
    private int numProjectiles;///< The current number of projectiles shot on each spell cast.
    private int critChance;///< The critical hit chance of the player.
    private long projectileDamage;///< The damage dealt by each projectile.
    private int projectileDamageLevel;///< The level of the projectile damage upgrade.
    private long experience;///< The current player experience.

    /**
     * Constructor without parameters.
     */
    public Player() {
        frameCount = -1;
        AddObserver(AudioManager.GetInstance());
        // Create healthbar
        healthBar = new GUIStatusBar<>(this, player -> (long) player.health);
        healthBar.SetPosition(HEALTHBAR_X, HEALTHBAR_Y);
        healthBar.SetSize(HEALTHBAR_WIDTH, HEALTHBAR_HEIGHT);
        AddObserver(healthBar);
        // Create mana bar
        manaBar = new GUIStatusBar<>(this, player -> (long) player.mana);
        manaBar.SetPosition(HEALTHBAR_X, MANABAR_Y);
        manaBar.SetSize(HEALTHBAR_WIDTH, HEALTHBAR_HEIGHT);
        manaBar.SetColor(Color.CYAN);
        AddObserver(manaBar);
        // Add experience observer
        AddObserver(GlobalReferences.GetExperiencePanel());
    }

    /**
     * Gets called when a new level is started.
     */
    public void Init() {
        health = GET_DEFAULT_HEALTH();
        mana = GET_DEFAULT_MANA();
        NotifyAllObservers(CombatEvent.STATUS_BAR_RESET);
    }

    /**
     * Used to reset the player stats to their base values when a new game is started.
     */
    public void ResetAllStats() {
        numProjectiles = 1;
        projectileDamage = DEFAULT_DAMAGE;
        projectileDamageLevel = 1;
        critChance = 0;
        currentProjetile = ProjectileType.FIRE;
        experience = 0L;
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
        g.drawImage(PlayerAssets.playerFrames[(frameCount / 8) % 4], PLAYER_X, PLAYER_Y, PLAYER_W, PLAYER_H, null);
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

    /**
     * Called when the player casts a spell.
     *
     * @param p The point towards which the spell was cast.
     * @return An array representing the projectiles that were shot.
     */
    public Projectile[] ShootProjectile(Point p) {
        if (mana < MANA_COST_PER_SHOOT) {
            return null;
        }
        mana -= MANA_COST_PER_SHOOT;
        NotifyAllObservers(currentProjetile.sfxEvent);
        NotifyAllObservers(CombatEvent.STATUS_BAR_UPDATE);
        //calculate coordinates of hitbox to center it at p -> subtract half width and height from each coordinate
        Point to = new Point(p.x - Projectile.PROJECTILE_WIDTH / 2, p.y - Projectile.PROJECTILE_HEIGHT / 2);
        return ProjectileFactory.MakeProjectile(currentProjetile,
                new Point(PLAYER_X + 100, PLAYER_Y + 10), to, projectileDamage, numProjectiles, critChance);
    }

    @Override
    public void OnNotify(GameEvent e) {
        if (!(e instanceof CombatEvent))
            return;
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

    /**
     * Used to access the current projectile damage.
     *
     * @return A long representing the current projectile damage.
     */
    public long GetProjectileDamage() {
        return projectileDamage;
    }

    /**
     * Used to access the current projectile damage level.
     *
     * @return An int representing the current projectile damage level.
     */
    public int GetProjectileDamageLevel() {
        return projectileDamageLevel;
    }

    /**
     * Used to set the current projectile damage.
     *
     * @param d     A long representing the new projectile damage.
     * @param level An int representing the new projectile damage level.
     */
    public void SetProjectileDamage(long d, int level) {
        projectileDamage = d;
        projectileDamageLevel = level;
    }

    /**
     * Used to access the current projectile number.
     *
     * @return An int representing the current projectile number.
     */
    public int GetNumProjectiles() {
        return numProjectiles;
    }


    /**
     * Used to set the current number of projectiles.
     *
     * @param num An int representing the new number of projectiles.
     */
    public void SetNumProjectiles(int num) {
        numProjectiles = num;
    }


    /**
     * Used to access the current crtit chance.
     *
     * @return An int representing the current crit chance in percentages.
     */
    public int GetCritChance() {
        return critChance;
    }


    /**
     * Used to set the current crit chance.
     *
     * @param crit An int representing the new crit chance in percentages.
     */
    public void SetCritChance(int crit) {
        critChance = crit;
    }

    /**
     * Used to get the current spell type.
     *
     * @return An int representing the current spell type: 1 for fire, 2 for frost and 3 for arcane.
     */
    public int GetProjectileType() {
        switch (currentProjetile) {
            case FROST:
                return 2;
            case ARCANE:
                return 3;
            default:
                return 1;
        }
    }

    /**
     * Used to set the current spell type.
     *
     * @param projectileType An int representing the new spell type: 1 for fire, 2 for frost and 3 for arcane.
     */
    public void SetProjectileType(int projectileType) {
        switch (projectileType) {
            case 2:
                currentProjetile = ProjectileType.FROST;
                break;
            case 3:
                currentProjetile = ProjectileType.ARCANE;
                break;
            default:
                currentProjetile = ProjectileType.FIRE;
                break;
        }
    }

    /**
     * Used to get the current experience value.
     *
     * @return A long representing the current experience value.
     */
    public long GetExperience() {
        return experience;
    }

    /**
     * Used to set the current experience.
     *
     * @param exp A long representing the new experience value.
     */
    public void SetExperience(long exp) {
        experience = exp;
    }

    /**
     * Used to get the current level value.
     *
     * @return An int representing the current level value.
     */
    public int GetLevel() {
        return level;
    }

    /**
     * Used to set the current level.
     *
     * @param l An int representing the new level value.
     */
    public void SetLevel(int l) {
        level = l;
    }

    /**
     * Used to get the current health value.
     *
     * @return An int representing the current health value.
     */
    public int GetHealth() {
        return health;
    }
}
