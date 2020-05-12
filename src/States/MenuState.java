package States;

import Assets.Images.BackgroundAssets;
import Assets.Images.GUIAssets;
import GUI.GUIButton;
import Game.GameWindow;
import GameSystems.EventSystem.Events.AudioEvent;
import SQL.DatabaseManager;

import java.awt.*;
import java.util.ArrayList;

/**
 *  @brief Implements the main menu of the game.
 */
public class MenuState extends State {
    private boolean logoColorflag = true;///< Flag to signal the color of the game logo. It alternates every second.

    /**
     * Constructor without parameters.
     */
    public MenuState() {
        final int menuY = GameWindow.SCREEN_DIMENSION.height / 2 - 10;
        final int menuX = (GameWindow.SCREEN_DIMENSION.width - GUIButton.BUTTON_W) / 2;
        final int buttonSpacing = 75;

        allButtons = new ArrayList<>(5);
        allButtons.add(new GUIButton(GUIAssets.new_game_button, GUIAssets.new_game_button_hovered,
                menuX, menuY, GUIButton.BUTTON_W, GUIButton.BUTTON_H));
        allButtons.add(new GUIButton(GUIAssets.resume_button, GUIAssets.resume_button_hovered,
                menuX, menuY + buttonSpacing, GUIButton.BUTTON_W, GUIButton.BUTTON_H));
        allButtons.add(new GUIButton(GUIAssets.options_button, GUIAssets.options_button_hovered,
                menuX, menuY + 2 * buttonSpacing, GUIButton.BUTTON_W, GUIButton.BUTTON_H));
        allButtons.add(new GUIButton(GUIAssets.about_button, GUIAssets.about_button_hovered,
                menuX, menuY + 3 * buttonSpacing, GUIButton.BUTTON_W, GUIButton.BUTTON_H));
        allButtons.add(new GUIButton(GUIAssets.quit_button, GUIAssets.quit_button_hovered,
                menuX, menuY + 4 * buttonSpacing, GUIButton.BUTTON_W, GUIButton.BUTTON_H));
        // State transition events
        // New game button
        allButtons.get(0).AddActionListener(actionEvent -> {
                    if(DatabaseManager.IsEmpty(DatabaseManager.PLAYER_DATA_NAME)){
                        // Nothing saved, go directly to a new game.
                        NotifyAllObservers(AudioEvent.STOP_CURRENT_STATE_MUSIC);
                        StateManager.GetInstance().SetCurrentState(StateIndex.UPGRADE_STATE);
                    } else {
                        // Saved data exists in the database, ask the player again.
                        StateManager.GetInstance().SetCurrentState(StateIndex.NEW_GAME_QUERY_STATE);
                    }
                }
        );
        // Resume button
        allButtons.get(1).AddActionListener(actionEvent -> {
            NotifyAllObservers(AudioEvent.STOP_CURRENT_STATE_MUSIC);
            // This goes to UpgradeState without clearing player data
            StateManager.GetInstance().SetCurrentState(StateIndex.UPGRADE_STATE);
        });
        // Options button
        allButtons.get(2).AddActionListener(actionEvent -> StateManager.GetInstance().SetCurrentState(StateIndex.OPTIONS_STATE));
        // About button
        allButtons.get(3).AddActionListener(actionEvent -> StateManager.GetInstance().SetCurrentState(StateIndex.ABOUT_STATE));
        // Quit button
        allButtons.get(4).AddActionListener(actionEvent -> {
            NotifyAllObservers(AudioEvent.STOP_CURRENT_STATE_MUSIC);
            DatabaseManager.SavePlayerData();
            System.exit(0);
        });
    }

    @Override
    public void Init() {
        super.Init();
        NotifyAllObservers(AudioEvent.PLAY_CURRENT_STATE_MUSIC);
        logoColorflag = true;
        if (DatabaseManager.IsEmpty(DatabaseManager.PLAYER_DATA_NAME))
            allButtons.get(1).Block(GUIAssets.resume_button_blocked);
        else
            allButtons.get(1).Unblock(GUIAssets.resume_button);
    }

    @Override
    public void Draw(Graphics2D g2d) {
        if (frameCount == 59) {
            logoColorflag = !logoColorflag;
        }
        if (logoColorflag) {
            g2d.drawImage(BackgroundAssets.bgMenuFirst, 0, 0, null);
        } else {
            g2d.drawImage(BackgroundAssets.bgMenuSecond, 0, 0, null);
        }
        for (GUIButton b : allButtons)
            b.Draw(g2d);
    }
}
