package States;

import GUI.GUIButton;
import Game.GameWindow;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public abstract class State {
    protected ArrayList<GUIButton> allButtons;
    protected int frameCount;
    protected int secondCount;
    protected static final int buttonW = 200;
    protected static final int buttonH = 55;

    public void Init() {
        frameCount = -1;
        secondCount = 0;
        if(allButtons!=null) {
            for (GUIButton b : allButtons) {
                b.Init();
            }
        }
    }

    public void Update() {
        ++frameCount;
        if (frameCount >= 60) {
            frameCount = 0;
            ++secondCount;
        }
    }

    public void keyPressed(KeyEvent keyEvent) {
    }

    public void mousePressed(MouseEvent mouseEvent) {
        if(allButtons!=null) {
            for (GUIButton b : allButtons) {
                b.mousePressed(mouseEvent);
            }
        }
    }

    public void mouseMoved(MouseEvent mouseEvent) {
        if(allButtons!=null) {
            for (GUIButton b : allButtons) {
                b.mouseMoved(mouseEvent);
            }
        }
    }

    public abstract void Draw(GameWindow wnd);
}
