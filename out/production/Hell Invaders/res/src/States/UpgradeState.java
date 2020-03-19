package States;

import GUI.GUIButton;
import Game.GameWindow.GameWindow;
import Game.Graphics.Backgrounds;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;

public class UpgradeState extends State {

    @Override
    public void Init() {

    }

    @Override
    public void Update() {
        super.Update();
    }

    @Override
    public void Draw(GameWindow wnd) {
        BufferStrategy bs = wnd.GetCanvas().getBufferStrategy();
        Graphics g = bs.getDrawGraphics();
        g.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());
        g.drawImage(Backgrounds.bg_game_dark, 0, 0, null);
        bs.show();
        g.dispose();
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode()==KeyEvent.VK_ESCAPE)
            StateManager.GetInstance().SetCurrentState(StateManager.StateIndex.MENU_STATE);
    }
}
