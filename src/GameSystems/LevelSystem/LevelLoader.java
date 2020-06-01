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

/**
 * @brief Class that handles level initialization. Loads all enemies based on the current level and wave templates.
 */
public class LevelLoader {
    public static final int LEVEL_PERIOD_LENGTH = 5;///< The length of a level cycle.
    public static final int X_DEVIATION = 500;///< The maximum amount by which each enemy spawn point can deviate from the default spawn location.
    private static final Random RNG = new Random(System.currentTimeMillis());///< Random object that generates random number sequences.

    /**
     * Returns the interval between each wave based on the current game difficulty.
     *
     * @return An int representing the delay in seconds between each wave.
     */
    public static int GET_SECONDS_BETWEEN_WAVES() {
        return Game.difficulty == 3 ? 5 : 10;
    }

    /**
     * Loads all enemies into the level.
     *
     * @param allProjectiles A list of Projectile objects that will be cleared.
     * @param allEnemies     A list of all enemies in the current level. This list is modified by the function.
     * @param level          The current level to be loaded.
     */
    public static void InitLevel(ArrayList<Projectile> allProjectiles, ArrayList<Enemy> allEnemies, int level) {
        allProjectiles.clear();
        allEnemies.clear();
        LoadEnemies(allEnemies, level);
    }

    /**
     * Loads enemy waves based on the current level and a template read from a file.
     *
     * @param allEnemies A list of enemies into which the data is loaded.
     * @param level      The current level to be loaded.
     */
    public static void LoadEnemies(ArrayList<Enemy> allEnemies, int level) {
        try {
            String path = "out/production/Hell Invaders/levels/level" + ((level - 1) % LEVEL_PERIOD_LENGTH + 1) + ".txt";
            File f = new File(path);
            Scanner in = new Scanner(f);
            int numWaves = in.nextInt();
            for (int wave = 0; wave < numWaves; ++wave) {
                int numMonsters = in.nextInt();
                int numDragons = in.nextInt();
                LoadWave(allEnemies, level, wave, numMonsters, numDragons);
                if (level > 5) {
                    // Load twice as muany enemies for levels above 5
                    LoadWave(allEnemies, level, wave, numMonsters, numDragons);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads an individual wave.
     *
     * @param allEnemies A list of enemies into which the data is loaded.
     * @param level      The current level to be loaded.
     * @param wave       The current wave to be loaded.
     * @param monsters   The number of Monster type enemies to be loaded.
     * @param dragons    The number of Dragon type enemies to be loaded.
     */
    public static void LoadWave(ArrayList<Enemy> allEnemies, int level, int wave, int monsters, int dragons) {
        int x = (int)(GameWindow.SCREEN_DIMENSION.width + 180 - Enemy.DEFAULT_X_VELOCITY * 60 * GET_SECONDS_BETWEEN_WAVES() * (wave));
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
