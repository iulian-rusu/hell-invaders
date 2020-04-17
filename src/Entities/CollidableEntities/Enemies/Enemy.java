package Entities.CollidableEntities.Enemies;

import Entities.CollidableEntities.CollidableEntity;
import Entities.CollidableEntities.Projectiles.FrostProjectile;
import Entities.Player;
import GameSystems.EventSystem.Events.CombatEvent;
import GameSystems.EventSystem.Events.AudioEvent;
import GUI.GUIStatusBar;
import Game.Game;
import Game.GameWindow;

import java.awt.*;

public abstract class Enemy extends CollidableEntity implements Comparable<Enemy> {
    //size and speed paramaters
    public static final int DEFAULT_WIDTH = 200;
    public static final int DEFAULT_HEIGHT = 240;
    public static final int DEFAULT_X_VELOCITY = -1;
    //damage parameters
    public static int GET_DEFAULT_DAMAGE() { return 10 + 5 * Game.DIFFICULTY; }
    public static final int FRAMES_BETWEEN_ATTACKS = 120;
    //healt multiplier per level
    public static final double HEALTH_INCREMENT = 1.12;

    protected long health;
    protected int level;
    protected double xVelocity;
    protected double fx;
    protected GUIStatusBar<Enemy> healthBar;
    //flags for effects
    protected boolean isMoving = true;
    protected boolean isVisile = false;
    protected boolean isSlowed = false;
    private int slowedBegin = -1;//counts from when the enemy was slowed
    protected int framesSinceLastAttack = FRAMES_BETWEEN_ATTACKS;//for delay between attacks


    public Enemy(int x, int y, int hiboxW, int hitboxH, int textureW, int textureH, int level) {
        super(x, y, hiboxW, hitboxH, textureW, textureH);
        fx = x;
        this.xVelocity = DEFAULT_X_VELOCITY;
        this.level = level;
        //health depends on enemy type -> InitHealth must be overrided for each derived class
        InitHealth();
        //init healthbar
        healthBar = new GUIStatusBar<>(this, Enemy::ProvideHealthData);
        int healthBarX = hitBox.x + (hitBox.width - GUIStatusBar.DEFAULT_WIDTH) / 2;
        int healthBarY = hitBox.y + GUIStatusBar.DEFAULT_Y_OFFSET;
        healthBar.SetPosition(healthBarX, healthBarY);
        AddObserver(healthBar);
        //add the player as an observer
        AddObserver(Player.GetInstance());
    }

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

    protected abstract void InitHealth();

    protected abstract void Attack();

    public void GetSlowed() {
        slowedBegin = 0;
        if (!isSlowed) {
            isSlowed = true;
            xVelocity *= FrostProjectile.SLOW_PERCENTAGE;
        }
    }

    @Override
    public void Update() {
        super.Update();
        //move healthbar
        int healthBarX = hitBox.x + (hitBox.width - GUIStatusBar.DEFAULT_WIDTH) / 2;
        int healthBarY = hitBox.y + GUIStatusBar.DEFAULT_Y_OFFSET;
        healthBar.SetPosition(healthBarX, healthBarY);

        //update position
        if (isMoving) {
            fx += xVelocity;
            x = (int) fx;
            if (x <= GetAttackTransitionX()) {
                isMoving = false;
                xVelocity = 0;
            }
        } else if (health > 0 ) {
            Attack();
        }

        //make it visible if it's on screen
        if (isMoving && !isVisile && x <= GameWindow.wndDimension.width) {
            NotifyAllObservers(AudioEvent.PLAY_ENEMY_SPAWN);
            isVisile = true;
        }

        //check slowed status
        if (isSlowed) {
            ++slowedBegin;
            if (slowedBegin > FrostProjectile.SLOW_FRAME_COUNT) {
                isSlowed = false;
                xVelocity = DEFAULT_X_VELOCITY;
                slowedBegin = -1;
            }
        }

        //update frames since last attack
        if (framesSinceLastAttack < FRAMES_BETWEEN_ATTACKS) {
            ++framesSinceLastAttack;
        }
    }

    @Override
    public void Draw(Graphics g) {
        //g.drawRect(hitBox.x,hitBox.y,hitBox.width,hitBox.height);
        healthBar.Draw(g);
    }

    @Override
    public int compareTo(Enemy e) {
        return Integer.compare(this.y + GetHeight(), e.y + e.GetHeight());
    }

    public int GetHeight() {
        return DEFAULT_HEIGHT;
    }

    public int GetWidth() {
        return DEFAULT_WIDTH;
    }

    public static Long ProvideHealthData(Enemy e) {
        return e.health;
    }

    protected abstract int GetAttackTransitionX();
}
