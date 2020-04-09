package Entities.CollidableEntities.Enemies;

import Assets.EnemyAssets;
import Entities.Player;
import Game.Game;
import Game.GameWindow;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Dragon extends Enemy {
    //health parameters
    public static int GET_DEFAULT_HEALTH(){ return 25 * Game.DIFFICULTY;}
    public static int GET_HEALTH_INCREMENT(){ return 3 * Game.DIFFICULTY;}
    public static final double HEALTH_DEPENDECNY_EXPONENT = 1.9;
    //size parameters
    public static final int DEFAULT_HEIGHT = 100;
    public static final int DEFAULT_HITBOX_WIDTH = (int) (DEFAULT_WIDTH * 0.6);
    public static final int DEFAULT_HITBOX_HEIGHT = (int) (DEFAULT_HEIGHT * 0.6);
    public static final int DEFAULT_ATTACK_TRANSITION_X = GameWindow.wndDimension.width / 2;

    public Dragon(int x, int y, int level) {
        super(x, y, DEFAULT_HITBOX_WIDTH, DEFAULT_HITBOX_HEIGHT, DEFAULT_WIDTH, DEFAULT_HEIGHT, level);
    }

    @Override
    protected void InitHealth() {
        this.health = GET_DEFAULT_HEALTH() + (int) (Math.pow(level, HEALTH_DEPENDECNY_EXPONENT)) * GET_HEALTH_INCREMENT();
    }

    @Override
    protected void Attack() {
        if(framesSinceLastAttack>=FRAMES_BETWEEN_ATTACKS ){
            framesSinceLastAttack=0;
            Player.GetInstance().TakeDamage(Enemy.GET_DEFAULT_DAMAGE());
        }
    }

    @Override
    public void Draw(Graphics g) {
        if (!isVisile) {
            return;
        }
        BufferedImage currentFrame;
        if (isSlowed||!isMoving) {
            currentFrame = EnemyAssets.dragon_frames[(frameCount / 12) % 5];
        } else {
            currentFrame = EnemyAssets.dragon_frames[(frameCount / 6) % 5];
        }
        g.drawImage(currentFrame, x, y, textureBox.width, textureBox.height, null);
        super.Draw(g);
    }

    @Override
    public int GetHeight() {
        return DEFAULT_HEIGHT;
    }

    @Override
    public int GetHitBoxYOffset() {
        return 0;
    }


    @Override
    protected int GetAttackTransitionX() {
        return DEFAULT_ATTACK_TRANSITION_X;
    }
}
