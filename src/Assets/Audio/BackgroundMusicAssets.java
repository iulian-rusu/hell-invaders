package Assets.Audio;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class BackgroundMusicAssets {
    public static BackgroundMusic menuMusic;
    public static BackgroundMusic upgradeMusic;
    public static BackgroundMusic gameMusic;
    public static BackgroundMusic winMusic;
    public static BackgroundMusic lossMusic;

    public static void Init() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        menuMusic=new BackgroundMusic("/sounds/menu_music.wav",true);
        upgradeMusic=new BackgroundMusic("/sounds/upgrade_music.wav",true);
        gameMusic=new BackgroundMusic("/sounds/game_music.wav",true);
        winMusic=new BackgroundMusic("/sounds/win_music.wav",false);
        lossMusic=new BackgroundMusic("/sounds/loss_music.wav",false);
    }
}
