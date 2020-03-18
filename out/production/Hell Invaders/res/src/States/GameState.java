package States;

import Game.GameWindow.GameWindow;
import Game.Graphics.Assets;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;

public class GameState extends State {

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
        if (frameCount == 0) {
            g.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());
            g.drawImage(Assets.bg_game.getScaledInstance(wnd.GetWndWidth(), wnd.GetWndHeight(), Image.SCALE_SMOOTH),
                    0, 0, null);
        }
        bs.show();
        g.dispose();

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }
}
