package States;

import GUI.GUIButton;
import GameSystems.EventSystem.Observable;

import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

/**
 *  @brief Class that implements the game state mechanic.
 */
public abstract class State extends Observable {
    protected ArrayList<GUIButton> allButtons;///< Holds all the buttons of a state.
    protected int frameCount;///< Counts frames in each Update() call.
    protected int secondCount;///< Counts seconds, increments every 60 frames.

    /**
     * Called each time a state becomes the current game state.
     */
    public void Init() {
        frameCount = -1;
        secondCount = 0;
        if (allButtons != null) {
            for (GUIButton b : allButtons) {
                b.Init();
            }
        }
    }

    /**
     * Called each frame.
     */
    public void Update() {
        ++frameCount;
        if (frameCount >= 60) {
            frameCount = 0;
            ++secondCount;
        }
    }

    /**
     * Called when a mouse press event occurs.
     *
     * @param pressPoint The point where the mouse pressed.
     */
    public void MousePressed(Point pressPoint) {
        if (allButtons != null) {
            for (GUIButton b : allButtons) {
                b.MousePressed(pressPoint);
            }
        }
    }

    /**
     * Called when a mouse movement event occurs.
     *
     * @param movePoint The current location of the cursor.
     */
    public void MouseMoved(Point movePoint) {
        if (allButtons != null) {
            for (GUIButton b : allButtons) {
                b.MouseMoved(movePoint);
            }
        }
    }

    /**
     * Called when a mouse scroll event occurs.
     *
     * @param mouseWheelEvent A Java MouseWheelEvent that was recorded.
     */
    public void MouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
        // Does nothing
    }

    /**
     * Called each frame to draw elements to the screen.
     *
     * @param g2d A java Graphics2D object.
     */
    public abstract void Draw(Graphics2D g2d);
}
