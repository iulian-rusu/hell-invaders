package GameSystems.EventSystem.Events;

import Assets.Audio.AudioManager;
import Assets.Audio.SoundEffectAssets;
import States.StateManager;

import java.util.function.Function;

public enum AudioEvent implements GameEvent {
    PLAY_CURRENT_STATE_MUSIC(_void -> {
        AudioManager.GetInstance().Play(StateManager.GetInstance().GetCurrentStateIndex().bgMusic);
        return null;
    }),
    STOP_CURRENT_STATE_MUSIC(_void -> {
        AudioManager.GetInstance().Stop(StateManager.GetInstance().GetCurrentStateIndex().bgMusic);
        return null;
    }),
    PLAY_FIRE_SHOOT(_void -> {
        AudioManager.GetInstance().Play(SoundEffectAssets.fireShoot);
        return null;
    }),
    PLAY_FROST_SHOOT(_void -> {
        AudioManager.GetInstance().Play(SoundEffectAssets.frostShoot);
        return null;
    }),
    PLAY_ARCANE_SHOOT(_void -> {
        AudioManager.GetInstance().Play(SoundEffectAssets.arcaneShoot);
        return null;
    }),
    PLAY_ENEMY_SPAWN(_void -> {
        AudioManager.GetInstance().Play(SoundEffectAssets.enemySpawn);
        return null;
    }),
    PLAY_DRAGON_SHOOT(_void -> {
        AudioManager.GetInstance().Play(SoundEffectAssets.dragonShoot);
        return null;
    }),
    PLAY_ENEMY_HURT(_void -> {
        AudioManager.GetInstance().Play(SoundEffectAssets.enemyHurt);
        return null;
    }),
    PLAY_WIN_SFX(_void -> {
        AudioManager.GetInstance().Play(SoundEffectAssets.winSFX);
        return null;
    }),
    PLAY_OOF(_void->{
        AudioManager.GetInstance().Play(SoundEffectAssets.oof);
        return null;
    }),
    PLAY_BUTTON_PRESS(_void->{
        AudioManager.GetInstance().Play(SoundEffectAssets.buttonPress);
        return null;
    }),
    STOP_ALL_SFX(_void -> {
        AudioManager.GetInstance().StopAllSFX();
        return null;
    });

    //all audio events have an audio action that plays/stops necessary sounds
    public Function<Void, Void> performAction;

    AudioEvent(Function<Void, Void> action) {
        performAction = action;
    }

    @Override
    public GameEventType GetType() {
        return GameEventType.AudioEvent;
    }
}
