package Assets.Audio;

import GameSystems.EventSystem.Events.AudioEvent;
import GameSystems.EventSystem.Events.GameEvent;
import GameSystems.EventSystem.Observer;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * @brief Class that manages audio events.
 * <p>
 * This is a singleton class.
 * It must be instanciated before the StateManager class to avoid conflicts.
 */
public class AudioManager implements Observer {

    /**
     * Used to access the instance of the class.
     *
     * @return An instance of AudioManager.
     */
    public static AudioManager GetInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    /**
     * Loads audio assets from the disk.
     */
    public static void Init() {
        try {
            BackgroundMusicAssets.Init();
            SoundEffectAssets.Init();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }

    }

    /**
     * Plays the specified sound.
     *
     * @param sound An Audio object to be played.
     */
    public void Play(Audio sound) {
        sound.Play();
    }

    /**
     * Stops the specified sound.
     *
     * @param sound An Audio object to be stopped.
     */
    public void Stop(Audio sound) {
        sound.Stop();
    }

    /**
     * Stops all currentlyplaying sound effects.
     */
    public void StopAllSFX() {
        SoundEffectAssets.enemySpawn.StopAllSFX();
        SoundEffectAssets.enemyHurt.StopAllSFX();
        SoundEffectAssets.dragonShoot.StopAllSFX();
        SoundEffectAssets.oof.StopAllSFX();
    }

    /**
     * Resumes the playing of a sound.
     *
     * @param sound An Audio object to be resumed.
     */
    public void Resume(Audio sound) {
        sound.Resume();
    }

    @Override
    public void OnNotify(GameEvent e) {
        if (!(e instanceof AudioEvent))
            return;
        ((AudioEvent) e).playAudio.run();
    }

    /**
     * Private constructor without parameters.
     */
    private AudioManager() {
        Init();
    }

    private static AudioManager instance = null;///< The unique instance of the singleton.
}
