package GameSystems.EventSystem;

import GameSystems.EventSystem.Events.GameEvent;

/**
 * @brief An interface implemented by all observer-type classes. Provides a means of observing objects and responding to game events.
 */
public interface Observer {

    /**
     * Called to notify the observer that an event has happened.
     *
     * @param e A GameEvent object.
     */
    void OnNotify(GameEvent e);
}
