package SQL;

import GameSystems.EventSystem.Events.CombatEvent;
import GameSystems.EventSystem.Events.GameEvent;
import GameSystems.EventSystem.Events.PlayerDataEvent;
import GameSystems.EventSystem.Observer;

/**
 * @brief Class that implements Observer.
 * <p>
 * Its responsibility is to track if the Player instance has modified some of its internal fields.
 * This is done to avoid unnecessary accesses to the database when there is no actual new data.
 */
public class PlayerDataObserver implements Observer {
    private boolean playerDataModified = false;///< Flag to indicate if the player data has been modified and needs to be saved.

    /**
     * Returns the status of the player data.
     *
     * @return A boolean flag that indicates if the player data has been modified since last database save.
     */
    public boolean GetPlayerDataModified() {
        return playerDataModified;
    }

    /**
     * Sets the playerDataModified flag to false.
     */
    public void ResetPlayerDataMOdified() {
        playerDataModified = false;
    }

    @Override
    public void OnNotify(GameEvent e) {
        // All events when player data is modified
        if(e instanceof PlayerDataEvent ||  e == CombatEvent.ENEMY_DEATH){
            playerDataModified = true;
        }
    }
}
