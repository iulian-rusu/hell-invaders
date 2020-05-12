package Assets.Audio;

import javax.sound.sampled.*;
import java.io.IOException;

/**
 * @brief Container for a Clip object that provides an API for playing sound effects.
 */
public class SoundEffect implements Audio {
    Clip clip;///< The actual Clip object that stores the sound data.
    String path;///< The name of the music file on the disk.
    public boolean isPlaying;///< Flag that indicates if the clip is playing.
    private SoundEffect child = null;///< Child clip, created if the current clip is busy and can't play.

    /**
     * Constructor with parameters.
     *
     * @param path The path to the music file on the disk.
     * @throws UnsupportedAudioFileException In case it's not w .wav file.
     * @throws IOException                   If the file is missing.
     * @throws LineUnavailableException      If the clip is already being used.
     */
    public SoundEffect(String path) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        this.path = path;
        AudioInputStream audioInputStream;
        AudioFormat format;
        DataLine.Info info;
        audioInputStream = AudioSystem.getAudioInputStream(BackgroundMusic.class.getResource(path));
        format = audioInputStream.getFormat();
        info = new DataLine.Info(Clip.class, format);
        clip = (Clip) AudioSystem.getLine(info);
        clip.open(audioInputStream);
        clip.addLineListener(lineEvent -> {
            if (lineEvent.getType() == LineEvent.Type.STOP) {
                isPlaying = false;
                clip.setMicrosecondPosition(0);
            }
        });
        isPlaying = false;
        clip.stop();
    }

    /**
     * Stops the current sound effect and all its children.
     */
    public void StopAllSFX() {
        if (child != null) {
            child.StopAllSFX();
        }
        Stop();
    }

    @Override
    public void Play() {
        if (isPlaying) {
            // If this sound effects is already playing, recursively ask the child to play
            if (child == null) {
                try {
                    child = new SoundEffect(this.path);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            child.Play();
            return;
        }
        isPlaying = true;
        clip.start();
    }

    @Override
    public void Stop() {
        isPlaying = false;
        clip.stop();
    }

    @Override
    public void Resume() {
        isPlaying = true;
        clip.start();
    }
}
