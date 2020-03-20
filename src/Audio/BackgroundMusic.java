package Audio;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class BackgroundMusic  {

    public static Audio menuMusic;
    public static Audio upgradeMusic;
    public static Audio gameMusic;
    public static Audio winMusic;
    public static Audio lossMusic;
    public static void Init() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        menuMusic=new Audio("/sounds/menu_music.wav",true);
        upgradeMusic=new Audio("/sounds/upgrade_music.wav",true);
        gameMusic=new Audio("/sounds/game_music.wav",true);
        winMusic=new Audio("/sounds/win_music.wav",false);
        lossMusic=new Audio("/sounds/loss_music.wav",false);
    }
}
