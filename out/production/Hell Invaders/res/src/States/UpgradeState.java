package States;

import Audio.AudioManager;
import Audio.BackgroundMusic;
import Game.GameWindow;
import Assets.BackgroundAssets;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;

public class UpgradeState extends State {

    public UpgradeState(){
    }

    @Override
    public void Init() {
        super.Init();
        AudioManager.GetInstance().Play(BackgroundMusic.upgradeMusic);
    }

    @Override
    public void Update() {
        super.Update();
    }

    @Override
    public void Draw(GameWindow wnd) {
        BufferStrategy bs = wnd.GetCanvas().getBufferStrategy();
        Graphics g = bs.getDrawGraphics();
        g.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());
        g.drawImage(BackgroundAssets.bg_game_dark, 0, 0, null);
        bs.show();
        g.dispose();
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch(keyEvent.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                AudioManager.GetInstance().Stop(BackgroundMusic.upgradeMusic);
                StateManager.GetInstance().SetCurrentState(StateManager.StateIndex.MENU_STATE);
                break;
            case KeyEvent.VK_ENTER:
                AudioManager.GetInstance().Stop(BackgroundMusic.upgradeMusic);
                StateManager.GetInstance().SetCurrentState(StateManager.StateIndex.GAME_STATE);
        }
    }
}
