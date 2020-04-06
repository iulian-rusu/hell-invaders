package Entities.CollidableEntities.Projectiles;

import Assets.FontAssets;
import Entities.CollidableEntities.CollidableEntity;
import Entities.CollidableEntities.Enemies.Enemy;
import GUI.GUIText;

import java.awt.*;
import java.util.Random;

public abstract class Projectile extends CollidableEntity {

    public static final int PROJECTILE_WIDTH = 30;
    public static final int PROJECTILE_HEIGHT = 30;
    public static final int CRIT_AMPLIFIER = 2;
    public static final int DEFAULT_VELOCITY = 20;

    protected static final Random RNG = new Random();

    protected double xVelocity;
    protected double yVelocity;
    protected double fx;
    protected double fy;
    protected int damage;
    protected int critChance;
    protected boolean hasCrit = false;

    public Projectile(int x, int y, double xVelocity, double yVelocity, int damage, int critChance) {
        super(x, y, PROJECTILE_WIDTH, PROJECTILE_HEIGHT, PROJECTILE_WIDTH, PROJECTILE_HEIGHT);
        //doubles for precise position calculation
        fx = x;
        fy = y;
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        this.damage = damage;
        this.critChance = critChance;
        isActive = true;
    }

    public void DealDamage(Enemy e) {
        if (Math.abs(RNG.nextInt()) % 100 < critChance) {
            e.TakeDamage(GetCriticalDamage(damage));
            hasCrit = true;
        } else {
            e.TakeDamage(damage);
        }
    }

    public GUIText GetCombatText() {
        String val = String.valueOf(hasCrit ? GetCriticalDamage(damage) : damage);
        int textX = hitBox.x + RNG.nextInt() % 15;
        int textY = hitBox.y + RNG.nextInt() % 15;
        int size = hasCrit ? 60 : 50;
        GUIText ans = new GUIText(val, textX, textY, size);
        ans.SetFont(FontAssets.mainFont_italic);
        ans.SetColor(hasCrit ? Color.RED : Color.WHITE);
        ans.SetDuration(30);
        return ans;
    }

    protected int GetCriticalDamage(int damage) {
        return damage * CRIT_AMPLIFIER;
    }

    @Override
    public void Update() {
        super.Update();
        fx += xVelocity;
        fy += yVelocity;
        x = (int) fx;
        y = (int) fy;
    }

    @Override
    public int GetHitBoxYOffset() {
        return 0;
    }
}