package Game.Graphics;

import java.awt.image.BufferedImage;

public class ProjectileAssets {
    public static BufferedImage blue_projectile;
    public static BufferedImage purple_projectile;
    public static BufferedImage red_projectile;

    public static void Init(){
    blue_projectile=ImageLoader.LoadImage("/moving objects/projectiles/blue_projectile.png");
    purple_projectile=ImageLoader.LoadImage("/moving objects/projectiles/purple_projectile.png");
    red_projectile=ImageLoader.LoadImage("/moving objects/projectiles/red_projectile.png");
    }
}
