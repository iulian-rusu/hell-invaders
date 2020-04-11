package Assets;

import java.awt.image.BufferedImage;

public class ProjectileAssets {
    public static BufferedImage[] fire_projectiles;
    public static BufferedImage[] frost_projectiles;
    public static BufferedImage[] arcane_projectiles;
    public static BufferedImage[] enemy_projectiles;

    public static void InitFromSpriteSheet(){
        BufferedImage sprites=ImageLoader.LoadImage("/moving objects/projectiles/fire_sprites.png");
        assert sprites != null;
        fire_projectiles = new BufferedImage[2];
        for (int i = 0; i < 2; ++i) {
            fire_projectiles[i]=sprites.getSubimage(i*40,0,40,14);
        }

        sprites=ImageLoader.LoadImage("/moving objects/projectiles/frost_sprites.png");
        assert sprites != null;
        frost_projectiles=new BufferedImage[3];
        for (int i = 0; i < 3; ++i) {
            frost_projectiles[i] = sprites.getSubimage(i*40,0,40,14);
        }

        sprites=ImageLoader.LoadImage("/moving objects/projectiles/arcane_sprites.png");
        assert sprites != null;
        arcane_projectiles = new BufferedImage[6];
        for (int i = 0; i < 6; ++i) {
            arcane_projectiles[i] = sprites.getSubimage(i*16,0,16,16);
        }

        sprites=ImageLoader.LoadImage("/moving objects/projectiles/enemy_sprites.png");
        assert sprites != null;
        enemy_projectiles = new BufferedImage[2];
        for (int i = 0; i < 2; ++i) {
            enemy_projectiles[i] = sprites.getSubimage(i*17,0,17,16);
        }
    }
}
