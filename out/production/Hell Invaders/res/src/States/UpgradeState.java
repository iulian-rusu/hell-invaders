package States;

import Assets.Images.GUIAssets;
import GameSystems.EventSystem.Events.AudioEvent;
import GUI.GUIButton;
import Game.GameWindow;
import Assets.Images.BackgroundAssets;
import GameSystems.UpgradeSystem.*;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class UpgradeState extends ReversibleState {
    private final ExperiencePanel experiencePanel;
    private final ArrayList<Upgrade> allUpgrades;
    public UpgradeState() {
        //back button events
        allButtons.get(0).AddActionListener(actionEvent -> {
            NotifyAllObservers(AudioEvent.STOP_CURRENT_STATE_MUSIC);
            StateManager.GetInstance().SetCurrentState(StateManager.StateIndex.MENU_STATE);
        });
        //play button
        int playH = 110;
        int playW = 400;
        int playX = GameWindow.wndDimension.width / 2 - playW / 2;
        allButtons.add(new GUIButton(GUIAssets.play_button, GUIAssets.play_button_hovered, playX, 725, playW, playH));
        allButtons.get(1).AddActionListener(actionEvent -> {
            NotifyAllObservers(AudioEvent.STOP_CURRENT_STATE_MUSIC);
            StateManager.GetInstance().SetCurrentState(StateManager.StateIndex.GAME_STATE);
        });
        //upgrade and experience system
        experiencePanel=ExperiencePanel.GetInstance();
        allUpgrades=new ArrayList<>(4);
        allUpgrades.add(new DamageUpgrade(85,150));
        allUpgrades.add(new ProjectileNumberUpgrade(GameWindow.wndDimension.width-85-Upgrade.PANEL_WIDTH-Upgrade.ICON_WITDH,150));
        allUpgrades.add(new CritUpgrade(85,150+Upgrade.ICON_HEIGHT+150));
        allUpgrades.add(new SpellUpgrade(GameWindow.wndDimension.width-85-Upgrade.PANEL_WIDTH-Upgrade.ICON_WITDH,
                150+Upgrade.ICON_HEIGHT+150));
        //add upgrade buttons to all buttons list
        for(Upgrade u:allUpgrades){
            allButtons.add(u.GetButtonHandle());
        }
    }

    @Override
    public void Init() {
        super.Init();
        ExperiencePanel.GetInstance().UpdateValue();
        NotifyAllObservers(AudioEvent.PLAY_CURRENT_STATE_MUSIC);
    }

    @Override
    public void Draw(GameWindow wnd) {
        BufferStrategy bs = wnd.GetCanvas().getBufferStrategy();
        Graphics g = bs.getDrawGraphics();
        g.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());
        g.drawImage(BackgroundAssets.bg_game_dark, 0, 0, null);
        for (GUIButton b : allButtons) {
            b.Draw(g);
        }
        for(Upgrade u:allUpgrades){
            u.Draw(g);
        }
        experiencePanel.Draw(g);
        bs.show();
        g.dispose();
    }
}
