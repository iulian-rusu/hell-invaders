package Entities.Enemies;

import Entities.CollidableEntity;
import Events.SFXEvent;
import Game.GameWindow;

public abstract class Enemy extends CollidableEntity {
    protected static final int DEFAULT_X_VELOCITY=-1;
    protected static final int DEFAULT_HEALTH=200;

    protected int health;
    protected double xVelocity;
    protected boolean isVisible;


    public Enemy(int x, int y,int hiboxW, int hitboxH, int textureW, int textureH) {
        super(hiboxW, hitboxH, textureW,textureH);
        this.x=x;
        this.y=y;
        this.xVelocity=DEFAULT_X_VELOCITY;
        this.health=DEFAULT_HEALTH;
        isVisible=false;
    }

    @Override
    public void Update() {
        super.Update();
        if(!isVisible && x< GameWindow.wndDimension.width){
            NotifyAllObservers(new SFXEvent(SFXEvent.PLAY_MONSTER_SPAWN));
            isVisible=true;
        }
    }

    public void TakeDamage(int damage){
        health-=damage;
        if(health<=0){
            isActive=false;
        }
        NotifyAllObservers(new SFXEvent(SFXEvent.PLAY_MONSTER_HURT));
    }
}
