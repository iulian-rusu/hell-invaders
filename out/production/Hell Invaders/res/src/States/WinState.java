package States;

import Assets.BackgroundAssets;

import Assets.GUIAssets;
import Audio.Audio;
import Audio.AudioManager;
import Audio.BackgroundMusicAssets;
import EventSystem.Events.AudioEvent;
import GUI.GUIButton;
import Game.GameWindow;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;


public class WinState extends State {
    private final int backW = 400;
    private final int backH = 110;
    private final int backX = GameWindow.wndDimension.width / 2 - backW / 2;

    public WinState(){
        allButtons=new ArrayList<>();
        //back button
        allButtons.add(new GUIButton(GUIAssets.back_button,GUIAssets.back_button_hovered,backX,725,backW,backH));
        allButtons.get(0).AddActionListener(actionEvent -> {
            NotifyAllObservers(AudioEvent.STOP_CURRENT_STATE_MUSIC);
            StateManager.GetInstance().SetCurrentState(StateManager.StateIndex.UPGRADE_STATE);
        });
    }

    @Override
    public void Update(){
        super.Update();
        if(secondCount==5 && frameCount==0) {
            allButtons.get(0).Unblock();
            System.out.println("Unblocked");
        }
    }

    @Override
    public void Init() {
        super.Init();
        NotifyAllObservers(AudioEvent.PLAY_CURRENT_STATE_MUSIC);
        allButtons.get(0).Block();
    }

    @Override
    public void Draw(GameWindow wnd) {
        BufferStrategy bs = wnd.GetCanvas().getBufferStrategy();
        Graphics g = bs.getDrawGraphics();
        g.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());
        g.drawImage(BackgroundAssets.bg_win, 0, 0, null);
        if(secondCount>5) {
            allButtons.get(0).Draw(g);
        }
        bs.show();
        g.dispose();
    }
}
