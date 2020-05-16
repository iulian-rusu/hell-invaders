package GameSystems.EventSystem;

import GameSystems.EventSystem.Events.GameEvent;

/**
 * @brief An interface implemented by all observer-type classes. Provides a means of observing objects and responding to game events.
 */
public interface Observer {

    /**
     * Called when an observer is notified.
     *
     * @param e A GameEvent object.
     */
    void OnNotify(GameEvent e);
}
