package Assets;

import java.awt.image.BufferedImage;

public class PlayerAssets {
    public static BufferedImage[] player_frames;

    public static void Init(){
        player_frames=new BufferedImage[4];
        for(int i=0;i<4;++i)
            player_frames[i]= ImageLoader.LoadImage("/moving objects/black_wizard/black_wizard_frame"+i+".gif");
    }
}
