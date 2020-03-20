package Audio;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class AudioManager {
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
        BackgroundMusic.Init();
        SoundEffect.Init();
    }

    private static AudioManager instance = null;
}
