package GUI;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GUIButton {

    private GUIButtonState currentState;
    private final Rectangle clickBox;
    private final ArrayList<ActionListener> actionListeners;
    private BufferedImage imageReleased;
    private final BufferedImage imageHovered;

    public GUIButton(BufferedImage released, BufferedImage hovered, int x, int y, int width, int height) {
        imageReleased = released;
        imageHovered = hovered;
        clickBox = new Rectangle(x, y, width, height);
        actionListeners = new ArrayList<>();
        currentState = GUIButtonState.RELEASED;
    }

    public void Init() {
        currentState = GUIButtonState.RELEASED;
    }

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

    public void AddActionListener(ActionListener a) {
        actionListeners.add(a);
    }

    public void mouseMoved(MouseEvent e) {
        if (currentState == GUIButtonState.BLOCKED) {
            return;
        }
        if (currentState == GUIButtonState.RELEASED && clickBox.contains(e.getPoint())) {
            currentState = GUIButtonState.HOVERED;
        } else if (!clickBox.contains(e.getPoint())) {
            currentState = GUIButtonState.RELEASED;
        }
    }

    public void mousePressed(MouseEvent e) {
        if (currentState == GUIButtonState.BLOCKED) {
            return;
        }
        if (clickBox.contains(e.getPoint())) {
            currentState = GUIButtonState.PRESSED;
            for (ActionListener a : actionListeners)
                a.actionPerformed(null);
        }
    }

    public void Block() {
        if (currentState != GUIButtonState.BLOCKED) {
            currentState = GUIButtonState.BLOCKED;
        }
    }

    public void Unblock() {
        if (currentState == GUIButtonState.BLOCKED) {
            currentState = GUIButtonState.RELEASED;
        }
    }

    public void SetReleasedImage(BufferedImage released){
        //used to change image of unblocked button
        imageReleased=released;
    }

    private enum GUIButtonState {
        RELEASED, HOVERED, PRESSED, BLOCKED
    }
}
