package Assets.Audio;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * @brief Holds references to some sound effetcs.
 */
public class SoundEffectAssets {
    public static SoundEffect enemyHurt;///< Plays when an enemy is hit.
    public static SoundEffect dragonShoot;///< Plays when a dragon attacks.
    public static SoundEffect enemySpawn;///< Plays when an enemy spawns.
    public static SoundEffect fireShoot;///< Plays when a fire spell is cast.
    public static SoundEffect frostShoot;///< Plays when a frost spell is cast.
    public static SoundEffect arcaneShoot;///< Plays when an arcane spell is cast.
    public static SoundEffect winSFX;///< Plays when a level is won.
    public static SoundEffect oof;///< Plays when the player is hit.
    public static SoundEffect buttonPress;///< Plays when a button is pressed.

    /**
     * Loads sound effects from the disk.
     *
     * @throws UnsupportedAudioFileException If the audio format is invalid.
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
