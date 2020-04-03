package Events;

public class CombatEvent implements GameEvent{
    public static final int LEVEL_BEGIN=0x1;
    public static final int MONSTER_SPAWN=0x2;
    public static final int MONSTER_ATTACK=0x4;
    public static final int MONSTER_DEATH=0x8;
    public static final int SPELL_CAST=0x10;
    public static final int SPELL_HIT=0x20;
    public static final int SPELL_CRIT=0x40;
    public static final int LEVEL_WIN=0x80;
    public static final int LEVEL_LOSS=0x100;

    private int type;
    @Override
    public int GetType() {
        return type;
    }
}
