package GameSystems.UpgradeSystem;

import Assets.Images.GUIAssets;
import Entities.Player;
import GUI.GUIButton;
import GameSystems.EventSystem.Events.CombatEvent;
import GameSystems.EventSystem.Events.GameEvent;
import GameSystems.EventSystem.Observer;

import GUI.GUITextPanel;
import Game.GameWindow;
import States.ReversibleState;
import GameSystems.StatsSystem.LargeNumberHandler;

import java.awt.*;

public class ExperiencePanel implements Observer {
    //singleton class that manages player experience
    public static final int EXPERIENCE_PANEL_WIDTH = GUIButton.BUTTON_W;
    public static final int EXPERIENCE_PANEL_X = GameWindow.wndDimension.width - ReversibleState.BACK_BUTTON_X - EXPERIENCE_PANEL_WIDTH;

    private final GUITextPanel experiencePanel;
    private final String[] units = {"", " K", " M", " G", " T", " P", " E"};

    public static ExperiencePanel GetInstance() {
        if (instance == null) {
            instance = new ExperiencePanel();
        }
        return instance;
    }

    public void Draw(Graphics g) {
        experiencePanel.Draw(g);
    }

    public void UpdateValue() {
        long value = Player.GetInstance().GetExperience();
        experiencePanel.SetText(LargeNumberHandler.ParseLongInt(value));
    }

    @Override
    public void OnNotify(GameEvent e) {
        //experience bar updated in case of monster death or upgrade event
        if (e == CombatEvent.ENEMY_DEATH || e.GetType() == GameEvent.GameEventType.UpgradeEvent) {
            UpdateValue();
        }
    }

    private ExperiencePanel() {
        experiencePanel = new GUITextPanel("0",
                GUIAssets.green_button,
                EXPERIENCE_PANEL_X,
                ReversibleState.BACK_BUTTON_Y,
                EXPERIENCE_PANEL_WIDTH,
                GUIButton.BUTTON_H);
        experiencePanel.SetColor(GUITextPanel.DEFAULT_GREEN_COLOR);
    }

    private static ExperiencePanel instance = null;
}
