package Assets.Images;

import java.awt.image.BufferedImage;

/**
 * @brief Holds references to asssets used in the graphical interface of the game.
 */
public class GUIAssets {
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

        new_game_button = GetSubimageAt(spritesheet,1, 6, buttonW, buttonH);
        new_game_button_hovered = GetSubimageAt(spritesheet,0,7, buttonW, buttonH);

        resume_button = GetSubimageAt(spritesheet,2,5, buttonW, buttonH);
        resume_button_hovered = GetSubimageAt(spritesheet,2,7, buttonW, buttonH);
        resume_button_blocked = GetSubimageAt(spritesheet,2,6, buttonW, buttonH);

        options_button = GetSubimageAt(spritesheet,1,7, buttonW, buttonH);
        options_button_hovered = GetSubimageAt(spritesheet,2,0, buttonW, buttonH);

        about_button = GetSubimageAt(spritesheet,0,0, buttonW, buttonH);
        about_button_hovered = GetSubimageAt(spritesheet,0,1, buttonW, buttonH);

        quit_button = GetSubimageAt(spritesheet,2,3, buttonW, buttonH);
        quit_button_hovered = GetSubimageAt(spritesheet,2,4, buttonW, buttonH);

        easy_button = GetSubimageAt(spritesheet,1, 0, buttonW, buttonH);
        easy_button_hovered = GetSubimageAt(spritesheet,1,1, buttonW, buttonH);

        medium_button = GetSubimageAt(spritesheet,1,5, buttonW, buttonH);
        medium_button_hovered = GetSubimageAt(spritesheet,0,6, buttonW, buttonH);

        hard_button = GetSubimageAt(spritesheet,1,4, buttonW, buttonH);
        hard_button_hovered = GetSubimageAt(spritesheet,0,5, buttonW, buttonH);

        yellow_button = GetSubimageAt(spritesheet,2,8, buttonW, buttonH);

        green_button = GetSubimageAt(spritesheet,0,4, buttonW, buttonH);

        back_button = GetSubimageAt(spritesheet,0,2, buttonW, buttonH);
        back_button_hovered = GetSubimageAt(spritesheet,0,3, buttonW, buttonH);

        play_button = GetSubimageAt(spritesheet,2,1, buttonW, buttonH);
        play_button_hovered = GetSubimageAt(spritesheet,2,2, buttonW, buttonH);

        full_screen_button = GetSubimageAt(spritesheet, 1,2, buttonW, buttonH);
        full_screen_button_hovered = GetSubimageAt(spritesheet, 1,3, buttonW, buttonH);

        windowed_button = GetSubimageAt(spritesheet, 0,8, buttonW, buttonH);
        windowed_button_hovered = GetSubimageAt(spritesheet, 1,8, buttonW, buttonH);

        // Load some other textures
        damage = ImageLoader.LoadImage("/others/damage.png");
        crit = ImageLoader.LoadImage("/others/crit.png");
        projectiles = ImageLoader.LoadImage("/others/projectiles.png");
        upgrade_frost = ImageLoader.LoadImage("/others/upgrade_frost.png");
        upgrade_arcane = ImageLoader.LoadImage("/others/upgrade_arcane.png");
        buy_button = ImageLoader.LoadImage("/buttons/buy_button.png");
        buy_button_hovered = ImageLoader.LoadImage("/buttons/buy_button_hovered.png");
        buy_button_blocked = ImageLoader.LoadImage("/buttons/buy_button_blocked.png");
    }

    private static BufferedImage GetSubimageAt(BufferedImage spritesheet, int x, int y, int w, int h) {
        return spritesheet.getSubimage(x * w, y * h, w, h);
    }

    // Main cursor texture
    public static BufferedImage target_cursor;
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
    // Upgrade icons
    public static BufferedImage damage;
    public static BufferedImage crit;
    public static BufferedImage projectiles;
    public static BufferedImage upgrade_frost;
    public static BufferedImage upgrade_arcane;
    // Upgrade buttons
    public static BufferedImage buy_button;
    public static BufferedImage buy_button_hovered;
    public static BufferedImage buy_button_blocked;
    public static BufferedImage play_button;
    public static BufferedImage play_button_hovered;
}
