package Game.GameWindow;

import javax.swing.*;
import java.awt.*;

public class GameWindow {
    private JFrame wndFrame;
    private String wndTitle;
    private int wndWidth;
    private int wndHeight;
    private Canvas canvas;

    public GameWindow(String title) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        wndTitle = title;
        wndWidth = ((int) tk.getScreenSize().getWidth());
        wndHeight = ((int) tk.getScreenSize().getHeight());
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
       // wndFrame.setUndecorated(true);
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
    public JFrame GetFrame(){
        return wndFrame;
    }
}
