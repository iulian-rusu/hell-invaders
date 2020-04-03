package Entities;

import Entities.Enemies.Enemy;
import Entities.Projectiles.Projectile;
import Game.GameWindow;

import java.util.ArrayList;

public class EntityManager{

    public static synchronized void Update(ArrayList<Enemy> allEnemies, ArrayList<Projectile> allProjectiles) {
        CheckCollisions(allEnemies, allProjectiles);
        CheckVisibility(allProjectiles);
        Clean(allEnemies);
        Clean(allProjectiles);
    }

    public static synchronized <T extends CollidableEntity> void Clean(ArrayList<T> allEntities) {
        allEntities.removeIf(e -> !e.isActive);
    }

    public static synchronized void CheckCollisions(ArrayList<Enemy> allEnemies, ArrayList<Projectile> allProjectiles) {
        for (Projectile p : allProjectiles) {
            for (Enemy e : allEnemies) {
                if (p.CollidesWith(e)) {
                    p.DealDamage(e);
                    p.SetActive(false);
                    break;
                }
            }
        }
    }

    public static synchronized void CheckVisibility(ArrayList<Projectile> allProjectiles){
        for(Projectile p:allProjectiles){
            if(p.x<0 || p.x> GameWindow.wndDimension.width || p.y<0 || p.y > GameWindow.wndDimension.height) {
                p.SetActive(false);
            }
        }
    }
}
