package PlayerStats;

import Assets.GUIAssets;
import Entities.Player;
import EventSystem.Events.CombatEvent;
import EventSystem.Events.GameEvent;
import EventSystem.Observer;

import GUI.GUITextPanel;
import Game.GameWindow;
import States.ReversibleState;
import States.State;

import java.awt.*;

public class ExperiencePanel implements Observer {
    //singleton class that manages player experience

    public static final int EXPERIENCE_PANEL_WIDTH = State.BUTTON_W;
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
        if (value >= 1000) {
            double fval = value;
            int i = 0;
            while (fval >= 1000) {
                fval /= 1000.0;
                ++i;
            }
            String toDraw = String.valueOf(fval);
            //cut string if too many digits
            if (toDraw.length() > 6) {
                toDraw = toDraw.substring(0, 6);
            }
            //add units in case the value > 1000
            toDraw += units[i];
            experiencePanel.SetText(toDraw);
        } else {
            experiencePanel.SetText(String.valueOf(value));
        }
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
                State.BUTTON_H);
        experiencePanel.SetColor(GUITextPanel.DEFAULT_GREEN_COLOR);
    }

    private static ExperiencePanel instance = null;
}
