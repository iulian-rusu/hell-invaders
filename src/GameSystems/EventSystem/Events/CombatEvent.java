package GameSystems.EventSystem.Events;


/**
 * @brief Holds all possible combat-related events.
 */
public enum CombatEvent implements GameEvent {
    STATUS_BAR_UPDATE,
    STATUS_BAR_RESET,
    ENEMY_ATTACK,
    ENEMY_DEATH,
    LEVEL_WIN,
    LEVEL_LOSS
}
