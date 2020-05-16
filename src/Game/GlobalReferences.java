package Game;

import Entities.Player;
import GameSystems.UpgradeSystem.ExperiencePanel;
import SQL.DatabaseManager;

/**
 * @brief Helper class for easier access to important references.
 */
public class GlobalReferences {
    private static Player player = null;///< Reference to the main player.
    private static GameWindow gameWindow = null;///< Reference to the main game window.
    private static ExperiencePanel experiencePanel = null;///< Reference to the experience panel of the player.

    /**
     * Returns the Player instance.
     * <p>
     * If the player hasn't been accessed yet, this method will create a new player and will load player data from the database.
     * If no player data can be found, the method will load default values.
     *
     * @return A Player instance.
     */
    public static Player GetPlayer() {
        if (player == null) {
            player = new Player();
            // Check if data can be loaded from a previous save
            if (!DatabaseManager.IsEmpty(DatabaseManager.PLAYER_DATA_NAME)) {
                DatabaseManager.LoadPlayerData();
            } else {
                // Else load default values and store them in the database
                player.ResetAllStats();
            }
        }
        return player;
    }

    /**
     * Sets the game window instance.
     *
     * @param gameWindow The GameWindow object to be set.
     */
    public static void SetGameWindow(GameWindow gameWindow) {
        GlobalReferences.gameWindow = gameWindow;
    }

    /**
     * Returns the GameWindow instance.
     *
     * @return A GameWindow instance.
     */
    public static GameWindow GetGameWindow() {
        if (gameWindow == null) {
            throw new NullPointerException("Game window not initialized yet");
        }
        return gameWindow;
    }

    /**
     * Returns the ExperiencePanel instance.
     * If the experience panel hasn't been accessed yet, this method will create it.
     * If the experience panel hasn't been accessed yet, this method will create itF.
     *
     * @return An ExperiencePanel instance
     */
    public static ExperiencePanel GetExperiencePanel() {
        if (experiencePanel == null) {
            experiencePanel = new ExperiencePanel();
        }
        return experiencePanel;
    }
}
