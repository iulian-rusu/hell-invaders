package EventSystem.Events;

public enum CombatEvent implements GameEvent {
    LEVEL_BEGIN,
    MONSTER_SPAWN,
    MONSTER_HIT,
    MONSTER_ATTACK,
    MONSTER_DEATH,
    SPELL_CAST,
    SPELL_HIT,
    SPELL_CRI,
    LEVEL_WIN,
    LEVEL_LOSS;

    @Override
    public GameEventType GetType() {
        return GameEventType.CombatEvent;
    }
}
