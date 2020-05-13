package Assets.Images;

import Assets.FontAssets;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @brief Helper clas that loads an image from the specified path on the disk.
 */
public class ImageLoader {

    /**
     * Initializes all image assets.
     */
    public static void Init() {
        BackgroundAssets.Init();
        GUIAssets.Init();
        PlayerAssets.Init();
        EnemyAssets.Init();
        ProjectileAssets.Init();
        FontAssets.Init();
    }

    /**
     * Loads an image as a BufferedImage from the path specified.
     *
     * @param path The path to the image on the disk.
     * @return A BufferedImage instance.
     */
    public static BufferedImage LoadImage(String path) {
        try {
            return ImageIO.read(ImageLoader.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates a scaled version of the original image.
     *
     * @param original A BufferedImage reference to an image object.
     * @param w        The new width of the image.
     * @param h        The new height of the image.
     * @return A new BufferedImage that is a scaled version of the original.
     */
    public static BufferedImage GetScaledImage(BufferedImage original, int w, int h) {
        Image tmp = original.getScaledInstance(w, h, BufferedImage.SCALE_FAST);
        BufferedImage resized = new BufferedImage(w, h, original.getType());
        resized.getGraphics().drawImage(tmp, 0, 0, null);
        return resized;
    }
}

