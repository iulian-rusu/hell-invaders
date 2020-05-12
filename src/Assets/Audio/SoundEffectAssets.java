package Assets.Audio;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * @brief Holds references to some sound effetcs.
 */
public class SoundEffectAssets {
    public static SoundEffect enemyHurt;
    public static SoundEffect dragonShoot;
    public static SoundEffect enemySpawn;
    public static SoundEffect fireShoot;
    public static SoundEffect frostShoot;
    public static SoundEffect arcaneShoot;
    public static SoundEffect winSFX;
    public static SoundEffect oof;
    public static SoundEffect buttonPress;

    /**
     * Loads sound effects from the disk.
     *
     * @throws UnsupportedAudioFileException In case it's not w .wav file.
     * @throws IOException                   If the file is missing.
     * @throws LineUnavailableException      If the clip is already being used.
     */
    public static void Init() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        enemyHurt = new SoundEffect("/sounds/enemy_hurt.wav");
        dragonShoot = new SoundEffect("/sounds/dragon_shoot.wav");
        enemySpawn = new SoundEffect("/sounds/enemy_spawn.wav");
        fireShoot = new SoundEffect("/sounds/fire_shoot.wav");
        frostShoot = new SoundEffect("/sounds/frost_shoot.wav");
        arcaneShoot = new SoundEffect("/sounds/arcane_shoot.wav");
        winSFX = new SoundEffect("/sounds/win_sfx.wav");
        oof = new SoundEffect("/sounds/oof.wav");
        buttonPress = new SoundEffect("/sounds/button_press.wav");
    }
}
