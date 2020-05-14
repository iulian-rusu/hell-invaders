package Assets.Images;

import Game.GameWindow;

import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * @brief Holds references to some background images.
 */
public class BackgroundAssets {
    public static BufferedImage bgGameNormal;///< The default game background.
    public static BufferedImage bgGameDark;///< The darker game background.
    public static BufferedImage bgMenuFirst;///< The first menu background.
    public static BufferedImage bgMenuSecond;///< The second menu background.
    public static BufferedImage bgWin;///< The win-state game background.
    public static BufferedImage bgLoss;///< The loss-state game background.

    /**
     * Loads all background images from the disk.
     */
    public static void Init() {
        int w = GameWindow.SCREEN_DIMENSION.width;
        int h = GameWindow.SCREEN_DIMENSION.height;

        bgGameNormal = ImageLoader.GetScaledImage(Objects.requireNonNull(ImageLoader.LoadImage("/backgrounds/bg_game_normal.png")), w, h);
        bgGameDark = ImageLoader.GetScaledImage(Objects.requireNonNull(ImageLoader.LoadImage("/backgrounds/bg_game_dark.png")), w, h);
        bgMenuFirst = ImageLoader.GetScaledImage(Objects.requireNonNull(ImageLoader.LoadImage("/backgrounds/bg_menu_first.png")), w, h);
        bgMenuSecond = ImageLoader.GetScaledImage(Objects.requireNonNull(ImageLoader.LoadImage("/backgrounds/bg_menu_second.png")), w, h);
        bgWin = ImageLoader.GetScaledImage(Objects.requireNonNull(ImageLoader.LoadImage("/backgrounds/bg_win.png")), w, h);
        bgLoss = ImageLoader.GetScaledImage(Objects.requireNonNull(ImageLoader.LoadImage("/backgrounds/bg_loss.png")), w, h);
    }
}
