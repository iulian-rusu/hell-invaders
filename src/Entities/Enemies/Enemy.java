package Entities.Enemies;

import Entities.CollidableEntity;

public abstract class Enemy extends CollidableEntity {
    protected int health;
    protected double xVelocity;

    public Enemy(int w, int h) {
        super(w, h);
    }

    public void TakeDamage(int damage){
        health-=damage;
        if(health<=0){
            isActive=false;
        }
    }
}
