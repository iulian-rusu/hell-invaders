package GameSystems.EventSystem.Events;

import Assets.Audio.AudioManager;
import Assets.Audio.SoundEffectAssets;
import States.StateManager;

/**
 *  @brief Holds all possible audio-related events.
 */
public enum AudioEvent implements GameEvent {
    PLAY_CURRENT_STATE_MUSIC(() -> AudioManager.GetInstance().Play(StateManager.GetInstance().GetCurrentStateIndex().bgMusic)),
    STOP_CURRENT_STATE_MUSIC(() -> AudioManager.GetInstance().Stop(StateManager.GetInstance().GetCurrentStateIndex().bgMusic)),
    PLAY_FIRE_SHOOT(() -> AudioManager.GetInstance().Play(SoundEffectAssets.fireShoot)),
    PLAY_FROST_SHOOT(() -> AudioManager.GetInstance().Play(SoundEffectAssets.frostShoot)),
    PLAY_ARCANE_SHOOT(() -> AudioManager.GetInstance().Play(SoundEffectAssets.arcaneShoot)),
    PLAY_ENEMY_SPAWN(() -> AudioManager.GetInstance().Play(SoundEffectAssets.enemySpawn)),
    PLAY_DRAGON_SHOOT(() -> AudioManager.GetInstance().Play(SoundEffectAssets.dragonShoot)),
    PLAY_ENEMY_HURT(() -> AudioManager.GetInstance().Play(SoundEffectAssets.enemyHurt)),
    PLAY_WIN_SFX(() -> AudioManager.GetInstance().Play(SoundEffectAssets.winSFX)),
    PLAY_OOF(() -> AudioManager.GetInstance().Play(SoundEffectAssets.oof)),
    PLAY_BUTTON_PRESS(() -> AudioManager.GetInstance().Play(SoundEffectAssets.buttonPress)),
    STOP_ALL_SFX(() -> AudioManager.GetInstance().StopAllSFX());

    public Runnable playAudio;///< A Runnable object that is called to play necessary audio related to the event.

    AudioEvent(Runnable action) {
        playAudio = action;
    }
}
