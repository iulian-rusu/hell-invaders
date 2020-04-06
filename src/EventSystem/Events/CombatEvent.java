package EventSystem.Events;

public enum CombatEvent implements GameEvent {
    LEVEL_BEGIN,
    MONSTER_SPAWN,
    STATUS_BAR_UPDATE,
    STATUS_BAR_RESET,
    MONSTER_HIT,
    MONSTER_ATTACK,
    MONSTER_DEATH,
    SPELL_CAST,
    SPELL_HIT,
    SPELL_CRI,
    PLAYER_HIT,
    LEVEL_WIN,
    LEVEL_LOSS;

    @Override
    public GameEventType GetType() {
        return GameEventType.CombatEvent;
    }
}
