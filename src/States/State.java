package States;

import Game.GameWindow;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class State {
    protected int frameCount;
    protected int secondCount;

    public void Init(){
        frameCount=-1;
        secondCount=0;
    }

    public void Update() {
        ++frameCount;
        if (frameCount >= 60) {
            frameCount = 0;
            ++secondCount;
        }
    }

    public void keyPressed(KeyEvent keyEvent){}
    public abstract void Draw(GameWindow wnd);
    public abstract void mousePressed(MouseEvent mouseEvent);
    public abstract void mouseMoved(MouseEvent mouseEvent);
}
