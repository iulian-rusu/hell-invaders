package GameSystems.UpgradeSystem;

import Assets.Images.GUIAssets;
import GUI.GUIButton;
import GUI.Text.GUIText;
import GUI.Text.GUITextPanel;
import Game.GlobalReferences;
import GameSystems.EventSystem.Observable;
import States.AboutState;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Upgrade extends Observable {
    //position parameters
    public static final int OFFSET = 10;
    public static final int PANEL_WIDTH = 2 * GUIButton.BUTTON_W + OFFSET;
    public static final int PANEL_HEIGHT = GUIButton.BUTTON_H;
    public static final int ICON_HEIGHT = 2 * PANEL_HEIGHT + OFFSET;
    public static final int ICON_WITDH = ICON_HEIGHT;
    //text parameters
    public static final String MAX_TEXT = "MAXED";
    public static final float FONT_SIZE = 55f;
    public static final int TEXT_OFFSET = 50;
    public static final Color TEXT_COLOR = AboutState.TEXT_COLOR;

    protected GUIButton buyButton;
    protected GUITextPanel upgradeName;
    protected GUITextPanel priceText;
    protected BufferedImage icon;
    protected ArrayList<GUIText> description;
    protected long price;
    protected int level;
    protected int x;
    protected int y;
    protected boolean isMaxed = false;

    public Upgrade(int x, int y) {
        this.x = x;
        this.y = y;
        //TODO : don't set level explicitly here, load it from DB
        level = 1;
        //init buttons and panels
        buyButton = new GUIButton(GUIAssets.buy_button, GUIAssets.buy_button_hovered,
           x + ICON_WITDH + OFFSET, y + PANEL_HEIGHT + OFFSET, GUIButton.BUTTON_W, GUIButton.BUTTON_H);
        upgradeName = new GUITextPanel("NAME", GUIAssets.yellow_button,
           x + ICON_WITDH + OFFSET, y, PANEL_WIDTH, PANEL_HEIGHT);
        priceText = new GUITextPanel("PRICE", GUIAssets.green_button,
                x + ICON_WITDH + GUIButton.BUTTON_W + 2 * OFFSET, y + PANEL_HEIGHT + OFFSET,
                GUIButton.BUTTON_W, GUIButton.BUTTON_H, GUITextPanel.DEFAULT_GREEN_COLOR);
        //init description
        description = new ArrayList<>();
        description.add(new GUIText("NEXT VALUE", x, y + ICON_HEIGHT + TEXT_OFFSET, FONT_SIZE));
        description.add(new GUIText("CURRENT VALUE", x, y + ICON_HEIGHT + 2 * TEXT_OFFSET, FONT_SIZE));
        for (GUIText t : description) {
            t.SetColor(TEXT_COLOR);
        }
        description.get(0).SetFontSize(FONT_SIZE + 10);
        //experience panel will observe all upgrades
        AddObserver(ExperiencePanel.GetInstance());

        LoadDataFromDB();
    }

    public void CheckIfBlocked() {
        if (isMaxed || GlobalReferences.player.GetExperience() < this.price) {
            buyButton.Block(GUIAssets.buy_button_blocked);
        } else if (!isMaxed) {
            buyButton.Unblock(GUIAssets.buy_button);
        }
    }

    public GUIButton GetButtonHandle() {
        return this.buyButton;
    }

    public void Draw(Graphics g) {
        g.drawImage(icon, x, y, ICON_WITDH, ICON_HEIGHT, null);
        upgradeName.Draw(g);
        priceText.Draw(g);
        for (GUIText text : description) {
            text.Draw(g);
        }
    }

    protected long GetNewExperience() {
        long playerXP = GlobalReferences.player.GetExperience();
        playerXP -= price;
        return playerXP;
    }

    protected abstract void Buy();
    protected abstract void LoadDataFromDB();
}
