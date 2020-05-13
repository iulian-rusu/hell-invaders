package Entities;

import GameSystems.EventSystem.Observable;

import java.awt.*;

/**
 *  @brief Implements a generic entity - an object that can be dynamically created and interracted with.
 */
public abstract class Entity extends Observable {
    protected int frameCount = -1;///< Counts the current frame of the game.

    /**
     * Called each frame to update the state of the entity.
     */
    public void Update() {
        frameCount = (frameCount + 1) % 60;
    }

    /**
     * Called each frame to draw the entity on the screen.
     *
     * @param g A Java Graphics object.
     */
    public abstract void Draw(Graphics g);
}
