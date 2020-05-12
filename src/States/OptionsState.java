package States;

import Assets.Images.BackgroundAssets;
import GUI.GUIButton;
import GameSystems.OptionsSystem.DifficultyOption;
import GameSystems.OptionsSystem.Option;
import GameSystems.OptionsSystem.WindowModeOption;

import java.awt.*;
import java.util.ArrayList;

/**
 *  @brief Implements the options of the game.
 */
public class OptionsState extends ReversibleState {
    private final ArrayList<Option> allOptions;///< Holds all options.

    /**
     * Constructor without parameters.
     */
    public OptionsState() {
        // Back button
        allButtons.get(0).AddActionListener(actionEvent -> StateManager.GetInstance().SetCurrentState(StateIndex.MENU_STATE));
        allOptions = new ArrayList<>(2);
        // Add difficulty button
        DifficultyOption difficultyOption = new DifficultyOption();
        allButtons.add(difficultyOption.GetButtonHandle());
        allOptions.add(difficultyOption);
        // Add window mode option
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
        for (Option option : allOptions) {
            option.Draw(g2d);
        }
    }
}
