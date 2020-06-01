package Entities.CollidableEntities.Enemies;

import Entities.CollidableEntities.CollidableEntity;
import Entities.CollidableEntities.Projectiles.FrostProjectile;
import GUI.GUIStatusBar;
import Game.Game;
import Game.GameWindow;
import Game.GlobalReferences;
import GameSystems.EventSystem.Events.AudioEvent;
import GameSystems.EventSystem.Events.CombatEvent;

import java.awt.*;

/**
 * @brief Class that implements enemies.
 */
public abstract class Enemy extends CollidableEntity implements Comparable<Enemy> {
    public static final int DEFAULT_WIDTH = 200;///< The default width of an enemy.
    public static final int DEFAULT_HEIGHT = 240;///< The default height of an enemy.
    public static final double DEFAULT_X_VELOCITY = -1.0;///< The default x velocity of an enemy.
    public static final int FRAMES_BETWEEN_ATTACKS = 120;///< The default number of frames between consecutive attacks.
    public static final double HEALTH_INCREMENT = 1.1;///< The increment in health each level.

    protected long health;///< The current health of the enemy.
    protected int level;///< The current level of the enemy.
    protected double xVelocity;///< The double precision velocity on the x axis.
    protected double fx;///< The double precision x coordinate.
    protected GUIStatusBar<Enemy> healthBar;///< A GUIStatusBar object that tracks the health.
    protected boolean isMoving = true;///< Indicates if the enemy is moving.
    protected boolean isVisile = false;///< Indicates if the enemy is on the screen.
    protected boolean isSlowed = false;///< Indicates if the enemy is slowed by a spell.
    protected int framesSinceLastAttack = FRAMES_BETWEEN_ATTACKS;///< Counts the number of frames since the last attack.
    protected int slowedFrameCount = -1;///< Counts when the slowed state began
    /**
     * Constructor with parameters.
     *
     * @param x        The x coordinate of the top-left corner of the hitbox.
     * @param y        The y coordinate of the top-left corner of the hitbox.
     * @param hiboxW   The witdh of the hitbox.
     * @param hitboxH  The height of the hitbox.
     * @param textureW The width of the texture box.
     * @param textureH The height of the texture box.
     * @param level    The level of the enemy.
     */
    public Enemy(int x, int y, int hiboxW, int hitboxH, int textureW, int textureH, int level) {
        super(x, y, hiboxW, hitboxH, textureW, textureH);
        fx = x;
        this.xVelocity = DEFAULT_X_VELOCITY;
        this.level = level;
        // Health depends on enemy type, InitHealth() must be overriden for each derived class
        InitHealth();
        // Init healthbar
        healthBar = new GUIStatusBar<>(this, enemy -> enemy.health);
        int healthBarX = hitBox.x + (hitBox.width - GUIStatusBar.DEFAULT_WIDTH) / 2;
        int healthBarY = hitBox.y + GUIStatusBar.DEFAULT_Y_OFFSET;
        healthBar.SetPosition(healthBarX, healthBarY);
        AddObserver(healthBar);
        // Add the player as an observer
        AddObserver(GlobalReferences.GetPlayer());
    }

    /**
     * Returns the default damage dealt based on the game difficulty.
     *
     * @return An int representing the damage value.
     */
    public static int GET_DEFAULT_DAMAGE() {
        return 10 + 5 * Game.difficulty;
    }

    /**
     * Called when then enemy is damaged.
     *
     * @param damage The amount of damage to be taken.
     */
    public void TakeDamage(long damage) {
        health -= damage;
        if (health <= 0) {
            isVisile = false;
            isCollisionActive = false;
            isMoving = false;
            NotifyAllObservers(CombatEvent.ENEMY_DEATH);
        }
        NotifyAllObservers(AudioEvent.PLAY_ENEMY_HURT);
        NotifyAllObservers(CombatEvent.STATUS_BAR_UPDATE);
    }

    /**
     * Initializes the health based on enemy type.
     */
    protected abstract void InitHealth();

    /**
     * Called when the enemy attacks. Attacks might be ranged or melee depending on enemy type.
     */
    protected abstract void Attack();

    /**
     * Called when the enemy is slowed by a spell.
     */
    public void GetSlowed() {
        slowedFrameCount = 0;
        if (!isSlowed) {
            isSlowed = true;
            xVelocity *= (1 - FrostProjectile.SLOW_PERCENTAGE);
        }
    }

    /**
     * Updates the slowedFrameCount and checks if the enemy is still slowed.
     */
    protected void UpdateSlowedStatus(){
        ++slowedFrameCount;
        if (slowedFrameCount > FrostProjectile.SLOW_FRAME_COUNT) {
            isSlowed = false;
            xVelocity /= (1 - FrostProjectile.SLOW_PERCENTAGE);
            slowedFrameCount = -1;
        }
    }

    @Override
    public void Update() {
        super.Update();
        // Update healthbar position
        int healthBarX = hitBox.x + (hitBox.width - GUIStatusBar.DEFAULT_WIDTH) / 2;
        int healthBarY = hitBox.y + GUIStatusBar.DEFAULT_Y_OFFSET;
        healthBar.SetPosition(healthBarX, healthBarY);
        // Update enemy position
        if (isMoving) {
            fx += xVelocity;
            x = (int) fx;
            if (x <= GetAttackTransitionX()) {
                isMoving = false;
                xVelocity = 0;
            }
        } else if (health > 0) {
            Attack();
        }
        // Make it visible if it's on the screen
        if (isMoving && !isVisile && x <= GameWindow.SCREEN_DIMENSION.width) {
            NotifyAllObservers(AudioEvent.PLAY_ENEMY_SPAWN);
            isVisile = true;
        }
        // Check if slowed
        if (isSlowed) {
            UpdateSlowedStatus();
        }
        // Update the number of frames since last attack
        if (framesSinceLastAttack < FRAMES_BETWEEN_ATTACKS) {
            ++framesSinceLastAttack;
        }
    }

    @Override
    public void Draw(Graphics g) {
        healthBar.Draw(g);
    }

    /**
     * Overrides the compareTo method to allow sorting of entities by their y coordinate.
     *
     * @param e The entity to be compared to.
     * @return An int representing the result of the comparsion.
     * @see java.lang.Comparable
     */
    @Override
    public int compareTo(Enemy e) {
        return Integer.compare(this.y + GetHeight(), e.y + e.GetHeight());
    }

    /**
     * Returns the default height of the enemy.
     *
     * @return An int representing the height value.
     */
    public int GetHeight() {
        return DEFAULT_HEIGHT;
    }

    /**
     * Returns the default width of the enemy.
     *
     * @return An int representing the width value.
     */
    public int GetWidth() {
        return DEFAULT_WIDTH;
    }

    /**
     * Returns the x cordinate at which the enemy goes into attack mode.
     *
     * @return An int representing the coordinate.
     */
    protected abstract int GetAttackTransitionX();
}
