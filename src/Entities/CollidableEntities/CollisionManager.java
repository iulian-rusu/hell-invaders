package Entities.CollidableEntities;

import Entities.CollidableEntities.Enemies.Enemy;
import Entities.CollidableEntities.Projectiles.Projectile;
import GUI.GUIText;
import Game.GameWindow;

import java.util.ArrayList;

public class CollisionManager {
    //singleton entity manager class
    public static CollisionManager GetInstance() {
        if (instance == null)
            instance = new CollisionManager();
        return instance;
    }

    public ArrayList<GUIText> Update(ArrayList<Enemy> allEnemies, ArrayList<Projectile> allProjectiles) {
        ArrayList<GUIText> combatTexts = CheckCollisions(allEnemies, allProjectiles);
        CheckVisibility(allProjectiles);
        Clean(allEnemies);
        Clean(allProjectiles);
        return combatTexts;
    }

    public <T extends CollidableEntity> void Clean(ArrayList<T> allEntities) {
        //remove incative entities
        allEntities.removeIf(e -> !e.isActive);
    }

    public ArrayList<GUIText> CheckCollisions(ArrayList<Enemy> allEnemies, ArrayList<Projectile> allProjectiles) {
        ArrayList<GUIText> combatTexts =new ArrayList<>();
        for (Projectile p : allProjectiles) {
            for (Enemy e : allEnemies) {
                if (p.CollidesWith(e)) {
                    p.DealDamage(e);
                    p.SetActive(false);
                    combatTexts.add(p.GetCombatText());
                    break;
                }
            }
        }
        return combatTexts ;
    }

    public void CheckVisibility(ArrayList<Projectile> allProjectiles) {
        for (Projectile p : allProjectiles) {
            int px=p.GetTextureBoxX();
            int py=p.GetTextureBoxY();
            if (px < 0 || px > GameWindow.wndDimension.width || py < 0 || py > GameWindow.wndDimension.height) {
                p.SetActive(false);
            }
        }
    }
    private static CollisionManager instance = null;
}
