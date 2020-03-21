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

public class StatsState extends ReversableState {
    public StatsState(){
        allButtons.get(0).AddActionListener(actionEvent -> StateManager.GetInstance().SetCurrentState(StateManager.StateIndex.MENU_STATE));
    }

    @Override
    public void Draw(GameWindow wnd) {
        BufferStrategy bs = wnd.GetCanvas().getBufferStrategy();
        Graphics g = bs.getDrawGraphics();
        g.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());
        g.drawImage(BackgroundAssets.bg_game_dark, 0, 0, null);
        for(GUIButton b:allButtons){
            b.Draw(g);
        }
        bs.show();
        g.dispose();
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode()==KeyEvent.VK_ESCAPE)
            StateManager.GetInstance().SetCurrentState(StateManager.StateIndex.MENU_STATE);
    }
}
