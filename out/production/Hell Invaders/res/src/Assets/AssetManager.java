package Assets;

import Game.GameWindow;

public class AssetManager {

    public static void Init(GameWindow wnd){
        BackgroundAssets.Init(wnd);
        GUIAssets.Init();
        PlayerAssets.Init();
        EnemyAssets.Init();
        ProjectileAssets.Init();
        FontAssets.Init();
    }
}
