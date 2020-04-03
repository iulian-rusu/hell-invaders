package Audio;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class SoundEffectAssets {
    public static SoundEffect monsterHurt;
    public static SoundEffect mosnterShoot;
    public static SoundEffect monsterSpawn;
    public static SoundEffect spellShoot;
    public static SoundEffect winSFX;

    public static void Init() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        monsterHurt = new SoundEffect("/sounds/monster_hurt.wav");
        mosnterShoot = new SoundEffect("/sounds/monster_shoot.wav");
        monsterSpawn = new SoundEffect("/sounds/monster_spawn.wav");
        spellShoot = new SoundEffect("/sounds/spell_shoot.wav");
        winSFX = new SoundEffect("/sounds/win_sfx.wav");
    }
}
