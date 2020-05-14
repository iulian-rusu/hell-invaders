package States;

import Assets.FontAssets;
import Assets.Images.BackgroundAssets;
import Assets.Images.GUIAssets;
import GUI.GUIButton;
import GUI.Text.GUIText;
import Game.GameWindow;
import GameSystems.EventSystem.Events.AudioEvent;

import java.awt.*;
import java.util.ArrayList;

/**
 * @brief Implements the loss mechanic of the game.
 */
public class LossState extends State {
    private final GUIText gameOverText;///< Text that tells the player they lost.

    /**
     * Constructor without parameters.
     */
    public LossState() {
        allButtons = new ArrayList<>();
        // Back button positioning
        int backButtonW = 400;
        int backButtonH = 110;
        int backButtonX = GameWindow.SCREEN_DIMENSION.width / 2 - backButtonW / 2;
        allButtons.add(new GUIButton(GUIAssets.back_button, GUIAssets.back_button_hovered, backButtonX, 725, backButtonW, backButtonH));
        allButtons.get(0).AddActionListener(actionEvent -> {
            NotifyAllObservers(AudioEvent.STOP_CURRENT_STATE_MUSIC);
            StateManager.GetInstance().SetCurrentState(StateIndex.UPGRADE_STATE);
        });
        // Game over text positioning
        int textX = backButtonX - 200;
        int textY = 550;
        gameOverText = new GUIText("YOU DIED.", textX, textY, 300);
        gameOverText.SetFont(FontAssets.mainFontBold);
        gameOverText.SetColor(Color.RED);
    }

    @Override
    public void Update() {
        super.Update();
        if (secondCount == 5 && frameCount == 0) {
            allButtons.get(0).Unblock(GUIAssets.back_button);
        }
    }

    @Override
    public void Init() {
        super.Init();
        NotifyAllObservers(AudioEvent.PLAY_CURRENT_STATE_MUSIC);
        allButtons.get(0).Block(GUIAssets.back_button);
    }

    @Override
    public void Draw(Graphics2D g2d) {
        g2d.drawImage(BackgroundAssets.bgLoss, 0, 0, null);
        if (secondCount > 1) {
            gameOverText.Draw(g2d);
        }
        if (secondCount > 5) {
            allButtons.get(0).Draw(g2d);
        }
    }

}
