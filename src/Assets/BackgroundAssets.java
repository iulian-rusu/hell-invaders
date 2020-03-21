package Assets;

import Game.GameWindow;

import java.awt.*;
import java.util.Objects;

public class BackgroundAssets {
    public static Image bg_game;
    public static Image bg_game_dark;
    public static Image bg_menu0;
    public static Image bg_menu1;
    public static Image bg_win;
    public static Image bg_game_over;

    public static void Init(GameWindow wnd) {
        bg_game = Objects.requireNonNull(ImageLoader.LoadImage("/backgrounds/bg_game.png"))
                .getScaledInstance(wnd.GetWndWidth(), wnd.GetWndHeight(), Image.SCALE_FAST);
        bg_game_dark = Objects.requireNonNull(ImageLoader.LoadImage("/backgrounds/bg_game_dark.png"))
                .getScaledInstance(wnd.GetWndWidth(), wnd.GetWndHeight(), Image.SCALE_FAST);
        bg_menu0 = Objects.requireNonNull(ImageLoader.LoadImage("/backgrounds/bg_menu0.png"))
                .getScaledInstance(wnd.GetWndWidth(), wnd.GetWndHeight(), Image.SCALE_FAST);
        bg_menu1 = Objects.requireNonNull(ImageLoader.LoadImage("/backgrounds/bg_menu1.png"))
                .getScaledInstance(wnd.GetWndWidth(), wnd.GetWndHeight(), Image.SCALE_FAST);
        bg_win = Objects.requireNonNull(ImageLoader.LoadImage("/backgrounds/bg_win.png"))
                .getScaledInstance(wnd.GetWndWidth(), wnd.GetWndHeight(), Image.SCALE_FAST);
        bg_game_over = Objects.requireNonNull(ImageLoader.LoadImage("/backgrounds/bg_game_over.png"))
                .getScaledInstance(wnd.GetWndWidth(), wnd.GetWndHeight(), Image.SCALE_FAST);
    }
}
