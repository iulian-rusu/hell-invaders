package Entities.Enemies;

import Assets.EnemyAssets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Monster extends Enemy {
    private static final int DEFAULT_WIDTH = 200;
    private static final int DEFAULT_HEIGHT = 220;
    private static final int DEFAULT_HITBOX_WIDTH = (int) (DEFAULT_WIDTH*0.9);
    private static final int DEFAULT_HITBOX_HEIGHT = (int) (DEFAULT_HEIGHT*0.6);

    public Monster(int x, int y) {
        super(x, y, DEFAULT_HITBOX_WIDTH, DEFAULT_HITBOX_HEIGHT, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    @Override
    public void Update() {
        super.Update();
        x += xVelocity;
    }

    @Override
    public void Draw(Graphics g) {
        if(!isVisible)
            return;
        BufferedImage currentFrame = EnemyAssets.monster_frames[(frameCount / 5) % 4];
        g.drawImage(currentFrame, x, y, textureBox.width, textureBox.height, null);
    }
}
