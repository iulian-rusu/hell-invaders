package Entities.Projectiles;

import Assets.ProjectileAssets;
import Entities.CollidableEntity;
import Entities.Enemies.Enemy;


public abstract class Projectile extends CollidableEntity {
    protected static final int CRIT_AMPLIFIER = 1;
    public static final int DEFAULT_VELOCITY = 30;

    protected double xVelocity;
    protected double yVelocity;
    protected double fx;
    protected double fy;
    protected double angle;
    protected int damage;

    public Projectile(int x, int y, double xVelocity, double yVelocity, double angle, int damage) {
        super(ProjectileAssets.PROJECTILE_WIDTH, ProjectileAssets.PROJECTILE_HEIGHT);
        this.x = x;
        this.y = y;
        //doubles for precise position calculation
        fx = x;
        fy = y;
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        this.angle = angle;
        this.damage = damage;
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
        e.TakeDamage(damage);
    }
}
