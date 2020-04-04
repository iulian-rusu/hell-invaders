package Entities.CollidableEntities.Projectiles;

import Entities.CollidableEntities.CollidableEntity;
import Entities.CollidableEntities.Enemies.Enemy;

import java.util.Random;

public abstract class Projectile extends CollidableEntity {
    public static final int CRIT_AMPLIFIER = 2;
    public static final int DEFAULT_VELOCITY = 30;
    public static final int PROJECTILE_WIDTH = 30;
    public static final int PROJECTILE_HEIGHT = 30;
    protected static final Random RNG = new Random();

    protected double xVelocity;
    protected double yVelocity;
    protected double fx;
    protected double fy;
    protected int damage;
    protected int critChance;

    public Projectile(int x, int y, double xVelocity, double yVelocity, int damage, int critChance) {
        super(x,y,PROJECTILE_WIDTH, PROJECTILE_HEIGHT,PROJECTILE_WIDTH,PROJECTILE_HEIGHT);
        //doubles for precise position calculation
        fx = x;
        fy = y;
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        this.damage = damage;
        this.critChance = critChance;
        isActive = true;
    }

    @Override
    public void Update() {
        super.Update();
        fx += xVelocity;
        fy += yVelocity;
        x = (int) fx;
        y = (int) fy;
    }

    public void DealDamage(Enemy e) {
        if (Math.abs(RNG.nextInt() )% 100 < critChance) {
            e.TakeDamage(damage * CRIT_AMPLIFIER);
        } else {
            e.TakeDamage(damage);
        }
    }
}
