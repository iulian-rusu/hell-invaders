package States;

import Game.GameWindow.GameWindow;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class State {
    protected int frameCount = -1;

    public abstract void Init();

    public void Update() {
        ++frameCount;
        if (frameCount >= 60)
            frameCount = 0;
    }

    public void keyPressed(KeyEvent keyEvent){}
    public abstract void Draw(GameWindow wnd);
    public abstract void mousePressed(MouseEvent mouseEvent);
    public abstract void mouseMoved(MouseEvent mouseEvent);
}
