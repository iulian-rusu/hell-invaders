package Assets;

import java.awt.image.BufferedImage;

public class ProjectileAssets {
    public static BufferedImage[] fire_projectiles;
    public static BufferedImage frost_projectile;
    public static BufferedImage[] arcane_projectiles;


    public static void Init() {

        fire_projectiles = new BufferedImage[2];
        for (int i = 0; i < 2; ++i) {
            fire_projectiles[i] = ImageLoader.LoadImage("/moving objects/projectiles/fire_projectile" + i + ".png");
        }

        frost_projectile = ImageLoader.LoadImage("/moving objects/projectiles/frost_projectile.png");

        arcane_projectiles = new BufferedImage[6];
        for (int i = 0; i < 6; ++i) {
            arcane_projectiles[i] = ImageLoader.LoadImage("/moving objects/projectiles/arcane_projectile" + i + ".png");
        }
    }
}
