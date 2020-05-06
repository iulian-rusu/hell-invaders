package States;

import GameSystems.EventSystem.Observable;
import GUI.GUIButton;

import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

/*! \class State
    \brief Abstract class that implements the game state mechanic.
*/
public abstract class State extends Observable {
    protected ArrayList<GUIButton> allButtons;///< Holds all the buttons of a state.
    protected int frameCount;///< Counts frames in each Update() call.
    protected int secondCount;///< Counts seconds, increments every 60 frames.

    /*! \fn public void Init()
        \brief Called each time a state becomes the current game state.
    */
    public void Init() {
        frameCount = -1;
        secondCount = 0;
        if(allButtons!=null) {
            for (GUIButton b : allButtons) {
                b.Init();
            }
        }
    }

    /*! \fn public void Update()
        \brief Called each frame.
    */
    public void Update() {
        ++frameCount;
        if (frameCount >= 60) {
            frameCount = 0;
            ++secondCount;
        }
    }

    /*! \fn public void MousePressed(Point pressPoint)
        \brief Called when a mouse press event occurs.
        \param pressPoint The point where the mouse pressed.
    */
    public void MousePressed(Point pressPoint) {
        if(allButtons!=null) {
            for (GUIButton b : allButtons) {
                b.MousePressed(pressPoint);
            }
        }
    }

    /*! \fn public void MouseMoved(Point movePoint)
        \brief Called when a mouse movement event occurs.
        \param movePoint The current location of the cursor.
    */
    public void MouseMoved(Point movePoint) {
        if(allButtons!=null) {
            for (GUIButton b : allButtons) {
                b.MouseMoved(movePoint);
            }
        }
    }

    /*! \fn public void MouseWheelMoved(MouseWheelEvent mouseWheelEvent)
        \brief Called when a mouse scroll event occurs.
        \param mouseWheenEvent A Java MouseWheelEvent that was recorded.
    */
    public void MouseWheelMoved(MouseWheelEvent mouseWheelEvent){

    }
    /*! \fn public abstract void Draw(Graphics2D g2d);
           \brief Called when a mouse scroll event occurs.
           \param g2d A java Graphics2D object.
       */
    public abstract void Draw(Graphics2D g2d);
}
