package Assets;

import Game.GameWindow;

import java.awt.image.BufferedImage;
import java.util.Objects;

public class BackgroundAssets {
    public static BufferedImage bg_game;
    public static BufferedImage bg_game_dark;
    public static BufferedImage bg_menu0;
    public static BufferedImage bg_menu1;
    public static BufferedImage bg_win;
    public static BufferedImage bg_game_over;

    public static void Init(GameWindow wnd) {
        int w=wnd.GetWndWidth();
        int h=wnd.GetWndHeight();

        bg_game = ImageLoader.GetScaledImage(Objects.requireNonNull(ImageLoader.LoadImage("/backgrounds/bg_game.png")),w,h);
        bg_game_dark = ImageLoader.GetScaledImage(Objects.requireNonNull(ImageLoader.LoadImage("/backgrounds/bg_game_dark.png")),w,h);
        bg_menu0 = ImageLoader.GetScaledImage(Objects.requireNonNull(ImageLoader.LoadImage("/backgrounds/bg_menu0.png")),w,h);
        bg_menu1 = ImageLoader.GetScaledImage(Objects.requireNonNull(ImageLoader.LoadImage("/backgrounds/bg_menu1.png")),w,h);
        bg_win = ImageLoader.GetScaledImage(Objects.requireNonNull(ImageLoader.LoadImage("/backgrounds/bg_win.png")),w,h);
        bg_game_over = ImageLoader.GetScaledImage(Objects.requireNonNull(ImageLoader.LoadImage("/backgrounds/bg_game_over.png")),w,h);
    }
}
