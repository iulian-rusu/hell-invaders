package Entities.CollidableEntities.Projectiles;

import Assets.Images.ProjectileAssets;
import GUI.Text.GUITextComponent;

import java.awt.*;

public class ArcaneProjectile extends Projectile {
    //override base CRIT_AMPLIFIER
    public static final int CRIT_AMPLIFIER = 4;

    public ArcaneProjectile(int x, int y, double xVelocity, double yVelocity, double angle, long damage, int critChance) {
        super(x, y, xVelocity, yVelocity, angle, damage, critChance);
    }

    @Override
    public GUITextComponent GetCombatText() {
        GUITextComponent ans = super.GetCombatText();
        ans.SetColor(hasCrit ? Color.RED : Color.WHITE);
        return ans;
    }

    @Override
    protected long GetCriticalDamage(long damage) {
        return damage * CRIT_AMPLIFIER;
    }

    @Override
    public void Draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.rotate(angle);
        //change coordinates to account for g2d rotation
        int xTransform = (int) ((x+textureBox.width*0.5) * Math.cos(-angle) - (y+textureBox.height*0.5) * Math.sin(-angle)-textureBox.width*0.5);
        int yTransform = (int) ((x+textureBox.width*0.5) * Math.sin(-angle) + (y+textureBox.height*0.5) * Math.cos(-angle)-textureBox.height*0.5);
        g2d.drawImage(ProjectileAssets.arcaneProjectiles[frameCount / 10], xTransform, yTransform, textureBox.width, textureBox.height, null);
        g2d.rotate(-angle);
    }
}
