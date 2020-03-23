package Audio;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class SoundEffect {
    public static Audio monsterHurt;
    public static Audio mosnterShoot;
    public static Audio monsterSpawn;
    public static Audio spellShoot;
    public static Audio winSFX;

    public static void Init() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        monsterHurt    = new Audio("/sounds/monster_hurt.wav",false);
        mosnterShoot = new Audio("/sounds/monster_shoot.wav",false);
        monsterSpawn= new Audio("/sounds/monster_spawn.wav",false);
        spellShoot= new Audio("/sounds/spell_shoot.wav",false);
        winSFX= new Audio("/sounds/win_sfx.wav",false);

    }
            }
