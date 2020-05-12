package Entities.CollidableEntities;

import Entities.CollidableEntities.Enemies.Enemy;
import Entities.CollidableEntities.Projectiles.Projectile;
import GUI.Text.GUITextComponent;
import Game.GameWindow;

import java.util.ArrayList;

/**
 * @brief Helper class that manages collision events.
 */
public class CollisionManager {
    /**
     * Checks collisions and performs all necessary updates on the entities.
     *
     * @param allEnemies     An ArrayList of all enemies on the battlefield.
     * @param allProjectiles An ArrayList of all projectiles on the battlefield.
     * @return An ArrayList with combat text that might appear during collision.
     */
    public static ArrayList<GUITextComponent> Update(ArrayList<Enemy> allEnemies, ArrayList<Projectile> allProjectiles) {
        ArrayList<GUITextComponent> combatTexts = CheckCollisions(allEnemies, allProjectiles);
        CheckVisibility(allProjectiles);
        allEnemies.removeIf(e -> !e.isActive);
        allProjectiles.removeIf(e -> !e.isActive);
        return combatTexts;
    }

    /**
     * Checks the collision between enemies and projectiles.
     *
     * @param allEnemies     An ArrayList of all enemies on the battlefield.
     * @param allProjectiles An ArrayList of all projectiles on the battlefield.
     * @return An ArrayList with combat text that might appear during collision.
     */
    public static ArrayList<GUITextComponent> CheckCollisions(ArrayList<Enemy> allEnemies, ArrayList<Projectile> allProjectiles) {
        ArrayList<GUITextComponent> combatTexts = new ArrayList<>();
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
        return combatTexts;
    }

    /**
     * Checks which projectiles are not on the screen and marks them as incative.
     *
     * @param allProjectiles An ArrayList of all projectiles on the battlefield.
     */
    public static void CheckVisibility(ArrayList<Projectile> allProjectiles) {
        for (Projectile p : allProjectiles) {
            int px = p.GetTextureBoxX();
            int py = p.GetTextureBoxY();
            if (px < 0 || px > GameWindow.SCREEN_DIMENSION.width || py < 0 || py > GameWindow.SCREEN_DIMENSION.height) {
                p.SetActive(false);
            }
        }
    }
}
