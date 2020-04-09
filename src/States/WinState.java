package States;

import Assets.BackgroundAssets;

import Assets.FontAssets;
import Assets.GUIAssets;
import Entities.Player;
import EventSystem.Events.AudioEvent;
import GUI.GUIButton;
import GUI.GUIText;
import Game.GameWindow;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;


public class WinState extends State {

    public static final int WIN_BACKGROUND_TRANSITION = 2;
    public static final int WIN_TEXT_TRANSITION = WIN_BACKGROUND_TRANSITION + 2;
    public static final int BACK_BUTTON_TRANSITION = WIN_TEXT_TRANSITION + 3;

    private GUIText winText;
    private Player p;

    public WinState() {
        p = Player.GetInstance();
        allButtons = new ArrayList<>();
        //back button
        int backH = 110;
        int backW = 400;
        int backX = GameWindow.wndDimension.width / 2 - backW / 2;
        allButtons.add(new GUIButton(GUIAssets.back_button, GUIAssets.back_button_hovered, backX, 725, backW, backH));
        allButtons.get(0).AddActionListener(actionEvent -> {
            NotifyAllObservers(AudioEvent.STOP_CURRENT_STATE_MUSIC);
            StateManager.GetInstance().SetCurrentState(StateManager.StateIndex.UPGRADE_STATE);
        });
        //level failure text
        int textX = backX - 200;
        int textY = 550;
        winText = new GUIText("YOU WON!", textX, textY, 300);
        winText.SetFont(FontAssets.mainFont_bold);
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
            allButtons.get(0).Unblock();
        }
    }

    @Override
    public void Init() {
        super.Init();
        allButtons.get(0).Block();
    }

    @Override
    public void Draw(GameWindow wnd) {
        BufferStrategy bs = wnd.GetCanvas().getBufferStrategy();
        Graphics g = bs.getDrawGraphics();
        g.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());
        if (secondCount < WIN_BACKGROUND_TRANSITION) {
            // sub-state 1
            g.drawImage(BackgroundAssets.bg_game, 0, 0, null);
            p.Draw(g);
        } else {
            //sub-state 2
            g.drawImage(BackgroundAssets.bg_win, 0, 0, null);
            if (secondCount > WIN_TEXT_TRANSITION) {
                winText.Draw(g);
            }
            if (secondCount > BACK_BUTTON_TRANSITION) {
                allButtons.get(0).Draw(g);
            }
        }
        bs.show();
        g.dispose();
    }
}
