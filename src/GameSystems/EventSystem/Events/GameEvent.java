package GameSystems.EventSystem.Events;

public interface GameEvent {
    GameEventType GetType();

    enum GameEventType{
        AudioEvent,
        CombatEvent,
        UpgradeEvent
    }
}
