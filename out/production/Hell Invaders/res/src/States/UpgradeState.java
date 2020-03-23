package States;

import Assets.GUIAssets;
import Audio.AudioManager;
import Audio.BackgroundMusic;
import GUI.GUIButton;
import Game.GameWindow;
import Assets.BackgroundAssets;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

public class UpgradeState extends ReversibleState {
    private final int playW=400;
    private  final int playH=110;
    private final int playX = Toolkit.getDefaultToolkit().getScreenSize().width / 2 - playW/2;

    public UpgradeState() {
        allButtons.get(0).AddActionListener(actionEvent -> StateManager.GetInstance().SetCurrentState(StateManager.StateIndex.MENU_STATE));
        allButtons.get(0).AddActionListener(actionEvent -> AudioManager.GetInstance().Stop(BackgroundMusic.upgradeMusic));
        allButtons.add(new GUIButton(GUIAssets.play_button, GUIAssets.play_button_hovered, playX, 725, playW, playH ));
        allButtons.get(1).AddActionListener(actionEvent -> StateManager.GetInstance().SetCurrentState(StateManager.StateIndex.GAME_STATE));
        allButtons.get(1).AddActionListener(actionEvent -> AudioManager.GetInstance().Stop(BackgroundMusic.upgradeMusic));
    }

    @Override
    public void Init() {
        super.Init();
        AudioManager.GetInstance().Play(BackgroundMusic.upgradeMusic);
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

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
            AudioManager.GetInstance().Stop(BackgroundMusic.upgradeMusic);
            StateManager.GetInstance().SetCurrentState(StateManager.StateIndex.MENU_STATE);
        }
    }
}
