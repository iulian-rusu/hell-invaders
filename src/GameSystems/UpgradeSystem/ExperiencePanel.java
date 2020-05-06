package GameSystems.UpgradeSystem;

import Assets.Images.GUIAssets;
import GUI.GUIButton;
import Game.GlobalReferences;
import GameSystems.EventSystem.Events.CombatEvent;
import GameSystems.EventSystem.Events.GameEvent;
import GameSystems.EventSystem.Events.UpgradeEvent;
import GameSystems.EventSystem.Observer;

import GUI.Text.GUITextPanel;
import Game.GameWindow;
import States.ReversibleState;
import GameSystems.NumberSystem.LargeNumberHandler;

import java.awt.*;

public class ExperiencePanel implements Observer {
    //singleton class that manages player experience
    public static final int EXPERIENCE_PANEL_WIDTH = GUIButton.BUTTON_W;
    public static final int EXPERIENCE_PANEL_X = GameWindow.screenDimension.width - ReversibleState.BACK_BUTTON_X - EXPERIENCE_PANEL_WIDTH;

    private final GUITextPanel experiencePanel;

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
        long value = GlobalReferences.player.GetExperience();
        experiencePanel.SetText(LargeNumberHandler.ParseLongInt(value)+" XP");
    }

    @Override
    public void OnNotify(GameEvent e) {
        //experience bar updated in case of monster death or upgrade event
        if (e == CombatEvent.ENEMY_DEATH || e instanceof UpgradeEvent) {
            UpdateValue();
        }
    }

    private ExperiencePanel() {
        experiencePanel = new GUITextPanel("0 XP",
                GUIAssets.green_button,
                EXPERIENCE_PANEL_X,
                ReversibleState.BACK_BUTTON_Y,
                EXPERIENCE_PANEL_WIDTH,
                GUIButton.BUTTON_H);
        experiencePanel.SetColor(GUITextPanel.DEFAULT_GREEN_COLOR);
    }

    private static ExperiencePanel instance = null;
}
