package Entities.CollidableEntities.Enemies;

import Assets.Images.EnemyAssets;
import Entities.Player;
import Game.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Monster extends Enemy {
    //health parameters
    public static int GET_DEFAULT_HEALTH() { return 40 * Game.DIFFICULTY; }
    public static int GET_HEALTH_INCREMENT() { return 4 * Game.DIFFICULTY; }
    public static long GET_ACTUAL_HEALTH(int level){
        long ans = GET_DEFAULT_HEALTH() + (long) (Math.pow(HEALTH_BASE, level - 1) * GET_HEALTH_INCREMENT());
        //test for overflow
        if (ans < 0) {
            ans=-ans;
        }
        return ans;
    }
    //size parameters
    public static final int DEFAULT_HITBOX_WIDTH = (int) (DEFAULT_WIDTH * 0.5);
    public static final int DEFAULT_HITBOX_HEIGHT = (int) (DEFAULT_HEIGHT * 0.6);
    public static final int DEFAULT_ATTACK_TRANSITION_X = 170;

    public Monster(int x, int y, int level) {
        super(x, y, DEFAULT_HITBOX_WIDTH, DEFAULT_HITBOX_HEIGHT, DEFAULT_WIDTH, DEFAULT_HEIGHT, level);
    }

    @Override
    public void Update() {
        super.Update();
        if(health<=0){
            isActive=false;
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
            Player.GetInstance().TakeDamage(GET_DEFAULT_DAMAGE());
        }
    }

    @Override
    public void Draw(Graphics g) {
        if (!isVisile) {
            return;
        }
        BufferedImage currentFrame;
        if (isSlowed || !isMoving) {
            currentFrame = EnemyAssets.monster_frames[(frameCount / 15) % 4];
        } else {
            currentFrame = EnemyAssets.monster_frames[(frameCount / 5) % 4];
        }
        g.drawImage(currentFrame, x, y, textureBox.width, textureBox.height, null);
        super.Draw(g);
    }

    @Override
    public int GetHitBoxYOffset() {
        return +30;
    }

    @Override
    protected int GetAttackTransitionX() {
        return DEFAULT_ATTACK_TRANSITION_X;
    }
}
