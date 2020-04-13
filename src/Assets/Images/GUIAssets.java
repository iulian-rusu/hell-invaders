package Assets.Images;

import java.awt.image.BufferedImage;

public class GUIAssets {
    public static void Init() {
        LoadMenuAssets();
        LoadOpionsAssets();
        LoadUpgradeAssets();
    }

    private static void LoadMenuAssets() {
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

    private static void LoadOpionsAssets() {
        easy_button = ImageLoader.LoadImage("/buttons/easy_button.png");
        easy_button_hovered = ImageLoader.LoadImage("/buttons/easy_button_hovered.png");
        medium_button = ImageLoader.LoadImage("/buttons/medium_button.png");
        medium_button_hovered = ImageLoader.LoadImage("/buttons/medium_button_hovered.png");
        hard_button = ImageLoader.LoadImage("/buttons/hard_button.png");
        hard_button_hovered = ImageLoader.LoadImage("/buttons/hard_button_hovered.png");
        yellow_button = ImageLoader.LoadImage("/buttons/yellow_button.png");
        green_button = ImageLoader.LoadImage("/buttons/green_button.png");
        back_button = ImageLoader.LoadImage("/buttons/back_button.png");
        back_button_hovered = ImageLoader.LoadImage("/buttons/back_button_hovered.png");
    }

    private static void LoadUpgradeAssets() {
        damage = ImageLoader.LoadImage("/others/damage.png");
        crit = ImageLoader.LoadImage("/others/crit.png");
        projectiles = ImageLoader.LoadImage("/others/projectiles.png");
        upgrade_frost = ImageLoader.LoadImage("/others/upgrade_frost.png");
        upgrade_aecane = ImageLoader.LoadImage("/others/upgrade_arcane.png");
        buy_button = ImageLoader.LoadImage("/buttons/buy_button.png");
        buy_button_hovered = ImageLoader.LoadImage("/buttons/buy_button_hovered.png");
        play_button = ImageLoader.LoadImage("/buttons/play_button.png");
        play_button_hovered = ImageLoader.LoadImage("/buttons/play_button_hovered.png");
    }

    //main menu buttons
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
    //options buttons
    public static BufferedImage easy_button;
    public static BufferedImage easy_button_hovered;
    public static BufferedImage medium_button;
    public static BufferedImage medium_button_hovered;
    public static BufferedImage hard_button;
    public static BufferedImage hard_button_hovered;
    public static BufferedImage yellow_button;
    public static BufferedImage green_button;
    public static BufferedImage back_button;
    public static BufferedImage back_button_hovered;
    //upgrade icons
    public static BufferedImage damage;
    public static BufferedImage crit;
    public static BufferedImage projectiles;
    public static BufferedImage upgrade_frost;
    public static BufferedImage upgrade_aecane;
    //upgrade buttons
    public static BufferedImage buy_button;
    public static BufferedImage buy_button_hovered;
    public static BufferedImage play_button;
    public static BufferedImage play_button_hovered;
}
