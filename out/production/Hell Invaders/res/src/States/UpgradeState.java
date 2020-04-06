package States;

import Assets.GUIAssets;
import Audio.SoundEffect;
import Audio.SoundEffectAssets;
import Entities.Player;
import EventSystem.Events.AudioEvent;
import GUI.GUIButton;
import Game.GameWindow;
import Assets.BackgroundAssets;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class UpgradeState extends ReversibleState {

    public UpgradeState() {
        //back button events
        allButtons.get(0).AddActionListener(actionEvent -> {
            NotifyAllObservers(AudioEvent.STOP_CURRENT_STATE_MUSIC);
            StateManager.GetInstance().SetCurrentState(StateManager.StateIndex.MENU_STATE);
        });
        //play button
        int playH = 110;
        int playW = 400;
        int playX = GameWindow.wndDimension.width / 2 - playW / 2;
        allButtons.add(new GUIButton(GUIAssets.play_button, GUIAssets.play_button_hovered, playX, 725, playW, playH));
        allButtons.get(1).AddActionListener(actionEvent -> {
            NotifyAllObservers(AudioEvent.STOP_CURRENT_STATE_MUSIC);
            StateManager.GetInstance().SetCurrentState(StateManager.StateIndex.GAME_STATE);
        });
    }

    @Override
    public void Init() {
        super.Init();
        NotifyAllObservers(AudioEvent.PLAY_CURRENT_STATE_MUSIC);
    }

    @Override
    public void Draw(GameWindow wnd) {
        BufferStrategy bs = wnd.GetCanvas().getBufferStrategy();
        Graphics g = bs.getDrawGraphics();
        g.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());
        g.drawImage(BackgroundAssets.bg_game_dark, 0, 0, null);
        for (GUIButton b : allButtons) {
            b.Draw(g);
        }
        bs.show();
        g.dispose();
    }
}
