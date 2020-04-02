package Entities;

import java.awt.*;

public abstract class CollidableEntity extends Entity {
    protected Rectangle hitBox;
    protected boolean isActive;

    public CollidableEntity(int w, int h){
        hitBox=new Rectangle(w,h);
    }
    public void SetActive(boolean f) {
        isActive = f;
    }

    public boolean CollidesWith(CollidableEntity other) {
        return this.hitBox.intersects(other.hitBox);
    }
}
