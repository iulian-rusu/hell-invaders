package Entities.CollidableEntities;

import Entities.CollidableEntities.Enemies.Enemy;
import Entities.CollidableEntities.Projectiles.Projectile;
import GUI.Text.GUITextComponent;
import Game.GameWindow;

import java.util.ArrayList;

public class CollisionManager {
    public static ArrayList<GUITextComponent> Update(ArrayList<Enemy> allEnemies, ArrayList<Projectile> allProjectiles) {
        ArrayList<GUITextComponent> combatTexts = CheckCollisions(allEnemies, allProjectiles);
        CheckVisibility(allProjectiles);
        Clean(allEnemies);
        Clean(allProjectiles);
        return combatTexts;
    }

    public static <T extends CollidableEntity> void Clean(ArrayList<T> allEntities) {
        //remove incative entities
        allEntities.removeIf(e -> !e.isActive);
    }

    public static ArrayList<GUITextComponent> CheckCollisions(ArrayList<Enemy> allEnemies, ArrayList<Projectile> allProjectiles) {
        ArrayList<GUITextComponent> combatTexts =new ArrayList<>();
        for (Projectile p : allProjectiles) {
            for (Enemy e : allEnemies) {
                if (p.isActive && e.isActive && p.CollidesWith(e)) {
                    p.DealDamage(e);
                    p.SetActive(false);
                    combatTexts.add(p.GetCombatText());
                    break;
                }
            }
        }
        return combatTexts ;
    }

    public static void CheckVisibility(ArrayList<Projectile> allProjectiles) {
        for (Projectile p : allProjectiles) {
            int px=p.GetTextureBoxX();
            int py=p.GetTextureBoxY();
            if (px < 0 || px > GameWindow.screenDimension.width || py < 0 || py > GameWindow.screenDimension.height) {
                p.SetActive(false);
            }
        }
    }
}
