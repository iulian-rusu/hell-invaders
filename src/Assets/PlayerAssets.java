package Assets;

import java.awt.*;
import java.util.Objects;

public class PlayerAssets {
    public static Image[] player_frames;
    public static final int playerH=160;
    public static final int playerW=130;
    public static final int playerX=25;
    public static final int playerY=420;

    public static void Init(){
        player_frames=new Image[4];
        for(int i=0;i<4;++i)
            player_frames[i]= Objects.requireNonNull(ImageLoader.LoadImage("/moving objects/black_wizard/black_wizard_frame" + i + ".gif"))
                    .getSubimage(170,90,230,279)
                    .getScaledInstance(playerW,playerH,Image.SCALE_FAST);

    }
}
