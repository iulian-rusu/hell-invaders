package States;

import GUI.GUIButton;
import Game.GameWindow;
import Assets.Images.BackgroundAssets;
import GameSystems.OptionsSystem.DifficultyOption;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class OptionsState extends ReversibleState {
    private final DifficultyOption difficultyOption;

    public OptionsState() {
        //back button
        allButtons.get(0).AddActionListener(actionEvent -> StateManager.GetInstance().SetCurrentState(StateManager.StateIndex.MENU_STATE));
        //add difficulty button
        difficultyOption=new DifficultyOption();
        allButtons.add(difficultyOption.GetButtonHandle());
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
        difficultyOption.Draw(g);
        bs.show();
        g.dispose();
    }
}
