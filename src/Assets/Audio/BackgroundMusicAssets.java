package Assets.Audio;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * @brief Holds references to some background music.
 */
public class BackgroundMusicAssets {
    public static BackgroundMusic menuMusic;///< Plays in the main menu.
    public static BackgroundMusic upgradeMusic;///< Plays in the upgrade menu.
    public static BackgroundMusic gameMusic;///< Plays in during a level.
    public static BackgroundMusic winMusic;///< Plays when the level is won.
    public static BackgroundMusic lossMusic;///< Plays when the level is lost.

    /**
     * Loads the music from the disk.
     *
     * @throws UnsupportedAudioFileException In case it's not w .wav file.
     * @throws IOException                   If the file is missing.
     * @throws LineUnavailableException      If the clip is already being used.
     */
    public static void Init() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        menuMusic = new BackgroundMusic("/sounds/menu_music.wav", true);
        upgradeMusic = new BackgroundMusic("/sounds/upgrade_music.wav", true);
        gameMusic = new BackgroundMusic("/sounds/game_music.wav", true);
        winMusic = new BackgroundMusic("/sounds/win_music.wav", false);
        lossMusic = new BackgroundMusic("/sounds/loss_music.wav", false);
    }
}
