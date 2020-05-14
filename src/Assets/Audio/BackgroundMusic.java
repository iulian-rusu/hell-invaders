package Assets.Audio;

import javax.sound.sampled.*;
import java.io.IOException;

/**
 * @brief Container for a Clip object that provides an API for playing background music.
 */
public class BackgroundMusic implements Audio {
    public boolean isPlaying;///< Flag that indicates if the music is playing.
    public boolean isLooping;///< Flag that indicates if the music will loop after finishing.
    Clip clip;///< The actual clip object.
    AudioInputStream audioInputStream;///< The input stream from the file.
    AudioFormat format;///< The format of the file.
    DataLine.Info info;///< The dataline info from the format.

    /**
     * Constructor with parameters.
     *
     * @param path The path to the music file on the disk.
     * @param loop Indicates if the music will loop again.
     * @throws UnsupportedAudioFileException In case it's not w .wav file.
     * @throws IOException                   If the file is missing.
     * @throws LineUnavailableException      If the clip is already being used.
     */
    public BackgroundMusic(String path, boolean loop) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        audioInputStream = AudioSystem.getAudioInputStream(BackgroundMusic.class.getResource(path));
        format = audioInputStream.getFormat();
        info = new DataLine.Info(Clip.class, format);
        clip = (Clip) AudioSystem.getLine(info);
        clip.open(audioInputStream);
        isLooping = loop;
        clip.addLineListener(lineEvent -> {
            if (lineEvent.getType() == LineEvent.Type.STOP) {
                // If the isPlaying flag is true at this point, it was not set by the Stop() method
                // The clip reached its end automatically, therefore it loops
                if (isLooping && isPlaying) {
                    isPlaying = false;
                    Play();
                    return;
                }
            }
            isPlaying = lineEvent.getType() == LineEvent.Type.START;
        });
        isPlaying = false;
        clip.stop();
    }

    @Override
    public void Play() {
        if (!isPlaying) {
            clip.setMicrosecondPosition(0);
            clip.start();
        }
    }

    @Override
    public void Stop() {
        if (isPlaying) {
            // Explicitly setting isPlaying flag to false implies the clip was stopped intentionally and not because it reached its end
            isPlaying = false;
            clip.stop();
        }
    }

    @Override
    public void Resume() {
        if (!isPlaying) {
            clip.start();
        }
    }
}
