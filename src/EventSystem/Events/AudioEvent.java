package EventSystem.Events;

public enum AudioEvent implements GameEvent {
    PLAY_CURRENT_STATE_MUSIC,
    STOP_CURRENT_STATE_MUSIC,
    PLAY_SPELL_SHOOT,
    PLAY_ENEMY_SPAWN,
    PLAY_DRAGON_SHOOT,
    PLAY_ENEMY_HURT,
    PLAY_WIN_SFX;

    @Override
    public GameEventType GetType() {
        return GameEventType.SFXEvent;
    }
}
