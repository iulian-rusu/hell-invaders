package Assets.Images;

import java.awt.image.BufferedImage;

public class EnemyAssets {
    public static BufferedImage[] dragonFrames;
    public static BufferedImage[] monsterFrames;

    public static void Init(){
        BufferedImage sprites= ImageLoader.LoadImage("/moving objects/dragon/dragon_sprites.png");
        assert sprites!=null;
        dragonFrames =new BufferedImage[5];
        for(int i=0;i<5;++i){
            int x=i%3;
            int y=i/3;
            dragonFrames[i]= sprites.getSubimage(x*640,y*640,640,640)
                    .getSubimage(100,300,450,245);
        }

        sprites=ImageLoader.LoadImage("/moving objects/monster/monster_sprites.png");
        assert sprites!=null;
        monsterFrames =new BufferedImage[4];
        for(int i=0;i<4;++i){
            int x=i%2;
            int y=i/2;
            monsterFrames[i]= sprites.getSubimage(x*256,y*256,256,256)
                    .getSubimage(20,30,200,225);
        }
    }
}
