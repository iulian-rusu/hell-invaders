package Assets.Images;

import Assets.FontAssets;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageLoader {
    
    public static void Init(){
        BackgroundAssets.Init();
        GUIAssets.Init();
        PlayerAssets.Init();
        EnemyAssets.Init();
        ProjectileAssets.Init();
        FontAssets.Init();
    }

    public static BufferedImage LoadImage(String path) {
        try {
            return ImageIO.read(ImageLoader.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BufferedImage GetScaledImage(BufferedImage original, int w, int h){
        Image tmp = original.getScaledInstance(w, h, BufferedImage.SCALE_FAST);
        BufferedImage resized = new BufferedImage(w, h, original.getType());
        resized.getGraphics().drawImage(tmp,0,0,null);
        return resized;
    }
}

