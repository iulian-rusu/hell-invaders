package EventSystem.Events;

public interface GameEvent {
    GameEventType GetType();

    enum GameEventType{
        SFXEvent,
        CombatEvent,
        UpgradeEvent
    }
}
