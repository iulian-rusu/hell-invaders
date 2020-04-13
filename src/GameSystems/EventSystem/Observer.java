package GameSystems.EventSystem;

import GameSystems.EventSystem.Events.GameEvent;

public interface Observer {
    void OnNotify(GameEvent e);
}
