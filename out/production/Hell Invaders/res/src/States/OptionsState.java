package States;

import GUI.GUIButton;
import Assets.Images.BackgroundAssets;
import GameSystems.OptionsSystem.DifficultyOption;
import GameSystems.OptionsSystem.Option;
import GameSystems.OptionsSystem.WindowModeOption;

import java.awt.*;
import java.util.ArrayList;

public class OptionsState extends ReversibleState {
    private final ArrayList<Option> allOptions;

    public OptionsState() {

        //back button
        allButtons.get(0).AddActionListener(actionEvent -> StateManager.GetInstance().SetCurrentState(StateManager.StateIndex.MENU_STATE));
        allOptions = new ArrayList<>(2);
        //add difficulty button
        DifficultyOption difficultyOption=new DifficultyOption();
        allButtons.add(difficultyOption.GetButtonHandle());
        allOptions.add(difficultyOption);
        //add window mode option
        WindowModeOption windowModeOption = new WindowModeOption();
        allButtons.add(windowModeOption.GetButtonHandle());
        allOptions.add(windowModeOption);
    }

    @Override
    public void Draw(Graphics2D g2d) {
        g2d.drawImage(BackgroundAssets.bgGameDark, 0, 0, null);
        for (GUIButton b : allButtons) {
            b.Draw(g2d);
        }
        for(Option option: allOptions){
            option.Draw(g2d);
        }
    }
}
