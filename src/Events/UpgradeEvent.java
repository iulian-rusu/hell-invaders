package Events;

public class UpgradeEvent implements GameEvent {
    public static final int SPELL_UPGRADE_BOUGHT=0x1;
    public static final int ATTACK_UPGRADE_BOUGHT=0x2;
    public static final int CRIT_UPGRADE_BOUGHT=0x4;
    public static final int PROJECTILE_UPGRADE_BOUGHT=0x8;

    int type;
    @Override
    public int GetType() {
        return 0;
    }
}
