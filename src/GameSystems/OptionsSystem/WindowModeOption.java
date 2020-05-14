package GameSystems.OptionsSystem;

import Assets.Images.GUIAssets;
import GUI.GUIButton;
import GUI.Text.GUIText;
import Game.GameWindow;
import Game.GlobalReferences;
import SQL.DatabaseManager;
import States.AboutState;

import javax.swing.*;
import java.awt.*;

/**
 * @brief Adds the option to switch between fullscreen and windowed mode.
 */
public class WindowModeOption implements Option {
    public static final int WINDOW_MODE_TEXT_X =
            (GameWindow.SCREEN_DIMENSION.width - GUIButton.BUTTON_W) / 2 - 160;///< The x coordinate of the description's bottom-left corner.
    public static final int WINDOW_MODE_TEXT_Y =
            GameWindow.SCREEN_DIMENSION.height - 50;///< The y coordinate of the description's bottom-left corner.
    public static final int WINDOW_MODE_BUTTON_X =
            WINDOW_MODE_TEXT_X + 370;///< The x coordinate of the top-left corner of the button.
    public static final int WINDOW_MODE_BUTTON_Y =
            GameWindow.SCREEN_DIMENSION.height - 100;///< The y coordinate of the top-left corner of the button.

    private final GUIButton windowModeButton;///< The button that switches between window modes.
    private final GUIText windowModeText;///< The text that describes the option.

    /**
     * Constructor without parameters.
     */
    public WindowModeOption() {
        windowModeButton = new GUIButton(GUIAssets.full_screen_button, GUIAssets.full_screen_button_hovered, WINDOW_MODE_BUTTON_X, WINDOW_MODE_BUTTON_Y,
                GUIButton.BUTTON_W, GUIButton.BUTTON_H);
        windowModeButton.AddActionListener(actionEvent -> ToggleWindowMode());
        windowModeText = new GUIText("WINDOW MODE: ", WINDOW_MODE_TEXT_X, WINDOW_MODE_TEXT_Y,
                DifficultyOption.BIGGER_INFO_FONT_SIZE, AboutState.TEXT_COLOR);

        UpdateButtonImages();
    }

    /**
     * Checks the current state of the game window and sets the corresponding image for the button.
     */
    private void UpdateButtonImages() {
        boolean isFullscreen = GlobalReferences.GetGameWindow().GetFrame().isUndecorated();
        if (isFullscreen) {
            windowModeButton.SetImages(GUIAssets.full_screen_button, GUIAssets.full_screen_button_hovered);
        } else {
            windowModeButton.SetImages(GUIAssets.windowed_button, GUIAssets.windowed_button_hovered);
        }
    }

    /**
     * Switches between fullscrenn and windowed mode.
     */
    private void ToggleWindowMode() {
        GameWindow gameWindow = GlobalReferences.GetGameWindow();
        JFrame frame = gameWindow.GetFrame();

        frame.dispose();
        frame.setUndecorated(!gameWindow.isFullscreen);
        gameWindow.isFullscreen = !gameWindow.isFullscreen;
        frame.setVisible(true);

        DatabaseManager.SaveGameData("IsFullscreen", gameWindow.isFullscreen ? 1 : 0);
        UpdateButtonImages();
    }

    @Override
    public GUIButton GetButtonHandle() {
        return windowModeButton;
    }

    @Override
    public void Draw(Graphics g) {
        windowModeButton.Draw(g);
        windowModeText.Draw(g);
    }
}