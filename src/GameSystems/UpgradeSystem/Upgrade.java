package GameSystems.UpgradeSystem;

import Assets.Images.GUIAssets;
import GUI.GUIButton;
import GUI.Text.GUIText;
import GUI.Text.GUITextPanel;
import Game.GlobalReferences;
import GameSystems.EventSystem.Observable;
import States.AboutState;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @brief Implements the upgrade mechanic of the game.
 */
public abstract class Upgrade extends Observable {
    public static final int OFFSET = 10;///< The distance between GUI elements.
    public static final int PANEL_WIDTH = 2 * GUIButton.BUTTON_W + OFFSET;///< The width of the upgrade panel.
    public static final int PANEL_HEIGHT = GUIButton.BUTTON_H;///< The height of the upgrade panel.
    public static final int ICON_HEIGHT = 2 * PANEL_HEIGHT + OFFSET;///< The height of the upgrade icon.
    public static final int ICON_WITDH = ICON_HEIGHT;///< The width of the upgrade icon.

    public static final String MAX_TEXT = "MAXED";///< The default text displayed when an upgrade is maxed out.
    public static final float FONT_SIZE = 55f;///< The default font size.
    public static final int TEXT_OFFSET = 50;///< The default distance between description lines.
    public static final Color TEXT_COLOR = AboutState.TEXT_COLOR;///< The default color of the desciption text.

    protected GUIButton buyButton;///< The button that buys the upgrade.
    protected GUITextPanel upgradeName;///< The panel displaying the upgrade name
    protected GUITextPanel priceText;///< The text displaying the current price of the upgrade.
    protected BufferedImage icon;///< The icon of the upgrade.
    protected ArrayList<GUIText> description;///< The description of the upgrade: current and next values.
    protected long price;///< The current price of the upgrade.
    protected int level;///< The current level of the upgrade.
    protected int x;//< The x coordinate of the top-left corner of the upgrade.
    protected int y;//< The y coordinate of the top-left corner of the upgrade.
    protected boolean isMaxed = false;///< Flag that indicates whether an upgrade can be maxed out or not.

    /**
     * Constructor with parameters.
     *
     * @param x The x coordinate of the top-left corner of the upgrade.
     * @param y The y coordinate of the top-left corner of the upgrade.
     */
    public Upgrade(int x, int y) {
        this.x = x;
        this.y = y;
        // Init buttons and panels
        buyButton = new GUIButton(GUIAssets.buy_button, GUIAssets.buy_button_hovered,
                x + ICON_WITDH + OFFSET, y + PANEL_HEIGHT + OFFSET, GUIButton.BUTTON_W, GUIButton.BUTTON_H);
        upgradeName = new GUITextPanel("NAME", GUIAssets.yellow_button,
                x + ICON_WITDH + OFFSET, y, PANEL_WIDTH, PANEL_HEIGHT);
        priceText = new GUITextPanel("PRICE", GUIAssets.green_button,
                x + ICON_WITDH + GUIButton.BUTTON_W + 2 * OFFSET, y + PANEL_HEIGHT + OFFSET,
                GUIButton.BUTTON_W, GUIButton.BUTTON_H, GUITextPanel.DEFAULT_GREEN_COLOR);
        // Init description
        description = new ArrayList<>();
        description.add(new GUIText("NEXT VALUE", x, y + ICON_HEIGHT + TEXT_OFFSET, FONT_SIZE));
        description.add(new GUIText("CURRENT VALUE", x, y + ICON_HEIGHT + 2 * TEXT_OFFSET, FONT_SIZE));
        for (GUIText t : description) {
            t.SetColor(TEXT_COLOR);
        }
        description.get(0).SetFontSize(FONT_SIZE + 10);
        description.get(0).SetColor(Color.ORANGE);
        // Experience panel will observe all upgrades
        AddObserver(GlobalReferences.GetExperiencePanel());
    }

    /**
     * Checks if the player has enough resources to buy the upgrade. If not, blocks the "buy" button.
     */
    public void CheckIfBlocked() {
        if (isMaxed || GlobalReferences.GetPlayer().GetExperience() < this.price) {
            buyButton.Block(GUIAssets.buy_button_blocked);
        } else if (!isMaxed) {
            buyButton.Unblock(GUIAssets.buy_button);
        }
    }

    /**
     * Returns a reference to the "buy" button. Used to add the button to the button list of the current game state.
     */
    public GUIButton GetButtonHandle() {
        return this.buyButton;
    }

    /**
     * Called each frame to draw the upgrade interface on the screen.
     *
     * @param g A Java Graphics object.
     */
    public void Draw(Graphics g) {
        g.drawImage(icon, x, y, ICON_WITDH, ICON_HEIGHT, null);
        upgradeName.Draw(g);
        priceText.Draw(g);
        for (GUIText text : description) {
            text.Draw(g);
        }
    }

    /**
     * Returns the new resources(experience) value after an upgrade is purhcased.
     *
     * @return The new experience value.
     */
    protected long GetNewExperience() {
        long playerXP = GlobalReferences.GetPlayer().GetExperience();
        playerXP -= price;
        return playerXP;
    }

    /**
     * Called to buy an upgrade.
     */
    protected abstract void Buy();

    /**
     * Used to update the current desciption of the upgrade after a state change.
     */
    protected abstract void UpdateDescription();

    /**
     * Called when an upgrade is created. Initializes fields based on player data.
     */
    public abstract void Init();
}
