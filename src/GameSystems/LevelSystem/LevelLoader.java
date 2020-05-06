package GameSystems.LevelSystem;

import Entities.CollidableEntities.Enemies.Dragon;
import Entities.CollidableEntities.Enemies.Enemy;
import Entities.CollidableEntities.Enemies.Monster;
import Entities.CollidableEntities.Projectiles.Projectile;
import Game.Game;
import Game.GameWindow;
import States.PlayState;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class LevelLoader {
    public static final int LEVEL_PERIOD_LENGTH = 5;
    public static int GET_SECONDS_BETWEEN_WAVES(){return Game.DIFFICULTY == 3 ? 5 : 10;}
    public static final int X_DEVIATION = 500;
    private static final Random RNG = new Random(System.currentTimeMillis());//for enemy spawn positions

    public static void InitLevel(ArrayList<Projectile> allProjectiles, ArrayList<Enemy> allEnemies, int level) {
        allProjectiles.clear();
        allEnemies.clear();
        LoadEnemies(allEnemies, level);
    }

    public static void LoadEnemies(ArrayList<Enemy> allEnemies, int level) {
        try {
            String path = "out/production/Hell Invaders/levels/level" + ((level-1)%LEVEL_PERIOD_LENGTH+1) + ".txt";
            File f = new File(path);
            Scanner in = new Scanner(f);
            int numWaves = in.nextInt();
            for (int wave = 0; wave < numWaves; ++wave) {
                int numMonsters = in.nextInt();
                int numDragons = in.nextInt();
                LoadWave(allEnemies, level, wave, numMonsters, numDragons);
                if(level>5){
                    //load double for level > 5
                    LoadWave(allEnemies,level,wave,numMonsters,numDragons);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void LoadWave(ArrayList<Enemy> allEnemies, int level, int wave, int monsters, int dragons) {
        int x = GameWindow.screenDimension.width + 180 - Enemy.DEFAULT_X_VELOCITY * 60 * GET_SECONDS_BETWEEN_WAVES() * (wave);
        int y;
        for (int i = 0; i < monsters; ++i) {
            int xOffset = (i > 0) ? x + Math.abs(RNG.nextInt()) % X_DEVIATION : x;
            y = PlayState.BATTLEFIELD_Y + Math.abs(RNG.nextInt()) % (PlayState.BATTLEFIELD_HEIGHT);
            allEnemies.add(new Monster(xOffset, y, level));
        }
        for (int i = 0; i < dragons; ++i) {
            int xOffset = (i > 0) ? x + Math.abs(RNG.nextInt()) % X_DEVIATION : x;
            y = PlayState.BATTLEFIELD_Y + Math.abs(RNG.nextInt()) % (PlayState.BATTLEFIELD_HEIGHT);
            allEnemies.add(new Dragon(xOffset, y, level));
        }
    }
}
