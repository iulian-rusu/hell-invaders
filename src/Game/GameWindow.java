package Game;

import SQL.DatabaseManager;

import javax.swing.*;
import java.awt.*;

/**
 *  @brief Container for the main window of the game.
 */
public class GameWindow {
    public static final double Y_SCALE_FACTOR = 0.95;///< Scaling factor to transition between bigger and smaller windows.
    public static final Dimension SCREEN_DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();///< The dimension of the screen.

    public boolean isFullscreen;///< Flag that indicates if the game window will be displayed din fullscreen mode.

    private JFrame wndFrame;///< The JFrame object associated with the window.
    private final String wndTitle;///< The title of the window.
    private final int wndWidth;///< The width of the window.
    private final int wndHeight;///< The height of the window.
    private Canvas canvas;///< The Canvas object associated with the window.

    /**
     * Constructor with parameters.
     *
     * @param title The title of the window.
     */
    public GameWindow(String title) {
        wndTitle = title;
        wndWidth = SCREEN_DIMENSION.width;
        wndHeight = SCREEN_DIMENSION.height;
        wndFrame = null;

        isFullscreen = DatabaseManager.GetGameData("IsFullscreen") == 1;
    }

    /**
     * Constructs the game window.
     */
    public void BuildGameWindow() {
        if (wndFrame != null) {
            return;
        }
        wndFrame = new JFrame(wndTitle);
        wndFrame.setSize(wndWidth, wndHeight);
        wndFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        wndFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        wndFrame.setResizable(true);
        wndFrame.setUndecorated(isFullscreen);
        wndFrame.setLocationRelativeTo(null);
        wndFrame.setVisible(true);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(wndWidth, wndHeight));
        wndFrame.add(canvas);
        wndFrame.pack();

        GlobalReferences.SetGameWindow(this);
    }

    /**
     * Returns the width of the game window.
     *
     * @return The width of the game window.
     */
    public int GetWndWidth() {
        return wndWidth;
    }

    /**
     * Returns the height of the game window.
     *
     * @return The height of the game window.
     */
    public int GetWndHeight() {
        return wndHeight;
    }

    /**
     * Returns the Canvas object.
     *
     * @return A Canvas object.
     */
    public Canvas GetCanvas() {
        return canvas;
    }

    /**
     * Returns the JFrame object.
     *
     * @return A JFrame object.
     */
    public JFrame GetFrame() {return wndFrame;}
}
