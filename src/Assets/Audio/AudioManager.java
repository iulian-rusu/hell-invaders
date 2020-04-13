package Assets.Audio;

import GameSystems.EventSystem.Events.AudioEvent;
import GameSystems.EventSystem.Events.GameEvent;
import GameSystems.EventSystem.Observer;

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

    public void StopAllSFX() {
        //recursively stops all active sound effects from their respective lists
        SoundEffectAssets.enemySpawn.StopAllSFX();
        SoundEffectAssets.enemyHurt.StopAllSFX();
        SoundEffectAssets.dragonShoot.StopAllSFX();
        SoundEffectAssets.oof.StopAllSFX();
    }

    public void Resume(Audio sound) {
        sound.Resume();
    }

    @Override
    public void OnNotify(GameEvent e) {
        if (!(e.GetType() == GameEvent.GameEventType.AudioEvent))
            return;
        ((AudioEvent)e).performAction.apply(null);
    }

    private AudioManager() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        BackgroundMusicAssets.Init();
        SoundEffectAssets.Init();
    }

    private static AudioManager instance = null;
}
