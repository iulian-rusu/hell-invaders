package States;

import Assets.Images.BackgroundAssets;
import Assets.Images.GUIAssets;
import GUI.GUIButton;
import GUI.Text.GUIText;
import Game.GameWindow;
import Game.GlobalReferences;
import GameSystems.EventSystem.Events.AudioEvent;
import SQL.DatabaseManager;

import java.awt.*;

/**
 *  @brief Intermediate state that happens when the player wants to start a new game.
 */
public class NewGameQueryState extends ReversibleState {
    public static final int QUERY_Y = 400;///< The y coordinate of the question.
    public static final int QUERY_X = 270;///< The x coordinate of the question
    public static final int OPTIONS_Y = QUERY_Y + 50; ///< The y coordinate of the possible options - start a new game or go back.
    public static final int OPTION_X_OFFSET = 25;///< The x offset between the two options.
    public static final int BACK_OPTION_X =
            GameWindow.SCREEN_DIMENSION.width / 2 - GUIButton.BUTTON_W - OPTION_X_OFFSET;///< The x coordiante of the "Back" option
    public static final int NEW_GAME_OPTION_X =
            GameWindow.SCREEN_DIMENSION.width / 2 + OPTION_X_OFFSET;///< THe x coordinate of the "New Game" option.

    private final GUIText newGameQuery;///< Text object that draws a message to the screen.

    /**
     * Constructor without parameters.
     */
    public NewGameQueryState() {
        super();
        // Go back to menu option
        this.allButtons.get(0).SetPosition(BACK_OPTION_X, OPTIONS_Y);
        this.allButtons.get(0).AddActionListener(actionEvent -> StateManager.GetInstance().SetCurrentState(StateIndex.MENU_STATE));
        // New game option
        GUIButton newGameButton = new GUIButton(GUIAssets.new_game_button, GUIAssets.new_game_button_hovered, NEW_GAME_OPTION_X, OPTIONS_Y,
                GUIButton.BUTTON_W, GUIButton.BUTTON_H);
        newGameButton.AddActionListener(actionEvent -> {
            NotifyAllObservers(AudioEvent.STOP_CURRENT_STATE_MUSIC);
            // Rewrite database
            DatabaseManager.ClearPlayerData();
            GlobalReferences.GetPlayer().ResetAllStats();
            DatabaseManager.SavePlayerData();
            // Go to upgrades
            StateManager.GetInstance().SetCurrentState(StateIndex.UPGRADE_STATE);
        });
        allButtons.add(newGameButton);
        newGameQuery = new GUIText("ARE YOU SURE YOU WANT TO START A NEW GAME?", QUERY_X, QUERY_Y, 75);
    }

    @Override
    public void Draw(Graphics2D g2d) {
        g2d.drawImage(BackgroundAssets.bgGameDark, 0, 0, null);
        for (GUIButton b : allButtons)
            b.Draw(g2d);
        newGameQuery.Draw(g2d);
    }
}
