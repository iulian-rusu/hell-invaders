package States;

import Assets.Audio.BackgroundMusic;
import Assets.Audio.BackgroundMusicAssets;

/**
 * @brief Maps each game state to its index in the state vector and its background music.
 */
public enum StateIndex {
    MENU_STATE(0, BackgroundMusicAssets.menuMusic),
    OPTIONS_STATE(1, BackgroundMusicAssets.menuMusic),
    ABOUT_STATE(2, BackgroundMusicAssets.menuMusic),
    UPGRADE_STATE(3, BackgroundMusicAssets.upgradeMusic),
    PLAY_STATE(4, BackgroundMusicAssets.gameMusic),
    LOSS_STATE(5, BackgroundMusicAssets.lossMusic),
    WIN_STATE(6, BackgroundMusicAssets.winMusic),
    NEW_GAME_QUERY_STATE(7, BackgroundMusicAssets.menuMusic);

    public int value;///< The actual value of the state index.
    public BackgroundMusic bgMusic;///< The background music associated with each game state.

    /**
     * Constructor with parameters.
     *
     * @param i     The index of the state in the state vector.
     * @param music The background music of the state.
     */
    StateIndex(int i, BackgroundMusic music) {
        this.value = i;
        this.bgMusic = music;
    }
}
