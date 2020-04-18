package GUI;

import Assets.Audio.AudioManager;
import GameSystems.EventSystem.Events.AudioEvent;
import GameSystems.EventSystem.Observable;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GUIButton extends Observable {
    //size parameters
    public static final int BUTTON_W = 230;
    public static final int BUTTON_H = 60;

    private GUIButtonState currentState;
    private final Rectangle clickBox;
    private final ArrayList<ActionListener> actionListeners;
    private BufferedImage imageReleased;
    private BufferedImage imageHovered;

    public GUIButton(BufferedImage released, BufferedImage hovered, int x, int y, int width, int height) {
        imageReleased = released;
        imageHovered = hovered;
        clickBox = new Rectangle(x, y, width, height);
        actionListeners = new ArrayList<>();
        currentState = GUIButtonState.RELEASED;
        //for adio events
        AddObserver(AudioManager.GetInstance());
    }

    public void Init() {
        if(currentState!=GUIButtonState.BLOCKED) {
            currentState = GUIButtonState.RELEASED;
        }
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
            NotifyAllObservers(AudioEvent.PLAY_BUTTON_PRESS);
            for (ActionListener a : actionListeners)
                a.actionPerformed(null);
        }
    }

    public void SetImages(BufferedImage released, BufferedImage hovered){
        this.imageReleased=released;
        this.imageHovered=hovered;
    }

    public void Block(BufferedImage blockedIcon) {
        if (currentState != GUIButtonState.BLOCKED) {
            currentState = GUIButtonState.BLOCKED;
            this.imageReleased=blockedIcon;
        }
    }

    public void Unblock(BufferedImage unblockedIcon) {
        if (currentState == GUIButtonState.BLOCKED) {
            currentState = GUIButtonState.RELEASED;
            this.imageReleased=unblockedIcon;
        }
    }

    private enum GUIButtonState {
        RELEASED, HOVERED, PRESSED, BLOCKED
    }
}
