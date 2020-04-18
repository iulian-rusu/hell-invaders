package GameSystems.UpgradeSystem;

import Assets.Images.GUIAssets;
import Entities.Player;
import GUI.GUIButton;
import GUI.GUIText;
import GUI.GUITextPanel;
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

    public Upgrade(int x, int y) {
        this.x = x;
        this.y = y;
        this.level = 1;
        //init buttons and panels
        this.buyButton = new GUIButton(GUIAssets.buy_button, GUIAssets.buy_button_hovered,
                x + ICON_WITDH + OFFSET, y + PANEL_HEIGHT + OFFSET, GUIButton.BUTTON_W, GUIButton.BUTTON_H);
        this.upgradeName = new GUITextPanel("", GUIAssets.yellow_button,
                x + ICON_WITDH + OFFSET, y, PANEL_WIDTH, PANEL_HEIGHT);
        this.priceText = new GUITextPanel("", GUIAssets.green_button,
                x + ICON_WITDH + GUIButton.BUTTON_W + 2 * OFFSET, y + PANEL_HEIGHT + OFFSET, GUIButton.BUTTON_W, GUIButton.BUTTON_H);
        this.priceText.SetColor(GUITextPanel.DEFAULT_GREEN_COLOR);
        //init description
        this.description = new ArrayList<>();
        this.description.add(new GUIText("NEXT VALUE", x, y + ICON_HEIGHT + TEXT_OFFSET, FONT_SIZE));
        this.description.add(new GUIText("CURRENT VALUE", x, y + ICON_HEIGHT + 2 * TEXT_OFFSET, FONT_SIZE));
        for (GUIText t : description) {
            t.SetColor(TEXT_COLOR);
        }
        description.get(0).SetFontSize(FONT_SIZE + 10);
        //experience panel will observe all upgrades
        AddObserver(ExperiencePanel.GetInstance());
    }

    public void CheckIfBlocked() {
        if(Player.GetInstance().GetExperience()<this.price){
            this.buyButton.Block(GUIAssets.buy_button_blocked);
        }
        else{
            this.buyButton.Unblock(GUIAssets.buy_button);
        }
    }

    public GUIButton GetButtonHandle() {
        return this.buyButton;
    }

    public void mouseMoved(MouseEvent e) {
        buyButton.mouseMoved(e);
    }

    public void mousePressed(MouseEvent e) {
        buyButton.mousePressed(e);
    }

    public void Draw(Graphics g) {
        g.drawImage(icon, x, y, ICON_WITDH, ICON_HEIGHT, null);
        upgradeName.Draw(g);
        priceText.Draw(g);
        for (GUIText t : description) {
            t.Draw(g);
        }
    }

    protected long GetNewExperience() {
        long playerXP = Player.GetInstance().GetExperience();
        playerXP -= this.price;
        return playerXP;
    }

    protected abstract void Buy();
}
