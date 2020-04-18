package States;


import Assets.Audio.BackgroundMusic;
import Assets.Audio.AudioManager;
import Assets.Audio.BackgroundMusicAssets;
import Game.GameWindow;

public class StateManager {
    //singleton class that holds all states and manages them
    public static StateManager GetInstance() {
        if (instance == null) {
            instance = new StateManager();
        }
        return instance;
    }

    public void Update() {
        allStates[currentStateIndex.value].Update();
    }

    public void Draw(GameWindow wnd) { allStates[currentStateIndex.value].Draw(wnd); }

    public void SetCurrentState(StateIndex newStateIndex) {
        currentStateIndex = newStateIndex;
        allStates[currentStateIndex.value].Init();
    }

    public State GetCurrentState() {
        return allStates[currentStateIndex.value];
    }
    public StateIndex GetCurrentStateIndex(){return currentStateIndex;}

    private StateManager() {
        allStates = new State[]{
                new MenuState(),
                new OptionsState(),
                new AboutState(),
                new UpgradeState(),
                new PlayState(),
                new LossState(),
                new WinState()
        };
        for(State s:allStates) {
            s.AddObserver(AudioManager.GetInstance());
        }
    }

    private StateIndex currentStateIndex;
    private final State[] allStates;
    private static StateManager instance = null;

    public enum StateIndex {
        //helper enum for more meaningful index names for the state vector
        MENU_STATE(0, BackgroundMusicAssets.menuMusic),
        OPTIONS_STATE(1, BackgroundMusicAssets.menuMusic),
        ABOUT_STATE(2, BackgroundMusicAssets.menuMusic),
        UPGRADE_STATE(3, BackgroundMusicAssets.upgradeMusic),
        GAME_STATE(4, BackgroundMusicAssets.gameMusic),
        LOSS_STATE(5, BackgroundMusicAssets.lossMusic),
        WIN_STATE(6, BackgroundMusicAssets.winMusic);

        public int value;
        public BackgroundMusic bgMusic;

        StateIndex(int i, BackgroundMusic music) {
            this.value = i;
            this.bgMusic=music;
        }
    }
}

