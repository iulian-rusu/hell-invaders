package LevelSystem;

import Entities.CollidableEntities.Enemies.Dragon;
import Entities.CollidableEntities.Enemies.Enemy;
import Entities.CollidableEntities.Enemies.Monster;
import Entities.CollidableEntities.Projectiles.Projectile;
import Game.Game;
import Game.GameWindow;
import States.GameState;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class LevelInitializer {

    public static final int LEVEL_PERIOD_LENGTH = 5;
    public static final int SECONDS_BETWEEN_WAVES = (4-Game.DIFFICULTY)*5;
    public static final int X_DEVIATION = 500;
    private static final Random RNG = new Random();

    public static void InitLevel(ArrayList<Projectile> allProjectiles, ArrayList<Enemy> allEnemies, int level) {
        allProjectiles.clear();
        allEnemies.clear();
        LoadEnemies(allEnemies, level % LEVEL_PERIOD_LENGTH);
    }

    public static void LoadEnemies(ArrayList<Enemy> allEnemies, int level) {
        try {
            String path = "out/production/Hell Invaders/levels/level" + level + ".txt";
            File f = new File(path);
            Scanner in = new Scanner(f);
            int numWaves = in.nextInt();
            for (int i = 0; i < numWaves; ++i) {
                int m = in.nextInt();
                int d = in.nextInt();
                LoadWave(allEnemies, level, i, m, d);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void LoadWave(ArrayList<Enemy> allEnemies, int level, int wave, int monsters, int dragons) {
        int x = GameWindow.wndDimension.width + 180 - Enemy.DEFAULT_X_VELOCITY * 60 * SECONDS_BETWEEN_WAVES * (wave);
        int y;
        for (int i = 0; i < monsters; ++i) {
            int xOffset = (i > 0) ? x + Math.abs(RNG.nextInt()) % X_DEVIATION : x;
            y = GameState.BATTLEFIELD_Y + Math.abs(RNG.nextInt()) % (GameState.BATTLEFIELD_HEIGHT);
            allEnemies.add(new Monster(xOffset, y, level));
        }
        for (int i = 0; i < dragons; ++i) {
            int xOffset = (i > 0) ? x + Math.abs(RNG.nextInt()) % X_DEVIATION : x;
            y = GameState.BATTLEFIELD_Y + Math.abs(RNG.nextInt()) % (GameState.BATTLEFIELD_HEIGHT);
            allEnemies.add(new Dragon(xOffset, y, level));
        }
    }
}
