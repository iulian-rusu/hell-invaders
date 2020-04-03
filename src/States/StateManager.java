package States;

import Audio.AudioManager;
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
        allStates[currentStateIndex.GetValue()].Update();
    }

    public void Draw(GameWindow wnd) {
        allStates[currentStateIndex.GetValue()].Draw(wnd);
    }

    public void SetCurrentState(StateIndex newStateIndex) {
        currentStateIndex = newStateIndex;
        allStates[currentStateIndex.GetValue()].Init();
    }

    public State GetCurrentState() {
        return allStates[currentStateIndex.GetValue()];
    }

    private StateManager() {
        allStates = new State[]{
                new MenuState(),
                new OptionsState(),
                new StatsState(),
                new UpgradeState(),
                new GameState(),
                new LossState(),
                new WinState()
        };
        allStates[StateIndex.GAME_STATE.GetValue()].AddObserver(AudioManager.GetInstance());
        SetCurrentState(StateIndex.MENU_STATE);
    }

    private StateIndex currentStateIndex;
    private State[] allStates;
    private static StateManager instance = null;

    public enum StateIndex {
        //helper enum for more meaningful index names for the state vector
        MENU_STATE(0),
        OPTIONS_STATE(1),
        STATS_STATE(2),
        UPGRADE_STATE(3),
        GAME_STATE(4),
        LOSS_STATE(5),
        WIN_STATE(6);

        private int value;

        public int GetValue() {
            return value;
        }

        StateIndex(int i) {
            this.value = i;
        }
    }
}

