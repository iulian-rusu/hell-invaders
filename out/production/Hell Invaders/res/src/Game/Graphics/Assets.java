package Game.Graphics;

import java.awt.image.BufferedImage;

public class Assets {
    //backgrounds
    public static BufferedImage bg_game;
    public static BufferedImage bg_menu0;
    public static BufferedImage bg_menu1;
    public static BufferedImage bg_win;
    public static BufferedImage bg_game_over;
    //GUI elements
    public static BufferedImage new_game_button;
    public static BufferedImage new_game_button_hovered;
    public static BufferedImage resume_button;
    public static BufferedImage resume_button_hovered;
    public static BufferedImage resume_button_blocked;
    public static BufferedImage options_button;
    public static BufferedImage options_button_hovered;
    public static BufferedImage stats_button;
    public static BufferedImage stats_button_hovered;
    public static BufferedImage quit_button;
    public static BufferedImage quit_button_hovered;

    public static void Init() {
        bg_game = ImageLoader.LoadImage("/backgrounds/bg_game.png");
        bg_menu0= ImageLoader.LoadImage("/backgrounds/bg_menu0.png");
        bg_menu1= ImageLoader.LoadImage("/backgrounds/bg_menu1.png");
        bg_win= ImageLoader.LoadImage("/backgrounds/bg_win.png");
        bg_game_over= ImageLoader.LoadImage("/backgrounds/bg_game_over.png");

        new_game_button = ImageLoader.LoadImage("/buttons/new_game_button.png");
        new_game_button_hovered = ImageLoader.LoadImage("/buttons/new_game_button_hovered.png");
        resume_button = ImageLoader.LoadImage("/buttons/resume_button.png");
        resume_button_hovered = ImageLoader.LoadImage("/buttons/resume_button_hovered.png");
        resume_button_blocked = ImageLoader.LoadImage("/buttons/resume_button_blocked.png");
        options_button = ImageLoader.LoadImage("/buttons/options_button.png");
        options_button_hovered = ImageLoader.LoadImage("/buttons/options_button_hovered.png");
        stats_button = ImageLoader.LoadImage("/buttons/stats_button.png");
        stats_button_hovered = ImageLoader.LoadImage("/buttons/stats_button_hovered.png");
        quit_button = ImageLoader.LoadImage("/buttons/quit_button.png");
        quit_button_hovered = ImageLoader.LoadImage("/buttons/quit_button_hovered.png");
    }
}
