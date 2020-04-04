package Audio;

import EventSystem.Events.AudioEvent;
import EventSystem.Events.GameEvent;
import EventSystem.Observer;
import States.StateManager;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class AudioManager implements Observer {
    //singleton audio manager class
    //must be instanciated before state manager class
    public static AudioManager GetInstance() {
        if (instance == null) {
            try {
                instance = new AudioManager();
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public void Play(Audio sound) {
        sound.Play();
    }

    public void Stop(Audio sound) {
        sound.Stop();
    }

    public void Resume(Audio sound) {
        sound.Resume();
    }

    private AudioManager() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        BackgroundMusicAssets.Init();
        SoundEffectAssets.Init();
    }

    private static AudioManager instance = null;

    @Override
    public void OnNotify(GameEvent e) {
        if (!(e.GetType() == GameEvent.GameEventType.SFXEvent))
            return;
        switch ((AudioEvent) e) {
            case PLAY_CURRENT_STATE_MUSIC:
                Play(StateManager.GetInstance().GetCurrentStateIndex().bgMusic);
                break;
            case STOP_CURRENT_STATE_MUSIC:
                Stop(StateManager.GetInstance().GetCurrentStateIndex().bgMusic);
                break;
            case PLAY_SPELL_SHOOT:
                Play(SoundEffectAssets.spellShoot);
                break;
            case PLAY_ENEMY_SPAWN:
                Play(SoundEffectAssets.enemySpawn);
                break;
            case PLAY_DRAGON_SHOOT:
                Play(SoundEffectAssets.dragonShoot);
                break;
            case PLAY_ENEMY_HURT:
                Play(SoundEffectAssets.enemyHurt);
                break;
            case PLAY_WIN_SFX:
                Play(SoundEffectAssets.winSFX);
                break;
        }
    }
}
