package Assets;

import java.awt.image.BufferedImage;

public class EnemyAssets {
    public static BufferedImage[] dragon_frames;
    public static BufferedImage[] monster_frames;

    public static void Init(){
        dragon_frames=new BufferedImage[5];
        for(int i=0;i<5;++i){
            dragon_frames[i]=ImageLoader.LoadImage("/moving objects/dragon/dragon_frame"+i+".png");
        }
        monster_frames=new BufferedImage[4];
        for(int i=0;i<4;++i){
            monster_frames[i]=ImageLoader.LoadImage("/moving objects/monster/monster_frame"+i+".png");
        }
    }
}
