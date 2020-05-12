package GameSystems.EventSystem.Events;


/**
 *  @brief Holds all possible upgrade-related events.
 */
public enum UpgradeEvent implements GameEvent {
    SPELL_UPGRADE_BOUGHT,
    DAMAGE_UPGRADE_BOUGHT,
    CRIT_UPGRADE_BOUGHT,
    PROJECTILE_UPGRADE_BOUGHT
}
