package Entities;

import Entities.Enemies.Enemy;
import Entities.Projectiles.Projectile;

import java.awt.*;
import java.util.PriorityQueue;

public class EntityManager {

    private static int width=Toolkit.getDefaultToolkit().getScreenSize().width;
    private static int height=Toolkit.getDefaultToolkit().getScreenSize().height;

    public static void Update(PriorityQueue<Enemy> allEnemies, PriorityQueue<Projectile> allProjectiles) {
        CheckCollisions(allEnemies, allProjectiles);
        CheckVisibility(allProjectiles);
        Clean(allEnemies);
        Clean(allProjectiles);
    }

    public static synchronized <T extends CollidableEntity> void Clean(PriorityQueue<T> allEntities) {
        allEntities.removeIf(e -> !e.isActive);
    }

    public static void CheckCollisions(PriorityQueue<Enemy> allEnemies, PriorityQueue<Projectile> allProjectiles) {
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

    public static void CheckVisibility(PriorityQueue<Projectile> allProjectiles){
        for(Projectile p:allProjectiles){
            if(p.x<0 || p.x>width || p.y<0 || p.y > height) {
                p.SetActive(false);
            }
        }
    }
}
