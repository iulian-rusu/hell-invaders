package Assets;

public class AssetInitializer {
    public static void Init(){
        BackgroundAssets.Init();
        GUIAssets.Init();
        PlayerAssets.InitFromSpriteSheet();
        EnemyAssets.InitFromSpriteSheet();
        ProjectileAssets.InitFromSpriteSheet();
        FontAssets.Init();
    }
}
