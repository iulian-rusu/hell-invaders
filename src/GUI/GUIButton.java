package GUI;

import Assets.Audio.AudioManager;
import GameSystems.EventSystem.Events.AudioEvent;
import GameSystems.EventSystem.Observable;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *  @brief Provides an API for an interactive button object.
 */
public class GUIButton extends Observable {
    public static final int BUTTON_W = 230;///< The default width of the button.
    public static final int BUTTON_H = 60;///< The default height of the button.

    private GUIButtonState currentState;///< The current state of the button. Can be released, hovered, pressed, blocked.
    private final Rectangle clickBox;///< The Rectangle object that defines the bounds of the button.
    private final ArrayList<ActionListener> actionListeners;///< A list of ActionListener objects to add functionality to the button.
    private BufferedImage imageReleased;///< The texutre of the released button.
    private BufferedImage imageHovered;///< The texture of the hovered button.

    /**
     * Constructor with parameters.
     *
     * @param released The image of the released button.
     * @param hovered  The image of the hovered button.
     * @param x        The x coordinate of the top-left corner of the button.
     * @param y        The y coordiate of the top-left corner of the button.
     * @param width    The width of thee button.
     * @param height   The height of the button.
     */
    public GUIButton(BufferedImage released, BufferedImage hovered, int x, int y, int width, int height) {
        imageReleased = released;
        imageHovered = hovered;
        clickBox = new Rectangle(x, y, width, height);
        actionListeners = new ArrayList<>();
        currentState = GUIButtonState.RELEASED;
        // Observer of audio events
        AddObserver(AudioManager.GetInstance());
    }

    /**
     * If the button is not blocked, sets the current state to "released".
     */
    public void Init() {
        if (currentState != GUIButtonState.BLOCKED) {
            currentState = GUIButtonState.RELEASED;
        }
    }

    /**
     * Draws the button on the screen.
     *
     * @param g A java Graphics object.
     */
    public void Draw(Graphics g) {
        switch (currentState) {
            case RELEASED:
            case BLOCKED:
                g.drawImage(imageReleased, clickBox.x, clickBox.y, clickBox.width, clickBox.height, null);
                break;
            case HOVERED:
            case PRESSED:
                g.drawImage(imageHovered, clickBox.x, clickBox.y, clickBox.width, clickBox.height, null);
                break;
        }
    }

    /**
     * Provides an API to add ActionListener objects to the button.
     *
     * @param a A java ActionListener object.
     */
    public void AddActionListener(ActionListener a) {
        actionListeners.add(a);
    }

    /**
     * Called by the current game state when the mouse is moved. Used to detect hovering.
     *
     * @param movePoint A java Point object representing the current position of the mouse.
     */
    public void MouseMoved(Point movePoint) {
        if (currentState == GUIButtonState.BLOCKED) {
            return;
        }
        if (currentState == GUIButtonState.RELEASED && clickBox.contains(movePoint)) {
            currentState = GUIButtonState.HOVERED;
        } else if (!clickBox.contains(movePoint)) {
            currentState = GUIButtonState.RELEASED;
        }
    }

    /**
     * Called by the current game state when the mouse is pressed.
     *
     * @param pressPoint A java Point object representing the current position of the mouse.
     */
    public void MousePressed(Point pressPoint) {
        if (currentState == GUIButtonState.BLOCKED) {
            return;
        }
        if (clickBox.contains(pressPoint)) {
            currentState = GUIButtonState.PRESSED;
            NotifyAllObservers(AudioEvent.PLAY_BUTTON_PRESS);
            for (ActionListener a : actionListeners)
                a.actionPerformed(null);
        }
    }

    /**
     * Moves the current position of the mouse in the specified direction.
     *
     * @param dx The increase in the x coordinate.
     * @param dy The increase in the y coordinate.
     */
    public void Translate(int dx, int dy) {
        this.clickBox.x += dx;
        this.clickBox.y += dy;
    }

    /**
     * Sets the current position of the button.
     *
     * @param x The new x coordinate.
     * @param y The new y coordinate.
     */
    public void SetPosition(int x, int y) {
        this.clickBox.setLocation(x, y);
    }

    /**
     * Sets new button images.
     *
     * @param released The new image of the released button.
     * @param hovered  The new image of the hovered button.
     */
    public void SetImages(BufferedImage released, BufferedImage hovered) {
        this.imageReleased = released;
        this.imageHovered = hovered;
    }

    /**
     * Sets the current state of the button to "blocked".
     *
     * @param blockedIcon The image of the blocked button.
     */
    public void Block(BufferedImage blockedIcon) {
        if (currentState != GUIButtonState.BLOCKED) {
            currentState = GUIButtonState.BLOCKED;
            this.imageReleased = blockedIcon;
        }
    }

    /**
     * Sets the current state of the button to "released".
     *
     * @param unblockedIcon The image of the released button.
     */
    public void Unblock(BufferedImage unblockedIcon) {
        if (currentState == GUIButtonState.BLOCKED) {
            currentState = GUIButtonState.RELEASED;
            this.imageReleased = unblockedIcon;
        }
    }

    /**
     * Holds all possible button states.
     *
     * @see java.lang.Enum
     */
    private enum GUIButtonState {
        RELEASED, HOVERED, PRESSED, BLOCKED
    }
}
