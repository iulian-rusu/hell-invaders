package States;

import Assets.Audio.AudioManager;

import java.awt.*;

/**
 *  @brief Singleton class that manages and holds all states.
 */
public class StateManager {

    /**
     * Static methods used to access the unique StateManager instance.
     * @return The unique instance of the StateManager class.
     */
    public static StateManager GetInstance() {
        if (instance == null) {
            instance = new StateManager();
        }
        return instance;
    }

    /**
     * Calls Update() in the current game state.
     */
    public void Update() {
        allStates[currentStateIndex.value].Update();
    }

    /**
     * Calls Draw(g2d) in the current game state.
     * @param g2d Java Graphics2D object that draws to the screen.
     */
    public void Draw(Graphics2D g2d) {
        allStates[currentStateIndex.value].Draw(g2d);
    }

    /**
     * Changes the current game state.
     * @param newStateIndex The index of the new state to be changed to.
     */
    public void SetCurrentState(StateIndex newStateIndex) {
        currentStateIndex = newStateIndex;
        allStates[currentStateIndex.value].Init();
    }

    /**
     * Returns a reference to the current game state.
     * @return The current State object.
     */
    public State GetCurrentState() {
        return allStates[currentStateIndex.value];
    }

    /**
     * Returns a reference to the current game state index.
     * @return The current StateIndex object.
     */
    public StateIndex GetCurrentStateIndex() {
        return currentStateIndex;
    }

    /**
     * Private constructor of the singleton class. Constructs all game states and stores them.
     */
    private StateManager() {
        allStates = new State[]{
                new MenuState(),
                new OptionsState(),
                new AboutState(),
                new UpgradeState(),
                new PlayState(),
                new LossState(),
                new WinState(),
                new NewGameQueryState()
        };
        for (State s : allStates) {
            s.AddObserver(AudioManager.GetInstance());
        }
    }

    private StateIndex currentStateIndex;///< Stores the index of the current state
    private final State[] allStates;///< Stores all game states.
    private static StateManager instance = null;///< Stores a reference to the singleton object.
}

