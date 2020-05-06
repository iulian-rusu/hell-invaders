package States;

import Assets.Audio.BackgroundMusic;
import Assets.Audio.AudioManager;
import Assets.Audio.BackgroundMusicAssets;

import java.awt.*;

/*! \class StateManager
    \brief Singleton class that manages and holds all states.

 */
public class StateManager {

    /*! \fn public static StateManager GetInstance()
        \brief Static methods used to access the unique StateManager instance.
     */
    public static StateManager GetInstance() {
        if (instance == null) {
            instance = new StateManager();
        }
        return instance;
    }

    /*! \fn public void Update()
        \brief Calls Update() in the current game state.
    */
    public void Update() {
        allStates[currentStateIndex.value].Update();
    }

    /*! \fn public void Draw(Graphics2D g2d)
        \brief Calls Draw(g2d) in the current game state.
        \param g2d Java Graphics2D object that draws to the screen.
    */
    public void Draw(Graphics2D g2d) { allStates[currentStateIndex.value].Draw(g2d); }

    /*! \fn public void SetCurrentState(StateIndex newStateIndex)
        \brief Changes the current game state.
        \param newStateIndex The index of the new state to be changed to.
    */
    public void SetCurrentState(StateIndex newStateIndex) {
        currentStateIndex = newStateIndex;
        allStates[currentStateIndex.value].Init();
    }

    /*! \fn public State GetCurrentState()
         \brief Returns a reference to the current game state.
    */
    public State GetCurrentState() {
        return allStates[currentStateIndex.value];
    }

    /*! \fn public StateIndex GetCurrentStateIndex()
        \brief Returns a reference to the current game state index.
   */
    public StateIndex GetCurrentStateIndex(){return currentStateIndex;}

    /*! \fn  private StateManager()
        \brief Private constructor of the singleton class. Constructs all game states and stores them.
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
        for(State s:allStates) {
            s.AddObserver(AudioManager.GetInstance());
        }
    }

    private StateIndex currentStateIndex;///< Stores the index of the current state
    private final State[] allStates;///< Stores all game states.
    private static StateManager instance = null;///< Stores a reference to the singleton object.


    /*! \enum StateIndex
        \brief Helper nested enum class to associate each state with its index in the state vector.
     */
    public enum StateIndex {
        MENU_STATE(0, BackgroundMusicAssets.menuMusic),
        OPTIONS_STATE(1, BackgroundMusicAssets.menuMusic),
        ABOUT_STATE(2, BackgroundMusicAssets.menuMusic),
        UPGRADE_STATE(3, BackgroundMusicAssets.upgradeMusic),
        GAME_STATE(4, BackgroundMusicAssets.gameMusic),
        LOSS_STATE(5, BackgroundMusicAssets.lossMusic),
        WIN_STATE(6, BackgroundMusicAssets.winMusic),
        NEW_GAME_QUERY_STATE(7,BackgroundMusicAssets.menuMusic);

        public int value;///< The actual value of the state index.
        public BackgroundMusic bgMusic;///< The background music associated with each game state.

        StateIndex(int i, BackgroundMusic music) {
            this.value = i;
            this.bgMusic=music;
        }
    }
}

