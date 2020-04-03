package Audio;

import Events.SFXEvent;
import Events.GameEvent;
import Events.Observer;

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
    public void OnNotify(GameEvent e){
        if(!(e instanceof SFXEvent))
            return;
        int eventType = e.GetType();
        if ((eventType & SFXEvent.PLAY_SPELL_SHOOT) != 0)
            Play(SoundEffectAssets.spellShoot);
        if ((eventType & SFXEvent.PLAY_MONSTER_SPAWN) != 0)
            Play(SoundEffectAssets.monsterSpawn);
        if ((eventType & SFXEvent.PLAY_MONSTER_SHOOT) != 0)
            Play(SoundEffectAssets.mosnterShoot);
        if ((eventType & SFXEvent.PLAY_MONSTER_HURT) != 0)
            Play(SoundEffectAssets.monsterHurt);
        if ((eventType & SFXEvent.PLAY_WIN_SFX) != 0)
            Play(SoundEffectAssets.winSFX);
    }
}
