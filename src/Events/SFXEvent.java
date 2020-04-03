package Events;

public class SFXEvent implements GameEvent {
    public static final int PLAY_SPELL_SHOOT=0x1;
    public static final int PLAY_MONSTER_SPAWN=0x2;
    public static final int PLAY_MONSTER_SHOOT=0x4;
    public static final int PLAY_MONSTER_HURT=0x8;
    public static final int PLAY_WIN_SFX=0x10;

    private int type;
    public SFXEvent(int t){
        type=t;
    }
    @Override
    public int GetType() {
        return type;
    }
}
