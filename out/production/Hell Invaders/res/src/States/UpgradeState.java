package States;

import Assets.Images.GUIAssets;
import GameSystems.EventSystem.Events.AudioEvent;
import GUI.GUIButton;
import Game.GameWindow;
import Assets.Images.BackgroundAssets;
import GameSystems.UpgradeSystem.ExperiencePanel;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class UpgradeState extends ReversibleState {
    ExperiencePanel experiencePanel;
    
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

        experiencePanel=ExperiencePanel.GetInstance();
    }

    @Override
    public void Init() {
        super.Init();
        ExperiencePanel.GetInstance().UpdateValue();//TODO: remove this from here, find another way to init experience panel
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
        experiencePanel.Draw(g);
        bs.show();
        g.dispose();
    }
}
