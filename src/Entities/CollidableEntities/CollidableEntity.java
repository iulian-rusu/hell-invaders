package Entities.CollidableEntities;

import Audio.AudioManager;
import Entities.Entity;

import java.awt.*;

public abstract class CollidableEntity extends Entity {

    protected Rectangle hitBox;
    protected Rectangle textureBox;
    protected int x;
    protected int y;
    protected boolean isCollisionActive = true;

    public boolean isActive = true;

    public CollidableEntity(int x, int y, int hitboxW, int hitboxH, int textureW, int textureH) {
        this.x = x;
        this.y = y;
        hitBox = new Rectangle(hitboxW, hitboxH);
        textureBox = new Rectangle(textureW, textureH);
        //init texture and hit box locations
        textureBox.setLocation(x, y);
        hitBox.setLocation(x + (textureBox.width - hitBox.width) / 2, y + (textureBox.height - hitBox.height) / 2 + GetHitBoxYOffset());
        //add audio observer
        AddObserver(AudioManager.GetInstance());
    }

    public void SetActive(boolean f) {
        isActive = f;
    }

    public int GetHitBoxX() {
        return hitBox.x;
    }

    public int GetHitBoxY() {
        return hitBox.y;
    }

    public int GetTextureBoxX() {
        return textureBox.x;
    }

    public int GetTextureBoxY() {
        return textureBox.y;
    }

    public boolean CollidesWith(CollidableEntity other) {
        return isCollisionActive && other.isCollisionActive && this.hitBox.intersects(other.hitBox);
    }

    @Override
    public void Update() {
        super.Update();
        textureBox.setLocation(x, y);
        hitBox.setLocation(x + (textureBox.width - hitBox.width) / 2, y + (textureBox.height - hitBox.height) / 2 + GetHitBoxYOffset());
    }

    public abstract int GetHitBoxYOffset();
}
