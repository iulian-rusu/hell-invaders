package Entities.CollidableEntities.Enemies;

import Assets.EnemyAssets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Monster extends Enemy {
    public static final int DEFAULT_HITBOX_WIDTH = (int) (DEFAULT_WIDTH * 0.9);
    public static final int DEFAULT_HITBOX_HEIGHT = (int) (DEFAULT_HEIGHT * 0.6);
    public static final int DEFAULT_ATTACK_TRANSITION_X = 170;

    public Monster(int x, int y, int level) {
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
                BufferedImage currentFrame = EnemyAssets.monster_frames[(frameCount / 5) % 4];
                g.drawImage(currentFrame, x, y, textureBox.width, textureBox.height, null);
                break;
            case ATTACKING:
                currentFrame = EnemyAssets.monster_frames[(frameCount / 15) % 4];
                g.drawImage(currentFrame, x, y, textureBox.width, textureBox.height, null);
        }
        super.Draw(g);
    }
}
