package Game;

import javax.swing.*;
import java.awt.*;

public class GameWindow {
    //windowed mode scaling factor
    public static final double Y_SCALE_FACTOR = 0.95;

    public boolean isFullScreen = true;

    private JFrame wndFrame;
    private final String wndTitle;
    private final int wndWidth;
    private final int wndHeight;
    private Canvas canvas;

    public static final Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();

    public GameWindow(String title) {
        wndTitle = title;
        wndWidth = screenDimension.width;
        wndHeight = screenDimension.height;
        wndFrame = null;
    }

    public void BuildGameWindow() {
        if (wndFrame != null) {
            return;
        }
        wndFrame = new JFrame(wndTitle);
        wndFrame.setSize(wndWidth, wndHeight);
        wndFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        wndFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        wndFrame.setResizable(true);
        wndFrame.setUndecorated(isFullScreen);
        wndFrame.setLocationRelativeTo(null);
        wndFrame.setVisible(true);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(wndWidth, wndHeight));
        wndFrame.add(canvas);
        wndFrame.pack();

        GlobalReferences.gameWindow = this;
    }

    public int GetWndWidth() {
        return wndWidth;
    }
    public int GetWndHeight() {
        return wndHeight;
    }
    public Canvas GetCanvas() {
        return canvas;
    }
    public JFrame GetFrame() {return wndFrame;}
}
