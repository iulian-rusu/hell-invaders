package States;

import Audio.AudioManager;
import Audio.BackgroundMusic;
import GUI.GUIButton;
import GUI.GUIText;
import Game.GameWindow;
import Assets.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;

public class MenuState extends State {

    private boolean logoColorflag = true;
    private GUIButton[] allButtons;
    private GUIText bottomText;
    private final int buttonW = 200;
    private final int buttonH = 55;
    private final int menuY = Toolkit.getDefaultToolkit().getScreenSize().height / 2;
    private final int menuX = (Toolkit.getDefaultToolkit().getScreenSize().width - buttonW) / 2;
    private final int buttonSpacing = 65;

    public MenuState() {
        allButtons = new GUIButton[]{
                new GUIButton(GUIAssets.new_game_button, GUIAssets.new_game_button_hovered, menuX, menuY, buttonW, buttonH),
                new GUIButton(GUIAssets.resume_button_blocked, GUIAssets.resume_button_hovered, menuX, menuY + buttonSpacing, buttonW, buttonH),
                new GUIButton(GUIAssets.options_button, GUIAssets.options_button_hovered, menuX, menuY + 2 * buttonSpacing, buttonW, buttonH),
                new GUIButton(GUIAssets.stats_button, GUIAssets.stats_button_hovered, menuX, menuY + 3 * buttonSpacing, buttonW, buttonH),
                new GUIButton(GUIAssets.quit_button, GUIAssets.quit_button_hovered, menuX, menuY + 4 * buttonSpacing, buttonW, buttonH)
        };
        allButtons[1].Block();//resume button is blocked
        //state transition events
        allButtons[0].AddActionListener(actionEvent -> {StateManager.GetInstance().SetCurrentState(StateManager.StateIndex.UPGRADE_STATE);});
        allButtons[1].AddActionListener(actionEvent -> {StateManager.GetInstance().SetCurrentState(StateManager.StateIndex.UPGRADE_STATE);});
        allButtons[2].AddActionListener(actionEvent -> {StateManager.GetInstance().SetCurrentState(StateManager.StateIndex.OPTIONS_STATE);});
        allButtons[3].AddActionListener(actionEvent -> {StateManager.GetInstance().SetCurrentState(StateManager.StateIndex.STATS_STATE);});
        allButtons[4].AddActionListener(actionEvent -> {System.exit(0);});
        //music events
        allButtons[0].AddActionListener(actionEvent -> {AudioManager.GetInstance().Stop(BackgroundMusic.menuMusic);});
        allButtons[1].AddActionListener(actionEvent -> {AudioManager.GetInstance().Stop(BackgroundMusic.menuMusic);});
        allButtons[4].AddActionListener(actionEvent -> {AudioManager.GetInstance().Stop(BackgroundMusic.menuMusic);});

        bottomText=new GUIText("Iulian Rusu, 2020. All Rights Reserved.",
                5, Toolkit.getDefaultToolkit().getScreenSize().height-5,25f);
        bottomText.SetColor(Color.GRAY);
    }

    @Override
    public void Init() {
        super.Init();
        AudioManager.GetInstance().Play(BackgroundMusic.menuMusic);
        for(GUIButton b:allButtons) {
            b.Init();
        }

        allButtons[1].Block();
        logoColorflag = true;
    }

    @Override
    public void Update() {
        super.Update();
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

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        for (GUIButton b : allButtons)
            b.mouseMoved(mouseEvent);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        for (GUIButton b : allButtons)
            b.mousePressed(mouseEvent);
    }

}
