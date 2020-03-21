package Assets;

import java.awt.*;

import java.util.Objects;

public class ProjectileAssets {
    public static Image[] frost_projectile_frames;
    public static Image[] fire_projectile_frames;
    public static Image[] arcane_projectile_frames;

    public static void Init() {
        fire_projectile_frames = new Image[2];
        for (int i = 0; i < 2; ++i) {
            fire_projectile_frames[i] = Objects.requireNonNull(ImageLoader.LoadImage("/moving objects/projectiles/fire_projectile" + i + ".png"))
                    .getScaledInstance(80, 50, Image.SCALE_FAST);
        }
        frost_projectile_frames = new Image[3];
        for (int i = 0; i < 3; ++i) {
            frost_projectile_frames[i] = Objects.requireNonNull(ImageLoader.LoadImage("/moving objects/projectiles/frost_projectile" + i + ".png"))
                    .getScaledInstance(80, 50, Image.SCALE_FAST);
        }
        arcane_projectile_frames = new Image[6];
        for (int i = 0; i < 6; ++i) {
            arcane_projectile_frames[i] = Objects.requireNonNull(ImageLoader.LoadImage("/moving objects/projectiles/arcane_projectile" + i + ".png"))
                    .getScaledInstance(50, 30, Image.SCALE_FAST);
        }
    }
}
