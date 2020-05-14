package States;

import Assets.Images.BackgroundAssets;
import Assets.Images.GUIAssets;
import Entities.Player;
import GUI.GUIButton;
import GUI.Text.GUIText;
import Game.Game;
import Game.GameWindow;
import Game.GlobalReferences;
import GameSystems.EventSystem.Events.AudioEvent;
import GameSystems.UpgradeSystem.*;
import SQL.DatabaseManager;

import java.awt.*;
import java.util.ArrayList;

/**
 * @brief Implements the upgrade page of the game.
 */
public class UpgradeState extends ReversibleState {
    public static final int UPGRADE_LEFT_X = 135;///< The x coordinate of the left upgrade panels.
    public static final int UPGRADE_RIGHT_X = GameWindow.SCREEN_DIMENSION.width -
            UPGRADE_LEFT_X - Upgrade.PANEL_WIDTH - Upgrade.ICON_WITDH;///< The x coordinate of the right upgrade panels.
    public static final int UPGRADE_TOP_Y = 170;///< The y coordinate of the top upgrade panels.
    public static final int UPGRADE_Y_OFFSET = Upgrade.ICON_HEIGHT + 150;///< The y offset between upgrades.

    public static ArrayList<GUIText> infoText;///< Information about the current difficulty and level.

    private final ExperiencePanel experiencePanel;///< Reference to the experience panel.
    private final ArrayList<Upgrade> allUpgrades;///< List that holds all upgrades.

    /**
     * Constructor without parameters.
     */
    public UpgradeState() {
        // Back button events
        allButtons.get(0).AddActionListener(actionEvent -> {
            NotifyAllObservers(AudioEvent.STOP_CURRENT_STATE_MUSIC);
            DatabaseManager.SavePlayerData();
            StateManager.GetInstance().SetCurrentState(StateIndex.MENU_STATE);
        });
        // Play button
        int playH = 110;
        int playW = 400;
        int playX = GameWindow.SCREEN_DIMENSION.width / 2 - playW / 2;
        allButtons.add(new GUIButton(GUIAssets.play_button, GUIAssets.play_button_hovered, playX, 725, playW, playH));
        allButtons.get(1).AddActionListener(actionEvent -> {
            NotifyAllObservers(AudioEvent.STOP_CURRENT_STATE_MUSIC);
            DatabaseManager.SavePlayerData();
            StateManager.GetInstance().SetCurrentState(StateIndex.PLAY_STATE);
        });

        experiencePanel = GlobalReferences.GetExperiencePanel();

        allUpgrades = new ArrayList<>(4);
        allUpgrades.add(new DamageUpgrade(UPGRADE_LEFT_X, UPGRADE_TOP_Y));
        allUpgrades.add(new ProjectileNumberUpgrade(UPGRADE_RIGHT_X, UPGRADE_TOP_Y));
        allUpgrades.add(new CritUpgrade(UPGRADE_LEFT_X, UPGRADE_TOP_Y + UPGRADE_Y_OFFSET));
        allUpgrades.add(new SpellUpgrade(UPGRADE_RIGHT_X, UPGRADE_TOP_Y + UPGRADE_Y_OFFSET));
        // Add the upgrade buttons to the button list
        for (Upgrade u : allUpgrades) {
            allButtons.add(u.GetButtonHandle());
        }
        // Text for player info
        int infoTextSize = 100;
        infoText = new ArrayList<>(2);
        infoText.add(new GUIText("EASY", GameWindow.SCREEN_DIMENSION.width - 270,
                Player.MANABAR_Y + Player.HEALTHBAR_HEIGHT, infoTextSize));
        infoText.add(new GUIText("DAY 1", GameWindow.SCREEN_DIMENSION.width / 2 - 100,
                BACK_BUTTON_Y + GUIButton.BUTTON_H - 10, infoTextSize));
    }

    @Override
    public void Init() {
        super.Init();
        for (Upgrade upgrade : allUpgrades) {
            upgrade.Init();
            upgrade.CheckIfBlocked();
        }
        GlobalReferences.GetExperiencePanel().UpdateValue();
        NotifyAllObservers(AudioEvent.PLAY_CURRENT_STATE_MUSIC);
        // Init information
        InitText();
    }

    /**
     * Initializes the difficulty and current level information.
     */
    private void InitText() {
        // Init difficulty text
        String d = "EASY";
        Color c = Color.GREEN;
        switch (Game.difficulty) {
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
        // Init level text
        infoText.get(1).SetText("DAY " + GlobalReferences.GetPlayer().GetLevel());
    }

    @Override
    public void MousePressed(Point pressPoint) {
        super.MousePressed(pressPoint);
        // In case an upgrade was just bought
        for (Upgrade upgrade : allUpgrades) {
            upgrade.CheckIfBlocked();
        }
    }

    @Override
    public void Draw(Graphics2D g2d) {
        g2d.drawImage(BackgroundAssets.bgGameDark, 0, 0, null);
        for (GUIButton b : allButtons) {
            b.Draw(g2d);
        }
        for (Upgrade u : allUpgrades) {
            u.Draw(g2d);
        }
        for (GUIText t : infoText) {
            t.Draw(g2d);
        }
        experiencePanel.Draw(g2d);
    }
}
