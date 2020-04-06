package Game;

import javax.swing.*;
import java.awt.*;

public class GameWindow {

    private JFrame wndFrame;
    private String wndTitle;
    private int wndWidth;
    private int wndHeight;
    private Canvas canvas;

    public static final  Dimension wndDimension=Toolkit.getDefaultToolkit().getScreenSize();

    public GameWindow(String title) {
        wndTitle = title;
        wndWidth = wndDimension.width;
        wndHeight = wndDimension.height;
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
        wndFrame.setUndecorated(true);
        wndFrame.setLocationRelativeTo(null);
        wndFrame.setVisible(true);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(wndWidth, wndHeight));
        wndFrame.add(canvas);
        wndFrame.pack();
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
}
