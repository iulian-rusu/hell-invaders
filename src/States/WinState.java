package States;

import Assets.Images.BackgroundAssets;

import Assets.FontAssets;
import Assets.Images.GUIAssets;
import Entities.Player;
import Game.GlobalReferences;
import GameSystems.EventSystem.Events.AudioEvent;
import GUI.GUIButton;
import GUI.Text.GUIText;
import Game.GameWindow;

import java.awt.*;
import java.util.ArrayList;

/*! \class WinState
    \brief Implements the win mechanic of the game.
 */
public class WinState extends State {
    public static final int WIN_BACKGROUND_TRANSITION = 2;///< The number of seconds after which the background changes.
    public static final int WIN_TEXT_TRANSITION = WIN_BACKGROUND_TRANSITION + 1;///< The number of seconds after which the text appears on the screen.
    public static final int BACK_BUTTON_TRANSITION = WIN_TEXT_TRANSITION + 2;///< The number of seconds after which the "Back" button becomes visible.

    private final GUIText winText;///< Text objects that informs the player that they have won.
    private final Player player;///< Reference to the player.

    public WinState() {
        player = GlobalReferences.player;
        allButtons = new ArrayList<>();
        // Back button
        int backH = 110;
        int backW = 400;
        int backX = GameWindow.screenDimension.width / 2 - backW / 2;
        allButtons.add(new GUIButton(GUIAssets.back_button, GUIAssets.back_button_hovered, backX, 725, backW, backH));
        allButtons.get(0).AddActionListener(actionEvent -> {
            NotifyAllObservers(AudioEvent.STOP_CURRENT_STATE_MUSIC);
            StateManager.GetInstance().SetCurrentState(StateManager.StateIndex.UPGRADE_STATE);
        });
        // Level win text
        int textX = backX - 200;
        int textY = 550;
        winText = new GUIText("YOU WON!", textX, textY, 300);
        winText.SetFont(FontAssets.mainFontBold);
        winText.SetColor(Color.YELLOW);
    }

    @Override
    public void Update() {
        super.Update();
        if (secondCount < WIN_BACKGROUND_TRANSITION) {
            player.Update();
        }
        if (secondCount == WIN_BACKGROUND_TRANSITION && frameCount == 0) {
            NotifyAllObservers(AudioEvent.PLAY_CURRENT_STATE_MUSIC);
        }
        if (secondCount == BACK_BUTTON_TRANSITION && frameCount == 0) {
            allButtons.get(0).Unblock(GUIAssets.back_button);
        }
    }

    @Override
    public void Init() {
        super.Init();
        allButtons.get(0).Block(GUIAssets.back_button);
    }

    @Override
    public void Draw(Graphics2D g2d) {
        if (secondCount < WIN_BACKGROUND_TRANSITION) {
            // sub-state 1
            g2d.drawImage(BackgroundAssets.bgGameNormal, 0, 0, null);
            player.Draw(g2d);
        } else {
            //sub-state 2
            g2d.drawImage(BackgroundAssets.bgWin, 0, 0, null);
            if (secondCount > WIN_TEXT_TRANSITION) {
                winText.Draw(g2d);
            }
            if (secondCount > BACK_BUTTON_TRANSITION) {
                allButtons.get(0).Draw(g2d);
            }
        }
    }
}
