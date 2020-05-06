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
            instance = new AudioManager();
        }
        return instance;
    }

    public static void Init(){
        try {
            BackgroundMusicAssets.Init();
            SoundEffectAssets.Init();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }

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
        if (!(e instanceof AudioEvent))
            return;
        ((AudioEvent)e).playAudio.run();
    }

    private AudioManager(){
        Init();
    }
    private static AudioManager instance = null;
}
