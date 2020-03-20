package States;

import Audio.AudioManager;
import Audio.BackgroundMusic;
import Game.GameWindow;
import Assets.BackgroundAssets;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;

public class GameState extends State {

    @Override
    public void Init() {
        super.Init();
        AudioManager.GetInstance().Play(BackgroundMusic.gameMusic);
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
        g.drawImage(BackgroundAssets.bg_game, 0, 0, null);
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
        switch(keyEvent.getKeyCode()){
            case KeyEvent.VK_ESCAPE:
                AudioManager.GetInstance().Stop(BackgroundMusic.gameMusic);
                StateManager.GetInstance().SetCurrentState(StateManager.StateIndex.UPGRADE_STATE);
                break;
        }
    }
}
