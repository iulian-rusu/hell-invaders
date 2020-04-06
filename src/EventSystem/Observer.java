package EventSystem;

import EventSystem.Events.GameEvent;

public interface Observer {
    void OnNotify(GameEvent e);
}
