package Entities.CollidableEntities.Enemies;

import Assets.EnemyAssets;
import Game.GameWindow;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Dragon extends Enemy {
    public static final int DEFAULT_HEIGHT = 100;
    public static final int DEFAULT_HITBOX_WIDTH = DEFAULT_WIDTH;
    public static final int DEFAULT_HITBOX_HEIGHT = (int) (DEFAULT_HEIGHT * 0.8);
    public static final int DEFAULT_ATTACK_TRANSITION_X = GameWindow.wndDimension.width / 2;

    public Dragon(int x, int y, int level) {
        super(x, y, DEFAULT_HITBOX_WIDTH, DEFAULT_HITBOX_HEIGHT, DEFAULT_WIDTH, DEFAULT_HEIGHT, level);
    }

    @Override
    protected void Attack() {

    }

    @Override
    public void Update() {
        super.Update();
        switch (state) {
            case INVISIBLE:
            case MOVING:
                x += xVelocity;
                if (x <= DEFAULT_ATTACK_TRANSITION_X) {
                    state = EnemyState.ATTACKING;
                }
                break;
            case ATTACKING:
                Attack();
                break;
        }
    }

    @Override
    public void Draw(Graphics g) {
        switch (state) {
            case INVISIBLE:
                return;
            case MOVING:
            case ATTACKING:
                BufferedImage currentFrame = EnemyAssets.dragon_frames[(frameCount / 6) % 5];
                g.drawImage(currentFrame, x, y, textureBox.width, textureBox.height, null);
        }
        super.Draw(g);
    }

    @Override
    public int GetHeight() {
        return DEFAULT_HEIGHT;
    }
}
