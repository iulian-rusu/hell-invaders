package Assets;

import java.awt.image.BufferedImage;

public class PlayerAssets {
    public static BufferedImage[] player_frames;


    public static void InitFromSpriteSheet() {
        BufferedImage sprites = ImageLoader.LoadImage("/moving objects/black_wizard/black_wizard_sprites.png");
        assert sprites != null;
        player_frames = new BufferedImage[4];
        for (int i = 0; i < 4; ++i) {
            int x = (i % 2) * 500;
            int y = i / 2 * 500;
            player_frames[i] = sprites.getSubimage(x, y, 500, 500)
                    .getSubimage(170, 90, 230, 279);
        }
    }
}
