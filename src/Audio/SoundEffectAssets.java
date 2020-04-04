package Audio;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class SoundEffectAssets {
    public static SoundEffect enemyHurt;
    public static SoundEffect dragonShoot;
    public static SoundEffect enemySpawn;
    public static SoundEffect spellShoot;
    public static SoundEffect winSFX;

    public static void Init() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        enemyHurt = new SoundEffect("/sounds/enemy_hurt.wav");
        dragonShoot = new SoundEffect("/sounds/dragon_shoot.wav");
        enemySpawn = new SoundEffect("/sounds/enemy_spawn.wav");
        spellShoot = new SoundEffect("/sounds/spell_shoot.wav");
        winSFX = new SoundEffect("/sounds/win_sfx.wav");
    }
}
