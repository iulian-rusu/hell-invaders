package Assets.Audio;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class SoundEffectAssets {

    public static SoundEffect enemyHurt;
    public static SoundEffect dragonShoot;
    public static SoundEffect enemySpawn;
    public static SoundEffect fireShoot;
    public static SoundEffect frostShoot;
    public static SoundEffect arcaneShoot;
    public static SoundEffect winSFX;
    public static SoundEffect oof;

    public static void Init() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        enemyHurt = new SoundEffect("/sounds/enemy_hurt.wav");
        dragonShoot = new SoundEffect("/sounds/dragon_shoot.wav");
        enemySpawn = new SoundEffect("/sounds/enemy_spawn.wav");
        fireShoot = new SoundEffect("/sounds/fire_shoot.wav");
        frostShoot = new SoundEffect("/sounds/frost_shoot.wav");
        arcaneShoot = new SoundEffect("/sounds/arcane_shoot.wav");
        winSFX = new SoundEffect("/sounds/win_sfx.wav");
        oof = new SoundEffect("/sounds/oof.wav");
    }
}
