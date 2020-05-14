package GameSystems.UpgradeSystem;

import Assets.Images.GUIAssets;
import GUI.GUIButton;
import GUI.Text.GUITextPanel;
import Game.GameWindow;
import Game.GlobalReferences;
import GameSystems.EventSystem.Events.CombatEvent;
import GameSystems.EventSystem.Events.GameEvent;
import GameSystems.EventSystem.Events.UpgradeEvent;
import GameSystems.EventSystem.Observer;
import GameSystems.NumberSystem.LargeNumberHandler;
import States.ReversibleState;

import java.awt.*;

/**
 * @brief Provides a container for the experience held by the player.
 */
public class ExperiencePanel implements Observer {
    public static final int EXPERIENCE_PANEL_WIDTH = GUIButton.BUTTON_W;///< The default widht of the panel.
    public static final int EXPERIENCE_PANEL_X = GameWindow.SCREEN_DIMENSION.width -
            ReversibleState.BACK_BUTTON_X - EXPERIENCE_PANEL_WIDTH;///< The default x coordinate of the top-left corner of the panel.

    private final GUITextPanel experiencePanel;///< A GUITextPanel object used to display the current experience.

    /**
     * Constructor without parameters.
     */
    public ExperiencePanel() {
        experiencePanel = new GUITextPanel("0 XP",
                GUIAssets.green_button,
                EXPERIENCE_PANEL_X,
                ReversibleState.BACK_BUTTON_Y,
                EXPERIENCE_PANEL_WIDTH,
                GUIButton.BUTTON_H);
        experiencePanel.SetColor(GUITextPanel.DEFAULT_GREEN_COLOR);
    }

    /**
     * Called each frame to draw the panel on the screen.
     *
     * @param g A Java Graphics object.
     */
    public void Draw(Graphics g) {
        experiencePanel.Draw(g);
    }

    /**
     * Updates the current value of the experience.
     */
    public void UpdateValue() {
        long value = GlobalReferences.GetPlayer().GetExperience();
        experiencePanel.SetText(LargeNumberHandler.ParseLongInt(value) + " XP");
    }

    @Override
    public void OnNotify(GameEvent e) {
        // Experience bar updated in case of monster death or upgrade event
        if (e == CombatEvent.ENEMY_DEATH || e instanceof UpgradeEvent) {
            UpdateValue();
        }
    }
}
