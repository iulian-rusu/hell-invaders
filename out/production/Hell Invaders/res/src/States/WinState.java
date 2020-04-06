package States;

import Assets.BackgroundAssets;

import Assets.FontAssets;
import Assets.GUIAssets;
import EventSystem.Events.AudioEvent;
import GUI.GUIButton;
import GUI.GUIText;
import Game.GameWindow;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;


public class WinState extends State {

    private GUIText winText;

    public WinState(){
        allButtons=new ArrayList<>();
        //back button
        int backH = 110;
        int backW = 400;
        int backX = GameWindow.wndDimension.width / 2 - backW / 2;
        allButtons.add(new GUIButton(GUIAssets.back_button,GUIAssets.back_button_hovered, backX,725, backW, backH));
        allButtons.get(0).AddActionListener(actionEvent -> {
            NotifyAllObservers(AudioEvent.STOP_CURRENT_STATE_MUSIC);
            StateManager.GetInstance().SetCurrentState(StateManager.StateIndex.UPGRADE_STATE);
        });
        //level failure text
        int textX = backX-200;
        int textY = 550;
        winText =new GUIText("YOU WON!",textX,textY,300);
        winText.SetFont(FontAssets.mainFont_bold);
        winText.SetColor(Color.YELLOW);
    }

    @Override
    public void Update(){
        super.Update();
        if(secondCount==5 && frameCount==0) {
            allButtons.get(0).Unblock();
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
        if(secondCount>1){
            winText.Draw(g);
        }
        if(secondCount>5) {
            allButtons.get(0).Draw(g);
        }
        bs.show();
        g.dispose();
    }
}
