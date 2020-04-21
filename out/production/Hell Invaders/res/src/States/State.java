package States;

import GameSystems.EventSystem.Observable;
import GUI.GUIButton;
import Game.GameWindow;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

public abstract class State extends Observable {
    protected ArrayList<GUIButton> allButtons;
    protected int frameCount;
    protected int secondCount;

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

    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent){

    }

    public abstract void Draw(GameWindow wnd);
}
