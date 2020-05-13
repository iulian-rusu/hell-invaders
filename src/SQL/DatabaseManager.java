package SQL;

import Entities.Player;
import Game.GlobalReferences;

import java.sql.*;

/**
 * @brief Manages access to the SQLite database. Allows the loading of previous game data and settings.
 */
public class DatabaseManager {
    public static final String DB_NAME = "game_stats.db";///< The name of the game databse.
    public static final String PLAYER_DATA_NAME = "PlayerData";///< The name of the table containing player-retalted data.
    public static final String GAME_SETTINGS_NAME = "GameSettings";///< The name of the table containing game settings.

    private static Connection connection;///< The currently open connection to the database.
    private static Statement statement;///< The currently open statement.
    private static boolean isConnected = false;///< Flag to indicate current state.

    /**
     * Constructor without parameters.
     * Creates the database if it cannot be found. Creates tables and stores default values if none are already there.
     */
    public static void CreateDatabase() {
        try {
            // Create player data table if it doesn't exist yet
            String createPlayerTable = "CREATE TABLE IF NOT EXISTS " + PLAYER_DATA_NAME +
                    "(Level INT NOT NULL," +
                    " Experience INT NOT NULL, " +
                    " ProjectileDamage INT NOT NULL, " +
                    " ProjectileDamageLevel INT NOT NULL, " +
                    " NumProjectiles INT NOT NULL, " +
                    " CritChance INT NOT NULL, " +
                    " CurrentProjectile INT NOT NULL)";
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
     */
    public static void SavePlayerData() {
        try {
            Player player = GlobalReferences.GetPlayer();
            int level = player.GetLevel();
            long experience = player.GetExperience();
            long projectileDamage = player.GetProjectileDamage();
            int projectileDamageLevel = player.GetProjectileDamageLevel();
            int numProjectiles = player.GetNumProjectiles();
            int critChance = player.GetCritChance();
            int currentProjectile = player.GetProjectileType();

            String command;
            if (IsEmpty(PLAYER_DATA_NAME)) {
                command = String.format("INSERT INTO " + PLAYER_DATA_NAME +
                                " (Level, Experience, ProjectileDamage, ProjectileDamageLevel, NumProjectiles, CritChance, CurrentProjectile) " +
                                "VALUES (%d, %d, %d, %d, %d, %d, %d)",
                        level, experience, projectileDamage, projectileDamageLevel, numProjectiles, critChance, currentProjectile);
            } else {
                command = String.format("UPDATE " + PLAYER_DATA_NAME +
                                " SET Level = %d, " +
                                "Experience =  %d," +
                                "ProjectileDamage = %d, " +
                                "ProjectileDamageLevel = %d, " +
                                "NumProjectiles = %d, " +
                                "CritChance = %d, " +
                                "CurrentProjectile = %d",
                        level, experience, projectileDamage, projectileDamageLevel, numProjectiles, critChance, currentProjectile);
            }
            Connect();
            statement.execute(command);
            Disconnect();
        } catch (SQLException | NullPointerException throwables) {
            throwables.printStackTrace();
        } finally {
            Disconnect();
        }
    }

    /**
     * Deletes all player data from the database.
     */
    public static void ClearPlayerData() {
        try {
            if (!IsEmpty(PLAYER_DATA_NAME)) {
                Connect();
                statement.execute("DELETE FROM " + PLAYER_DATA_NAME);
            }
        } catch (SQLException | NullPointerException throwables) {
            throwables.printStackTrace();
        } finally {
            Disconnect();
        }
    }

    /**
     * Loads player data from the database and updates the Player instance.
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
            int currentProjectile = playerData.getInt("CurrentProjectile");

            Player player = GlobalReferences.GetPlayer();
            player.SetLevel(level);
            player.SetExperience(experience);
            player.SetProjectileDamage(projectileDamage, projectileDamageLevel);
            player.SetNumProjectiles(numProjectiles);
            player.SetCritChance(critChance);
            player.SetProjectileType(currentProjectile);

        } catch (SQLException | NullPointerException throwables) {
            throwables.printStackTrace();
        } finally {
            Disconnect();
        }
    }

    /**
     * Saves the value specified into the field of the game settings table.
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
     * Creates a new connection to the database if not already connected.
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
     * Closses the currently active connection to the database.
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
}
