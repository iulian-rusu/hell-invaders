package Assets.Images;

import Game.GameWindow;

import java.awt.image.BufferedImage;
import java.util.Objects;

public class BackgroundAssets {

    public static BufferedImage bgGameNormal;
    public static BufferedImage bgGameDark;
    public static BufferedImage bgMenuFirst;
    public static BufferedImage bgMenuSecond;
    public static BufferedImage bgWin;
    public static BufferedImage bgLoss;

    public static void Init() {
        int w = GameWindow.SCREEN_DIMENSION.width;
        int h = GameWindow.SCREEN_DIMENSION.height;

        bgGameNormal = ImageLoader.GetScaledImage(Objects.requireNonNull(ImageLoader.LoadImage("/backgrounds/bg_game_normal.png")),w,h);
        bgGameDark = ImageLoader.GetScaledImage(Objects.requireNonNull(ImageLoader.LoadImage("/backgrounds/bg_game_dark.png")),w,h);
        bgMenuFirst = ImageLoader.GetScaledImage(Objects.requireNonNull(ImageLoader.LoadImage("/backgrounds/bg_menu_first.png")),w,h);
        bgMenuSecond = ImageLoader.GetScaledImage(Objects.requireNonNull(ImageLoader.LoadImage("/backgrounds/bg_menu_second.png")),w,h);
        bgWin = ImageLoader.GetScaledImage(Objects.requireNonNull(ImageLoader.LoadImage("/backgrounds/bg_win.png")),w,h);
        bgLoss = ImageLoader.GetScaledImage(Objects.requireNonNull(ImageLoader.LoadImage("/backgrounds/bg_loss.png")),w,h);
    }
}
