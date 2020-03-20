package Assets;

import java.awt.image.BufferedImage;

public class GUIAssets {
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
    public static BufferedImage easy_button;
    public static BufferedImage easy_button_hovered;
    public static BufferedImage medium_button;
    public static BufferedImage medium_button_hovered;
    public static BufferedImage hard_button;
    public static BufferedImage hard_button_hovered;
    public static BufferedImage yellow_button;



    public static void Init() {
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
        easy_button = ImageLoader.LoadImage("/buttons/easy_button.png");
        easy_button_hovered = ImageLoader.LoadImage("/buttons/easy_button_hovered.png");
        medium_button = ImageLoader.LoadImage("/buttons/medium_button.png");
        medium_button_hovered = ImageLoader.LoadImage("/buttons/medium_button_hovered.png");
        hard_button = ImageLoader.LoadImage("/buttons/hard_button.png");
        hard_button_hovered = ImageLoader.LoadImage("/buttons/hard_button_hovered.png");
        yellow_button=ImageLoader.LoadImage("/buttons/yellow_button.png");
    }
}
