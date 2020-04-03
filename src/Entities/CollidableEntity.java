package Entities;

import Audio.AudioManager;

import java.awt.*;

public abstract class CollidableEntity extends Entity {
    protected Rectangle hitBox;
    protected Rectangle textureBox;
    protected boolean isActive;

    public CollidableEntity(int hitboxW, int hitboxH, int textureW, int textureH){
        hitBox=new Rectangle(hitboxW,hitboxH);
        textureBox=new Rectangle(textureW,textureH);
        isActive=true;
        AddObserver(AudioManager.GetInstance());
    }
    public void SetActive(boolean f) {
        isActive = f;
    }

    public boolean CollidesWith(CollidableEntity other) {
        return this.hitBox.intersects(other.hitBox);
    }

    @Override
    public void Update() {
        super.Update();
        hitBox.setLocation(x,y+(textureBox.height-hitBox.height));
        textureBox.setLocation(x,y);
    }
}
