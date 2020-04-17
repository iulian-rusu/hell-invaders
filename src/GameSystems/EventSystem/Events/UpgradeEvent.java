package GameSystems.EventSystem.Events;

public enum UpgradeEvent implements GameEvent {
    SPELL_UPGRADE_BOUGHT,
    DAMAGE_UPGRADE_BOUGHT,
    CRIT_UPGRADE_BOUGHT,
    PROJECTILE_UPGRADE_BOUGHT;

    @Override
    public GameEventType GetType() {
        return GameEventType.UpgradeEvent;
    }
}
