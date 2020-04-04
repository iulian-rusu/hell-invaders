package Entities.CollidableEntities;

import Audio.AudioManager;
import Entities.Entity;

import java.awt.*;

public abstract class CollidableEntity extends Entity {
    protected Rectangle hitBox;
    protected Rectangle textureBox;
    protected int x;
    protected int y;

    public boolean isActive;

    public CollidableEntity(int x, int y,int hitboxW, int hitboxH, int textureW, int textureH){
        this.x=x;
        this.y=y;
        hitBox=new Rectangle(hitboxW,hitboxH);
        textureBox=new Rectangle(textureW,textureH);
        isActive=true;
        AddObserver(AudioManager.GetInstance());
    }
    public void SetActive(boolean f) {
        isActive = f;
    }

    public int GetHitBoxX(){return hitBox.x;}
    public int GetHitBoxY(){return hitBox.y;}
    public int GetTextureBoxX(){return textureBox.x;}
    public int GetTextureBoxY(){return textureBox.y;}

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
