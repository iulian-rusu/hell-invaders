package States;

import GUI.GUIButton;
import Game.GameWindow.GameWindow;
import Game.Graphics.Assets;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;

public class MenuState extends State {

    private boolean logoColorflag = true;
    private GUIButton[] allButtons;

    private final int buttonW=200;
    private final int buttonH=55;
    private final int menuY=380;
    private final int menuX=(Toolkit.getDefaultToolkit().getScreenSize().width-buttonW)/2;
    private final int buttonSpacing=65;

    public MenuState() {

        allButtons = new GUIButton[]{
                new GUIButton(Assets.new_game_button, Assets.new_game_button_hovered, menuX, menuY, buttonW, buttonH),
                new GUIButton(Assets.resume_button_blocked, Assets.resume_button_hovered, menuX, menuY+buttonSpacing, buttonW, buttonH),
                new GUIButton(Assets.options_button, Assets.options_button_hovered, menuX, menuY+2*buttonSpacing, buttonW, buttonH),
                new GUIButton(Assets.stats_button, Assets.stats_button_hovered, menuX, menuY+3*buttonSpacing, buttonW, buttonH),
                new GUIButton(Assets.quit_button, Assets.quit_button_hovered, menuX, menuY+4*buttonSpacing, buttonW, buttonH)};

        allButtons[1].Block();//resume blocked initially

        for (GUIButton b : allButtons) {
            b.AddActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {

                }
            });
        }
    }

    @Override
    public void Init() {
        frameCount = 0;
    }

    @Override
    public void Update() {
        super.Update();
        for (GUIButton b : allButtons)
            b.Update();
    }

    @Override
    public void Draw(GameWindow wnd) {
        BufferStrategy bs = wnd.GetCanvas().getBufferStrategy();
        Graphics g = bs.getDrawGraphics();
        if (frameCount == 2) {
            if (logoColorflag) {
                g.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());
                g.drawImage(Assets.bg_menu0.getScaledInstance(wnd.GetWndWidth(), wnd.GetWndHeight(), Image.SCALE_SMOOTH),
                        0, 0, null);
            } else {
                g.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());
                g.drawImage(Assets.bg_menu1.getScaledInstance(wnd.GetWndWidth(), wnd.GetWndHeight(), Image.SCALE_SMOOTH),
                        0, 0, null);
            }
            logoColorflag = !logoColorflag;
        }
        for (GUIButton b : allButtons)
            b.Draw(g);
        bs.show();
        g.dispose();
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        for (GUIButton b : allButtons)
            b.mouseMoved(mouseEvent);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        for (GUIButton b : allButtons)
            b.mousePressed(mouseEvent);
    }

}
