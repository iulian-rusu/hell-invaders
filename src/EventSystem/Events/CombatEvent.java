package EventSystem.Events;

public enum CombatEvent implements GameEvent {
    STATUS_BAR_UPDATE,
    STATUS_BAR_RESET,
    ENEMY_ATTACK,
    ENEMY_DEATH,
    PLAYER_HIT,
    LEVEL_WIN,
    LEVEL_LOSS;

    @Override
    public GameEventType GetType() {
        return GameEventType.CombatEvent;
    }
}
