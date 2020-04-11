package States;


import EventSystem.Events.AudioEvent;
import GUI.GUIButton;
import GUI.GUIText;
import Game.GameWindow;
import Assets.*;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class MenuState extends State {

    private boolean logoColorflag = true;
    private final GUIText bottomText;

    public MenuState() {
        final int menuY = GameWindow.wndDimension.height / 2-20;
        final int menuX =( GameWindow.wndDimension.width - BUTTON_W) / 2;
        final int buttonSpacing = 75;

        allButtons = new ArrayList<>(5);
        allButtons.add(new GUIButton(GUIAssets.new_game_button, GUIAssets.new_game_button_hovered, menuX, menuY, BUTTON_W, BUTTON_H));
        allButtons.add(new GUIButton(GUIAssets.resume_button_blocked, GUIAssets.resume_button_hovered, menuX, menuY + buttonSpacing, BUTTON_W, BUTTON_H));
        allButtons.add(new GUIButton(GUIAssets.options_button, GUIAssets.options_button_hovered, menuX, menuY + 2 * buttonSpacing, BUTTON_W, BUTTON_H));
        allButtons.add(new GUIButton(GUIAssets.stats_button, GUIAssets.stats_button_hovered, menuX, menuY + 3 * buttonSpacing, BUTTON_W, BUTTON_H));
        allButtons.add(new GUIButton(GUIAssets.quit_button, GUIAssets.quit_button_hovered, menuX, menuY + 4 * buttonSpacing, BUTTON_W, BUTTON_H));
        allButtons.get(1).Block();
        //state transition events
        //play
        allButtons.get(0).AddActionListener(actionEvent -> {
            NotifyAllObservers(AudioEvent.STOP_CURRENT_STATE_MUSIC);
            //this will also clear everything from the database before going to UPGRADE_STATE
            StateManager.GetInstance().SetCurrentState(StateManager.StateIndex.UPGRADE_STATE);}
        );
        //resume
        allButtons.get(1).AddActionListener(actionEvent ->{
            NotifyAllObservers(AudioEvent.STOP_CURRENT_STATE_MUSIC);
            //this goes to UPGRADE_STATE without clearing player data
            StateManager.GetInstance().SetCurrentState(StateManager.StateIndex.UPGRADE_STATE);
        });
        //options
        allButtons.get(2).AddActionListener(actionEvent -> StateManager.GetInstance().SetCurrentState(StateManager.StateIndex.OPTIONS_STATE));
        //stats
        allButtons.get(3).AddActionListener(actionEvent -> StateManager.GetInstance().SetCurrentState(StateManager.StateIndex.STATS_STATE));
        //quit
        allButtons.get(4).AddActionListener(actionEvent -> {
            NotifyAllObservers(AudioEvent.STOP_CURRENT_STATE_MUSIC);
            System.exit(0);
        });
        bottomText = new GUIText("COPYRIGHT © 2020 IULIAN RUSU. ALL RIGHTS RESERVED.",
                5, GameWindow.wndDimension.height - 5, 25f);
        bottomText.SetColor(Color.GRAY);
    }

    @Override
    public void Init() {
        super.Init();
        NotifyAllObservers(AudioEvent.PLAY_CURRENT_STATE_MUSIC);
        allButtons.get(1).Block();
        logoColorflag = true;
    }

    @Override
    public void Draw(GameWindow wnd) {
        BufferStrategy bs = wnd.GetCanvas().getBufferStrategy();
        Graphics g = bs.getDrawGraphics();
        g.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());
        if (frameCount == 59) {
            logoColorflag = !logoColorflag;
        }
        if (logoColorflag) {
            g.drawImage(BackgroundAssets.bg_menu0, 0, 0, null);
        } else {
            g.drawImage(BackgroundAssets.bg_menu1, 0, 0, null);
        }
        for (GUIButton b : allButtons)
            b.Draw(g);
        bottomText.Draw(g);
        bs.show();
        g.dispose();
    }
}
