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


public class WinState extends State {
    public static final int WIN_BACKGROUND_TRANSITION = 2;
    public static final int WIN_TEXT_TRANSITION = WIN_BACKGROUND_TRANSITION + 1;
    public static final int BACK_BUTTON_TRANSITION = WIN_TEXT_TRANSITION + 2;

    private final GUIText winText;
    private final Player p;

    public WinState() {
        p = GlobalReferences.player;
        allButtons = new ArrayList<>();
        //back button
        int backH = 110;
        int backW = 400;
        int backX = GameWindow.screenDimension.width / 2 - backW / 2;
        allButtons.add(new GUIButton(GUIAssets.back_button, GUIAssets.back_button_hovered, backX, 725, backW, backH));
        allButtons.get(0).AddActionListener(actionEvent -> {
            NotifyAllObservers(AudioEvent.STOP_CURRENT_STATE_MUSIC);
            StateManager.GetInstance().SetCurrentState(StateManager.StateIndex.UPGRADE_STATE);
        });
        //level failure text
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
            p.Update();
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
            p.Draw(g2d);
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
