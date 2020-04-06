package Entities.CollidableEntities.Enemies;

import Assets.EnemyAssets;
import Game.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Monster extends Enemy {
    //health parameters
    public static int GET_DEFAULT_HEALTH(){ return 50 * Game.DIFFICULTY;}
    public static int GET_HEALTH_INCREMENT(){return 5 * Game.DIFFICULTY;}
    public static final double HEALTH_DEPENDECNY_EXPONENT = 2.0;
    //size parameters
    public static final int DEFAULT_HITBOX_WIDTH = (int) (DEFAULT_WIDTH * 0.5);
    public static final int DEFAULT_HITBOX_HEIGHT = (int) (DEFAULT_HEIGHT * 0.6);
    public static final int DEFAULT_ATTACK_TRANSITION_X = 170;

    public Monster(int x, int y, int level) {
        super(x, y, DEFAULT_HITBOX_WIDTH, DEFAULT_HITBOX_HEIGHT, DEFAULT_WIDTH, DEFAULT_HEIGHT, level);
    }

    @Override
    protected void InitHealth() {
        this.health = GET_DEFAULT_HEALTH() + (int) (Math.pow(level, HEALTH_DEPENDECNY_EXPONENT)) * GET_HEALTH_INCREMENT();
    }

    @Override
    protected void Attack() {

    }

    @Override
    public void Draw(Graphics g) {
        if (!isVisile) {
            return;
        }
        BufferedImage currentFrame;
        if (isSlowed||!isMoving) {
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
