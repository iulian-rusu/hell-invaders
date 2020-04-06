package Entities.CollidableEntities.Projectiles;

import Assets.ProjectileAssets;
import GUI.GUIText;

import java.awt.*;

public class ArcaneProjectile extends Projectile {
    //override base CRIT_AMPLIFIER
    public static final int CRIT_AMPLIFIER = 4;

    public ArcaneProjectile(int x, int y, double xVelocity, double yVelocity, int damage, int critChance) {
        super(x, y, xVelocity, yVelocity, damage, critChance);
    }

    @Override
    public GUIText GetCombatText() {
        GUIText ans = super.GetCombatText();
        ans.SetColor(hasCrit ? new Color(219, 0, 255) : Color.WHITE);
        return ans;
    }

    @Override
    protected int GetCriticalDamage(int damage) {
        return damage * CRIT_AMPLIFIER;
    }

    @Override
    public void Draw(Graphics g) {
        g.drawImage(ProjectileAssets.arcane_projectiles[frameCount / 10], x, y, hitBox.width, hitBox.height, null);
    }
}
