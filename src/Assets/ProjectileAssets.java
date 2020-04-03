package Assets;

import Entities.Projectiles.Projectile;

import java.awt.image.BufferedImage;
import java.util.Objects;

public class ProjectileAssets {
    public static BufferedImage frost_projectile;
    public static BufferedImage fire_projectile;
    public static BufferedImage arcane_projectile;


    public static void Init() {
        fire_projectile = ImageLoader.GetScaledImage(Objects.requireNonNull(ImageLoader.LoadImage(
                "/moving objects/projectiles/fire_projectile.png")), Projectile.PROJECTILE_WIDTH, Projectile.PROJECTILE_HEIGHT);

        frost_projectile = ImageLoader.GetScaledImage(Objects.requireNonNull(ImageLoader.LoadImage(
                "/moving objects/projectiles/frost_projectile.png")), Projectile.PROJECTILE_WIDTH, Projectile.PROJECTILE_HEIGHT);

        arcane_projectile = ImageLoader.GetScaledImage(Objects.requireNonNull(ImageLoader.LoadImage(
                "/moving objects/projectiles/arcane_projectile.png")), Projectile.PROJECTILE_WIDTH, Projectile.PROJECTILE_HEIGHT);
    }
}
