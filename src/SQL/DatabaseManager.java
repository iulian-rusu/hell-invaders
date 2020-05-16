package SQL;

import Entities.CollidableEntities.Projectiles.ProjectileType;
import Entities.Player;
import Game.GlobalReferences;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @brief Manages access to the SQLite database. Allows the loading of previous game data and settings.
 */
public class DatabaseManager {
    public static final String DB_NAME = "game_stats.db";///< The name of the database.
    public static final String PLAYER_DATA_NAME = "PlayerData";///< The name of the table containing player-retated data.
    public static final String GAME_SETTINGS_NAME = "GameSettings";///< The name of the table containing game settings.

    private static Connection connection;///< The currently open connection to the database.
    private static Statement statement;///< The currently open statement.
    private static boolean isConnected = false;///< Flag to indicate if the manager is currently conencted to the database.
    private static final PlayerDataObserver playerDataObserver = new PlayerDataObserver();
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");///< Date format for logging.

    /**
     * Constructor without parameters.
     * <p>
     * Creates the database if it cannot be found. Stores default values for game settings if none are there yet.
     */
    public static void CreateDatabase() {
        try {
            // Create player data table if it doesn't exist yet
            String createPlayerTable = "CREATE TABLE IF NOT EXISTS " + PLAYER_DATA_NAME +
                    "(Level INT NOT NULL," +
                    " Experience INT NOT NULL," +
                    " ProjectileDamage INT NOT NULL," +
                    " ProjectileDamageLevel INT NOT NULL," +
                    " NumProjectiles INT NOT NULL," +
                    " CritChance INT NOT NULL," +
                    " CurrentProjectile CHAR(20) NOT NULL)";
            Connect();
            statement.execute(createPlayerTable);
            Disconnect();

            // Create game settings table if it doesn't exist yet
            String createGameTable = "CREATE TABLE IF NOT EXISTS " + GAME_SETTINGS_NAME +
                    "(Difficulty INT NOT NULL," +
                    " IsFullscreen INT NOT NULL," +
                    "UNIQUE(Difficulty, IsFullscreen))";
            Connect();
            statement.execute(createGameTable);
            Disconnect();

            if (IsEmpty(GAME_SETTINGS_NAME)) {
                // Load default game settings if not loaded before
                String initGameTable = "INSERT INTO " + GAME_SETTINGS_NAME +
                        "(Difficulty, IsFullscreen) VALUES(1, 1)";
                Connect();
                statement.execute(initGameTable);
                Disconnect();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            Disconnect();
        }
    }

    /**
     * Returns a reference to the player data observer.
     *
     * @return A PlayerDataObserver object.
     * @see GameSystems.EventSystem.Observer
     */
    public static PlayerDataObserver GetPlayerDataObserverHandle() {
        return playerDataObserver;
    }

    /**
     * Returns a boolean indicating whether a table from the database is empty.
     *
     * @param tableName A String containing the table name.
     */
    public static boolean IsEmpty(String tableName) {
        try {
            Connect();
            ResultSet result = statement.executeQuery("SELECT COUNT(*) FROM " + tableName);
            return result.getInt("COUNT(*)") == 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        } finally {
            Disconnect();
        }
    }

    /**
     * Stores the current player data into the database. If some data is already there, it will be overwritten.
     * <p>
     * Player data is automatically saved when:
     * <ul>
     *     <li> A new game is started. </li>
     *     <li> The user returns from the upgrade menu to the main menu </li>
     * </ul>
     * <p>
     * This method will only save the data if the playerDataModified flag is set to true.
     */
    public static void SavePlayerData() {
        if (!playerDataObserver.GetPlayerDataModified())
            return;
        try {
            Player player = GlobalReferences.GetPlayer();
            int level = player.GetLevel();
            long experience = player.GetExperience();
            long projectileDamage = player.GetProjectileDamage();
            int projectileDamageLevel = player.GetProjectileDamageLevel();
            int numProjectiles = player.GetNumProjectiles();
            int critChance = player.GetCritChance();
            String currentProjectile = player.GetProjectileType().toString();

            String command;
            if (IsEmpty(PLAYER_DATA_NAME)) {
                command = String.format("INSERT INTO " + PLAYER_DATA_NAME +
                                " (Level, Experience, ProjectileDamage, ProjectileDamageLevel, NumProjectiles, CritChance, CurrentProjectile) " +
                                "VALUES (%d, %d, %d, %d, %d, %d, '%s')",
                        level, experience, projectileDamage, projectileDamageLevel, numProjectiles, critChance, currentProjectile);
            } else {
                command = String.format("UPDATE " + PLAYER_DATA_NAME +
                                " SET Level = %d, " +
                                "Experience =  %d," +
                                "ProjectileDamage = %d, " +
                                "ProjectileDamageLevel = %d, " +
                                "NumProjectiles = %d, " +
                                "CritChance = %d, " +
                                "CurrentProjectile = '%s'",
                        level, experience, projectileDamage, projectileDamageLevel, numProjectiles, critChance, currentProjectile);
            }
            Connect();
            statement.execute(command);
            Disconnect();
            playerDataObserver.ResetPlayerDataMOdified();

            LogMessage("Saved player data.");
        } catch (SQLException | NullPointerException throwables) {
            throwables.printStackTrace();
        } finally {
            Disconnect();
        }
    }

    /**
     * Deletes all player data from the database.
     * <p>
     * Player data is cleared when a new game is started.
     */
    public static void ClearPlayerData() {
        try {
            if (!IsEmpty(PLAYER_DATA_NAME)) {
                Connect();
                statement.execute("DELETE FROM " + PLAYER_DATA_NAME);

                LogMessage("Cleared player data.");
            }
        } catch (SQLException | NullPointerException throwables) {
            throwables.printStackTrace();
        } finally {
            Disconnect();
        }
    }

    /**
     * Loads player data from the database and updates the Player instance.
     * <p>
     * Player data is loaded immediately after the Player object is created.
     */
    public static void LoadPlayerData() {
        try {
            Connect();
            ResultSet playerData = statement.executeQuery("SELECT * FROM " + PLAYER_DATA_NAME);
            int level = playerData.getInt("Level");
            long experience = playerData.getLong("Experience");
            long projectileDamage = playerData.getLong("ProjectileDamage");
            int projectileDamageLevel = playerData.getInt("ProjectileDamageLevel");
            int numProjectiles = playerData.getInt("NumProjectiles");
            int critChance = playerData.getInt("CritChance");
            String currentProjectile = playerData.getString("CurrentProjectile");

            Player player = GlobalReferences.GetPlayer();
            player.SetLevel(level);
            player.SetExperience(experience);
            player.SetProjectileDamage(projectileDamage, projectileDamageLevel);
            player.SetNumProjectiles(numProjectiles);
            player.SetCritChance(critChance);
            player.SetProjectileType(ProjectileType.valueOf(currentProjectile));

            LogMessage("Loaded player data.");
        } catch (SQLException | NullPointerException throwables) {
            throwables.printStackTrace();
        } finally {
            Disconnect();
        }
    }

    /**
     * Saves the value specified into the field of the game settings table.
     * <p>
     * Game data is automatically saved after leaving the options menu.
     *
     * @param field The name of the field to be updated.
     * @param value The value to be inserted.
     */
    public static void SaveGameData(String field, int value) {
        try {
            Connect();
            statement.execute("UPDATE " + GAME_SETTINGS_NAME + " SET " + field + " = " + value);
        } catch (SQLException | NullPointerException throwables) {
            throwables.printStackTrace();
        } finally {
            Disconnect();
        }
    }

    /**
     * Retrieves and returns data from the specified field in the game table.
     * <p>
     * Game date is loaded when the game is initialized.
     *
     * @param field The name of the vield to retrieve from.
     * @return An integer represeting the value from the table.
     */
    public static int GetGameData(String field) {
        try {
            Connect();
            return statement.executeQuery("SELECT * FROM " + GAME_SETTINGS_NAME).getInt(field);
        } catch (SQLException | NullPointerException throwables) {
            throwables.printStackTrace();
            return 1;
        } finally {
            Disconnect();
        }
    }

    /**
     * Creates a new connection to the database, if not already connected.
     */
    private static void Connect() {
        if (isConnected)
            return;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_NAME);
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            isConnected = true;
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Closes the currently active connection to the database, if it exists.
     */
    private static void Disconnect() {
        if (!isConnected)
            return;
        try {
            connection.commit();
            connection.close();
            isConnected = false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Prints a log message.
     *
     * @param msg A String containing the message to be logged.
     */
    public static void LogMessage(String msg){
        LocalDateTime now = LocalDateTime.now();
        System.out.println(String.format("[DATABASE MANAGER @ %s]: %s", dateFormat.format(now), msg));
    }
}
