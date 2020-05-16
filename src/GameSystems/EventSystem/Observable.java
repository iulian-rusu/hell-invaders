package GameSystems.EventSystem;

import GameSystems.EventSystem.Events.GameEvent;

import java.util.ArrayList;

/**
 * @brief Abstract class extended by all classes that can hold and notify observers.
 */
public abstract class Observable {
    private ArrayList<Observer> allObservers;///< A list of all the observers of the object.

    /**
     * Allows the adding of an observer to the observer list.
     *
     * @param o An Observer object.
     */
    public void AddObserver(Observer o) {
        if (allObservers == null) {
            allObservers = new ArrayList<>();
        }
        allObservers.add(o);
    }

    /**
     * Allows the removing of an observer to the observer list.
     *
     * @param o An Observer object.
     */
    public void RemoveObserver(Observer o) {
        allObservers.remove(o);
    }

    /**
     * Notifies all current observers.
     *
     * @param e A GameEvent object.
     */
    public void NotifyAllObservers(GameEvent e) {
        if (allObservers == null)
            return;
        for (Observer o : allObservers) {
            o.OnNotify(e);
        }
    }
}
