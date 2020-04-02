package States;

import Assets.GUIAssets;
import GUI.GUIButton;
import GUI.GUIText;
import GUI.GUITextPanel;
import Game.GameWindow;
import Assets.BackgroundAssets;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class OptionsState extends ReversibleState {

    public OptionsState() {
        allButtons.get(0).AddActionListener(actionEvent -> StateManager.GetInstance().SetCurrentState(StateManager.StateIndex.MENU_STATE));
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
