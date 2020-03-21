package States;

import Assets.PlayerAssets;
import Assets.ProjectileAssets;
import Audio.AudioManager;
import Audio.BackgroundMusic;
import GUI.GUIButton;
import Game.GameWindow;
import Assets.BackgroundAssets;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

public class GameState extends ReversableState {

    public GameState() {
        allButtons.get(0).AddActionListener(actionEvent -> StateManager.GetInstance().SetCurrentState(StateManager.StateIndex.UPGRADE_STATE));
        allButtons.get(0).AddActionListener(actionEvent -> AudioManager.GetInstance().Stop(BackgroundMusic.gameMusic));
    }

    @Override
    public void Init() {
        super.Init();
        AudioManager.GetInstance().Play(BackgroundMusic.gameMusic);
    }

    @Override
    public void Draw(GameWindow wnd) {
        BufferStrategy bs = wnd.GetCanvas().getBufferStrategy();
        Graphics g = bs.getDrawGraphics();
        g.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());
        g.drawImage(BackgroundAssets.bg_game, 0, 0, null);
        for (GUIButton b : allButtons) {
            b.Draw(g);
        }
        g.drawImage(PlayerAssets.player_frames[(frameCount / 8) % 4], PlayerAssets.playerX, PlayerAssets.playerY, null);
        bs.show();
        g.dispose();
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
            AudioManager.GetInstance().Stop(BackgroundMusic.gameMusic);
            StateManager.GetInstance().SetCurrentState(StateManager.StateIndex.UPGRADE_STATE);
        }
    }
}
