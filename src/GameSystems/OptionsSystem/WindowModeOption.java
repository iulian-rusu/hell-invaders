package GameSystems.OptionsSystem;

import Assets.Images.GUIAssets;
import GUI.GUIButton;
import GUI.Text.GUIText;
import Game.GameWindow;
import Game.GlobalReferences;
import States.AboutState;

import javax.swing.*;
import java.awt.*;

public class WindowModeOption implements Option {
    //text parameters
    public static final int WINDOW_MODE_TEXT_X = (GameWindow.screenDimension.width - GUIButton.BUTTON_W) / 2 - 130;
    public static final int WINDOW_MODE_TEXT_Y = GameWindow.screenDimension.height - 50;
    //button parameters
    public static final int WINDOW_MODE_BUTTON_X = WINDOW_MODE_TEXT_X + 300;
    public static final int WINDOW_MODE_BUTTON_Y = GameWindow.screenDimension.height - 100;

    private final GUIButton windowModeButton;
    private final GUIText windowModeText;

    public WindowModeOption() {
        windowModeButton = new GUIButton(GUIAssets.full_screen_button, GUIAssets.full_screen_button_hovered, WINDOW_MODE_BUTTON_X, WINDOW_MODE_BUTTON_Y,
                GUIButton.BUTTON_W, GUIButton.BUTTON_H);
        windowModeButton.AddActionListener(actionEvent -> ToggleWindowMode());
        windowModeText = new GUIText("WINDOW MODE: ", WINDOW_MODE_TEXT_X, WINDOW_MODE_TEXT_Y,
                AboutState.DESCRIPTION_FONT_SIZE, AboutState.TEXT_COLOR);

        UpdateButtonImages();
    }

    private void UpdateButtonImages() {
        boolean isFullscreen = GlobalReferences.gameWindow.GetFrame().isUndecorated();
        if (isFullscreen) {
            windowModeButton.SetImages(GUIAssets.full_screen_button, GUIAssets.full_screen_button_hovered);
        } else {
            windowModeButton.SetImages(GUIAssets.windowed_button, GUIAssets.windowed_button_hovered);
        }
    }

    private void ToggleWindowMode() {
        JFrame frame = GlobalReferences.gameWindow.GetFrame();

        frame.dispose();
        frame.setUndecorated(!GlobalReferences.gameWindow.isFullScreen);
        GlobalReferences.gameWindow.isFullScreen = !GlobalReferences.gameWindow.isFullScreen;
        frame.setVisible(true);

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