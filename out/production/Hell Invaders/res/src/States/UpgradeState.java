package States;

import Assets.Images.GUIAssets;
import Entities.Player;
import GUI.GUIText;
import Game.Game;
import GameSystems.EventSystem.Events.AudioEvent;
import GUI.GUIButton;
import Game.GameWindow;
import Assets.Images.BackgroundAssets;
import GameSystems.UpgradeSystem.*;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class UpgradeState extends ReversibleState {
    //public static because it's shared with GameState class
    public static ArrayList<GUIText> infoText;

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
        //various text for player info
        int infoTextSize = 100;
        infoText = new ArrayList<>(2);
        infoText.add(new GUIText("EASY", GameWindow.wndDimension.width - 270,
                Player.MANABAR_Y+Player.HEALTHBAR_HEIGHT, infoTextSize));
        infoText.add(new GUIText("DAY 1", GameWindow.wndDimension.width / 2 - 100,
                BACK_BUTTON_Y + GUIButton.BUTTON_H - 10, infoTextSize));
    }

    @Override
    public void Init() {
        super.Init();
        for(Upgrade upgrade:allUpgrades){
            upgrade.CheckIfBlocked();
        }
        ExperiencePanel.GetInstance().UpdateValue();
        NotifyAllObservers(AudioEvent.PLAY_CURRENT_STATE_MUSIC);
        //init info text
        InitText();
    }

    void InitText() {
        //init difficulty text
        String d = "EASY";
        Color c = Color.GREEN;
        switch (Game.DIFFICULTY) {
            case 1:
                break;
            case 2:
                d = "MEDIUM";
                c = Color.YELLOW;
                break;
            case 3:
                d = "HARD";
                c = Color.RED;
                break;
        }
        infoText.get(0).SetText(d);
        infoText.get(0).SetColor(c);
        //init level text
        infoText.get(1).SetText("DAY " + Player.GetInstance().GetLevel());
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
        for(GUIText t:infoText){
            t.Draw(g);
        }
        experiencePanel.Draw(g);
        bs.show();
        g.dispose();
    }
}
