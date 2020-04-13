package Assets.Audio;

import javax.sound.sampled.*;
import java.io.IOException;

public class SoundEffect implements Audio {
    Clip clip;
    String path;
    public boolean isPlaying;
    private SoundEffect child = null;

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

    public void StopAllSFX() {
        if (child != null) {
            child.StopAllSFX();
        }
        Stop();
    }

    @Override
    public void Play() {
        if (isPlaying) {
            //if this sound effects is already playing -> recursively ask the child to play
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
