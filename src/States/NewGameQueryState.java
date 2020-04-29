package States;

import Assets.Images.BackgroundAssets;
import Assets.Images.GUIAssets;
import GUI.GUIButton;
import GUI.Text.GUIText;
import Game.GameWindow;
import GameSystems.EventSystem.Events.AudioEvent;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class NewGameQueryState extends ReversibleState {
    public static final int QUESTION_Y = 400;
    public static final int QUESTION_X = 270;
    public static final int OPTIONS_Y = QUESTION_Y + 50;
    public static final int OPTION_X_OFFSET = 25;
    public static final int BACK_OPTION_X = GameWindow.wndDimension.width / 2 - GUIButton.BUTTON_W - OPTION_X_OFFSET;
    public static final int NEW_GAME_OPTION_X = GameWindow.wndDimension.width / 2 + OPTION_X_OFFSET;

    private final GUIText question;

    public NewGameQueryState() {
        super();
        //go back to menu option
        this.allButtons.get(0).SetPosition(BACK_OPTION_X, OPTIONS_Y);
        this.allButtons.get(0).AddActionListener(actionEvent -> {
            StateManager.GetInstance().SetCurrentState(StateManager.StateIndex.MENU_STATE);
        });
        //new game option
        GUIButton new_game = new GUIButton(GUIAssets.new_game_button, GUIAssets.new_game_button_hovered, NEW_GAME_OPTION_X, OPTIONS_Y,
                GUIButton.BUTTON_W, GUIButton.BUTTON_H);
        new_game.AddActionListener(actionEvent -> {
            NotifyAllObservers(AudioEvent.STOP_CURRENT_STATE_MUSIC);
            //TODO: erase all data from the database here
            StateManager.GetInstance().SetCurrentState(StateManager.StateIndex.UPGRADE_STATE);
        });
        allButtons.add(new_game);
        //question
        question = new GUIText("ARE YOU SURE YOU WANT TO START A NEW GAME?", QUESTION_X, QUESTION_Y, 75);
    }

    @Override
    public void Init() {
        super.Init();
        //TODO: if the database is empty -> go straight to UPGRADE_STATE
    }

    @Override
    public void Draw(GameWindow wnd) {
        BufferStrategy bs = wnd.GetCanvas().getBufferStrategy();
        Graphics g = bs.getDrawGraphics();
        g.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());
        g.drawImage(BackgroundAssets.bg_game_dark, 0, 0, null);
        for (GUIButton b : allButtons)
            b.Draw(g);
        question.Draw(g);
        bs.show();
        g.dispose();
    }
}
