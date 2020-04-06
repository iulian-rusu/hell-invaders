package Audio;

import javax.sound.sampled.*;
import java.io.IOException;

public class BackgroundMusic implements Audio {

    Clip clip;
    AudioInputStream audioInputStream;
    AudioFormat format;
    DataLine.Info info;
    public boolean isPlaying;
    public boolean isLooping;

    public BackgroundMusic(String path, boolean loop) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        audioInputStream = AudioSystem.getAudioInputStream(BackgroundMusic.class.getResource(path));
        format = audioInputStream.getFormat();
        info = new DataLine.Info(Clip.class, format);
        clip = (Clip) AudioSystem.getLine(info);
        clip.open(audioInputStream);
        isLooping = loop;
        clip.addLineListener(lineEvent -> {
            if(lineEvent.getType()== LineEvent.Type.STOP) {
                // if the isPlaying flag is true at this point, it was not set by the Stop() method
                // -> the clip reached its end automatically, therefore it loops:
                if (isLooping && isPlaying) {
                    isPlaying=false;
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
            //explicitly setting isPlaying flag to false implies the clip was stopped intentionally and not because it reached its end
            isPlaying=false;
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
