package Assets.Images;

import java.awt.image.BufferedImage;

/**
 * @brief Holds references to projectile textures.
 */
public class ProjectileAssets {
    public static BufferedImage[] fireProjectiles;///< All fire projectile assets.
    public static BufferedImage[] frostProjectiles;///< All frost projectile assets.
    public static BufferedImage[] arcaneProjectiles;///< All arcane projectile assets.
    public static BufferedImage[] enemyProjectiles;///< All enemy projectile assets.

    /**
     * Loads all projectile textures from their spritesheets on the disk.
     */
    public static void Init() {
        BufferedImage sprites = ImageLoader.LoadImage("/moving objects/projectiles/fire_sprites.png");
        assert sprites != null;
        fireProjectiles = new BufferedImage[2];
        for (int i = 0; i < 2; ++i) {
            fireProjectiles[i] = sprites.getSubimage(i * 40, 0, 40, 14);
        }

        sprites = ImageLoader.LoadImage("/moving objects/projectiles/frost_sprites.png");
        assert sprites != null;
        frostProjectiles = new BufferedImage[3];
        for (int i = 0; i < 3; ++i) {
            frostProjectiles[i] = sprites.getSubimage(i * 40, 0, 40, 14);
        }

        sprites = ImageLoader.LoadImage("/moving objects/projectiles/arcane_sprites.png");
        assert sprites != null;
        arcaneProjectiles = new BufferedImage[6];
        for (int i = 0; i < 6; ++i) {
            arcaneProjectiles[i] = sprites.getSubimage(i * 16, 0, 16, 16);
        }

        sprites = ImageLoader.LoadImage("/moving objects/projectiles/enemy_sprites.png");
        assert sprites != null;
        enemyProjectiles = new BufferedImage[2];
        for (int i = 0; i < 2; ++i) {
            enemyProjectiles[i] = sprites.getSubimage(i * 17, 0, 17, 16);
        }
    }
}
