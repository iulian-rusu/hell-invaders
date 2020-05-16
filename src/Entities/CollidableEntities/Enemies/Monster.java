package Entities.CollidableEntities.Enemies;

import Assets.Images.EnemyAssets;
import Game.Game;
import GameSystems.EventSystem.Events.CombatEvent;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @brief Implements the Monster type of enemy - a close ranged attacker.
 */
public class Monster extends Enemy {
    public static final int DEFAULT_HITBOX_WIDTH = (int) (DEFAULT_WIDTH * 0.5);///< The width of the hitbox relative to the texture box.
    public static final int DEFAULT_HITBOX_HEIGHT = (int) (DEFAULT_HEIGHT * 0.6);///< The height of the hitbox relative to the texture box.
    public static final int DEFAULT_ATTACK_TRANSITION_X = 170;///< The default x coordinate for attack state trigger.

    /**
     * Constructor with parameters.
     *
     * @param x     The x coordinate of the top-left corner of the hitbox.
     * @param y     The y coordinate of the top-left corner of the hitbox.
     * @param level The level of the monster.
     */
    public Monster(int x, int y, int level) {
        super(x, y, DEFAULT_HITBOX_WIDTH, DEFAULT_HITBOX_HEIGHT, DEFAULT_WIDTH, DEFAULT_HEIGHT, level);
    }

    /**
     * Returns the default health based on the game difficulty.
     *
     * @return An int representing the health value.
     */
    public static int GET_DEFAULT_HEALTH() {
        return 40 * Game.difficulty;
    }

    /**
     * Returns the health dealt based on the default health and level.
     *
     * @return An int representing the health value.
     */
    public static long GET_ACTUAL_HEALTH(int level) {
        long ans = (long) (Math.pow(HEALTH_INCREMENT, level - 1) * GET_DEFAULT_HEALTH());
        // Test for overflow
        if (ans < 0) {
            ans = -ans;
        }
        return ans;
    }

    @Override
    public void Update() {
        super.Update();
        if (health <= 0) {
            isActive = false;
        }
    }

    @Override
    protected void InitHealth() {
        this.health = GET_ACTUAL_HEALTH(this.level);
    }

    @Override
    protected void Attack() {
        if (framesSinceLastAttack >= FRAMES_BETWEEN_ATTACKS) {
            framesSinceLastAttack = 0;
            NotifyAllObservers(CombatEvent.ENEMY_ATTACK);
        }
    }

    @Override
    public void Draw(Graphics g) {
        if (!isVisile) {
            return;
        }
        BufferedImage currentFrame;
        if (isSlowed || !isMoving) {
            currentFrame = EnemyAssets.monsterFrames[(frameCount / 15) % 4];
        } else {
            currentFrame = EnemyAssets.monsterFrames[(frameCount / 5) % 4];
        }
        g.drawImage(currentFrame, x, y, textureBox.width, textureBox.height, null);
        super.Draw(g);
    }

    @Override
    public int GetHitBoxYOffset() {
        return 30;
    }

    @Override
    protected int GetAttackTransitionX() {
        return DEFAULT_ATTACK_TRANSITION_X;
    }
}
