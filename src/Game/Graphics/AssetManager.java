package Game.Graphics;

import Game.GameWindow.GameWindow;

public class AssetManager {

    public static void Init(GameWindow wnd){
        Backgrounds.Init(wnd);
        GUIAssets.Init();
        PlayerAssets.Init();
        EnemyAssets.Init();
        ProjectileAssets.Init();
    }
}
