package Assets.Images;

import java.awt.image.BufferedImage;

/**
 * @brief Holds references to asssets used in the graphical interface of the game.
 */
public class GUIAssets {
    public static BufferedImage target_cursor;///< Combat curson texture.
    // Main menu buttons
    public static BufferedImage new_game_button;
    public static BufferedImage new_game_button_hovered;
    public static BufferedImage resume_button;
    public static BufferedImage resume_button_hovered;
    public static BufferedImage resume_button_blocked;
    public static BufferedImage options_button;
    public static BufferedImage options_button_hovered;
    public static BufferedImage about_button;
    public static BufferedImage about_button_hovered;
    public static BufferedImage quit_button;
    public static BufferedImage quit_button_hovered;
    // Options buttons
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
    public static BufferedImage full_screen_button;
    public static BufferedImage full_screen_button_hovered;
    public static BufferedImage windowed_button;
    public static BufferedImage windowed_button_hovered;
    // Upgrade buttons
    public static BufferedImage buy_button;
    public static BufferedImage buy_button_hovered;
    public static BufferedImage buy_button_blocked;
    public static BufferedImage play_button;
    public static BufferedImage play_button_hovered;
    // Upgrade icons
    public static BufferedImage damage;
    public static BufferedImage crit;
    public static BufferedImage projectiles;
    public static BufferedImage upgrade_frost;
    public static BufferedImage upgrade_arcane;

    /**
     * Loads all GUI assets from their spritesheet on the disk.
     */
    public static void Init() {
        target_cursor = ImageLoader.LoadImage("/others/target_cursor.png");

        // Load all buttons
        int buttonW = 124;
        int buttonH = 29;
        BufferedImage spritesheet = ImageLoader.LoadImage("/buttons/button_sprites.png");
        assert spritesheet != null;

        new_game_button = ImageLoader.GetSubimageAt(spritesheet, 1, 6, buttonW, buttonH);
        new_game_button_hovered = ImageLoader.GetSubimageAt(spritesheet, 0, 7, buttonW, buttonH);

        resume_button = ImageLoader.GetSubimageAt(spritesheet, 2, 5, buttonW, buttonH);
        resume_button_hovered = ImageLoader.GetSubimageAt(spritesheet, 2, 7, buttonW, buttonH);
        resume_button_blocked = ImageLoader.GetSubimageAt(spritesheet, 2, 6, buttonW, buttonH);

        options_button = ImageLoader.GetSubimageAt(spritesheet, 1, 7, buttonW, buttonH);
        options_button_hovered = ImageLoader.GetSubimageAt(spritesheet, 2, 0, buttonW, buttonH);

        about_button = ImageLoader.GetSubimageAt(spritesheet, 0, 0, buttonW, buttonH);
        about_button_hovered = ImageLoader.GetSubimageAt(spritesheet, 0, 1, buttonW, buttonH);

        quit_button = ImageLoader.GetSubimageAt(spritesheet, 2, 3, buttonW, buttonH);
        quit_button_hovered = ImageLoader.GetSubimageAt(spritesheet, 2, 4, buttonW, buttonH);

        easy_button = ImageLoader.GetSubimageAt(spritesheet, 1, 0, buttonW, buttonH);
        easy_button_hovered = ImageLoader.GetSubimageAt(spritesheet, 1, 1, buttonW, buttonH);

        medium_button = ImageLoader.GetSubimageAt(spritesheet, 1, 5, buttonW, buttonH);
        medium_button_hovered = ImageLoader.GetSubimageAt(spritesheet, 0, 6, buttonW, buttonH);

        hard_button = ImageLoader.GetSubimageAt(spritesheet, 1, 4, buttonW, buttonH);
        hard_button_hovered = ImageLoader.GetSubimageAt(spritesheet, 0, 5, buttonW, buttonH);

        yellow_button = ImageLoader.GetSubimageAt(spritesheet, 2, 8, buttonW, buttonH);

        green_button = ImageLoader.GetSubimageAt(spritesheet, 0, 4, buttonW, buttonH);

        back_button = ImageLoader.GetSubimageAt(spritesheet, 0, 2, buttonW, buttonH);
        back_button_hovered = ImageLoader.GetSubimageAt(spritesheet, 0, 3, buttonW, buttonH);

        play_button = ImageLoader.GetSubimageAt(spritesheet, 2, 1, buttonW, buttonH);
        play_button_hovered = ImageLoader.GetSubimageAt(spritesheet, 2, 2, buttonW, buttonH);

        full_screen_button = ImageLoader.GetSubimageAt(spritesheet, 1, 2, buttonW, buttonH);
        full_screen_button_hovered = ImageLoader.GetSubimageAt(spritesheet, 1, 3, buttonW, buttonH);

        windowed_button = ImageLoader.GetSubimageAt(spritesheet, 0, 8, buttonW, buttonH);
        windowed_button_hovered = ImageLoader.GetSubimageAt(spritesheet, 1, 8, buttonW, buttonH);

        // Load icons
        int iconW = 40;
        int iconH = 40;
        spritesheet = ImageLoader.LoadImage("/others/icon_sprites.png");
        assert spritesheet != null;

        damage = ImageLoader.GetSubimageAt(spritesheet, 1, 1, iconW, iconH);
        crit = ImageLoader.GetSubimageAt(spritesheet, 2, 0, iconW, iconH);
        projectiles = ImageLoader.GetSubimageAt(spritesheet, 1, 0, iconW, iconH);
        upgrade_frost = ImageLoader.GetSubimageAt(spritesheet, 2, 1, iconW, iconH);
        upgrade_arcane = ImageLoader.GetSubimageAt(spritesheet, 0, 1, iconW, iconH);

        // Load some other buttons
        buy_button = ImageLoader.LoadImage("/buttons/buy_button.png");
        buy_button_hovered = ImageLoader.LoadImage("/buttons/buy_button_hovered.png");
        buy_button_blocked = ImageLoader.LoadImage("/buttons/buy_button_blocked.png");
    }


}
