package Entities.CollidableEntities.Enemies;

import Entities.CollidableEntities.CollidableEntity;
import EventSystem.Events.CombatEvent;
import EventSystem.Events.AudioEvent;
import GUI.GUIEnemyHealthBar;
import Game.Game;
import Game.GameWindow;

import java.awt.*;

public abstract class Enemy extends CollidableEntity implements Comparable<Enemy> {
    public static final int DEFAULT_WIDTH = 200;
    public static final int DEFAULT_HEIGHT = 220;
    public static final int DEFAULT_X_VELOCITY = -1;
    public static final int DEFAULT_HEALTH = 50 * Game.DIFFICULTY;
    public static final int HEALTH_INCREMENT = 10 * Game.DIFFICULTY;
    public static final int DEFAULT_DAMAGE = 10;

    protected int health;
    protected int level;
    protected double xVelocity;
    protected GUIEnemyHealthBar healthBar;
    protected EnemyState state;


    public Enemy(int x, int y, int hiboxW, int hitboxH, int textureW, int textureH, int level) {
        super(x, y, hiboxW, hitboxH, textureW, textureH);
        this.xVelocity = DEFAULT_X_VELOCITY;
        this.health = DEFAULT_HEALTH + level * HEALTH_INCREMENT;
        this.level = level;
        healthBar = new GUIEnemyHealthBar(this, this::GetHealth);
        AddObserver(healthBar);
        state = EnemyState.INVISIBLE;
    }

    public void TakeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            isActive = false;
            NotifyAllObservers(CombatEvent.MONSTER_DEATH);
        }
        NotifyAllObservers(AudioEvent.PLAY_ENEMY_HURT);
        NotifyAllObservers(CombatEvent.MONSTER_HIT);
    }

    public Integer GetHealth(Enemy e) {
        return e.health;
    }

    protected abstract void Attack();

    @Override
    public void Update() {
        super.Update();
        if (state == EnemyState.INVISIBLE && x < GameWindow.wndDimension.width) {
            NotifyAllObservers(AudioEvent.PLAY_ENEMY_SPAWN);
            state = EnemyState.MOVING;
        }
    }

    @Override
    public void Draw(Graphics g) {
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
}
