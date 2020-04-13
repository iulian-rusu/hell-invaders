package States;

import GUI.GUIButton;
import Game.GameWindow;
import Assets.Images.BackgroundAssets;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class StatsState extends ReversibleState {

    public StatsState(){
        //back button
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
}
