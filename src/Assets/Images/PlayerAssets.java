package Assets.Images;

import java.awt.image.BufferedImage;

/**
 * @brief Holds referenes to player textures.
 */
public class PlayerAssets {
    public static BufferedImage[] playerFrames;///< All player assets.

    /**
     * Loads all player texture from the plauer spritesheet on the disk.
     */
    public static void Init() {
        BufferedImage sprites = ImageLoader.LoadImage("/moving objects/black_wizard/black_wizard_sprites.png");
        assert sprites != null;
        playerFrames = new BufferedImage[4];
        for (int i = 0; i < 4; ++i) {
            int x = (i % 2) * 500;
            int y = i / 2 * 500;
            playerFrames[i] = sprites.getSubimage(x, y, 500, 500)
                    .getSubimage(170, 90, 230, 279);
        }
    }
}
