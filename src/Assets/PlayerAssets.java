package Assets;

import Entities.Player;

import java.awt.*;
import java.util.Objects;

public class PlayerAssets {
    public static Image[] player_frames;

    public static void Init() {
        player_frames = new Image[4];
        for (int i = 0; i < 4; ++i) {
            player_frames[i] = Objects.requireNonNull(ImageLoader.LoadImage("/moving objects/black_wizard/black_wizard_frame" + i + ".gif"))
                    .getSubimage(170, 90, 230, 279)
                    .getScaledInstance(Player.PLAYER_W, Player.PLAYER_H, Image.SCALE_FAST);
        }
    }
}
