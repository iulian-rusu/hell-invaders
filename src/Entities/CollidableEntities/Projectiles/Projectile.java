package Entities.CollidableEntities.Projectiles;

import Assets.FontAssets;
import Entities.CollidableEntities.CollidableEntity;
import Entities.CollidableEntities.Enemies.Enemy;
import GUI.Text.GUIText;
import GUI.Text.GUITextComponent;
import GUI.Text.GUITimedDecorator;
import GameSystems.NumberSystem.LargeNumberHandler;

import java.awt.*;
import java.util.Random;

public abstract class Projectile extends CollidableEntity {
    //size parameters
    public static final int PROJECTILE_WIDTH = 30;
    public static final int PROJECTILE_HEIGHT = 30;
    //behaviour parameters
    public static final int CRIT_AMPLIFIER = 2;
    public static final int DEFAULT_VELOCITY = 20;

    protected static final Random RNG = new Random(System.currentTimeMillis());//for crit mechanics

    protected double xVelocity;
    protected double yVelocity;
    protected double angle;
    protected double fx;
    protected double fy;
    protected long damage;
    protected int critChance;
    protected boolean hasCrit = false;

    public Projectile(int x, int y,double xVelocity, double yVelocity, double angle, long damage, int critChance) {
        super(x, y, PROJECTILE_WIDTH, PROJECTILE_HEIGHT, PROJECTILE_WIDTH, PROJECTILE_HEIGHT);
        //doubles for precise position calculation
        fx = x;
        fy = y;
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        this.angle=angle;
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

    public GUITextComponent GetCombatText() {
        long dmg=hasCrit ? GetCriticalDamage(damage) : damage;
        String val = LargeNumberHandler.ParseLongInt(dmg);
        int textX = hitBox.x + RNG.nextInt() % 15;
        int textY = hitBox.y + RNG.nextInt() % 15;
        int size = hasCrit ? 60 : 50;
        //using decorator design pattern to add a timer for the text
        GUITextComponent ans = new GUITimedDecorator(new GUIText(val, textX, textY, size), 30);
        ans.SetFont(FontAssets.mainFontItalic);
        ans.SetColor(hasCrit ? Color.YELLOW : Color.WHITE);
        return ans;
    }

    protected long GetCriticalDamage(long damage) {
        return damage * CRIT_AMPLIFIER;
    }

    @Override
    public void Update() {
        super.Update();
        fx += xVelocity;
        fy += yVelocity;
        x = (int) Math.round(fx);
        y = (int) Math.round(fy);
    }

    @Override
    public int GetHitBoxYOffset() {
        return 0;
    }
}
