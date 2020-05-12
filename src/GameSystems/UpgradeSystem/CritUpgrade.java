package GameSystems.UpgradeSystem;

import Assets.Images.GUIAssets;
import Game.GlobalReferences;
import GameSystems.EventSystem.Events.UpgradeEvent;
import GameSystems.NumberSystem.LargeNumberHandler;

/**
 *  @brief Provide an upgrade mechanic for the player's critical hit chance.
 */
public class CritUpgrade extends Upgrade {
    public static final long DEFAULT_PRICE = 100;///< The default price of the first purchase.
    public static final double PRICE_INCREMENT = 1.45;///< The amount by which the next purchase increments.

    /**
     * Returns the current price of the upgrade corresponding to its level.
     *
     * @param level The current level of the upgrade.
     * @return The price of the upgrade at the current level.
     */
    public static long GET_PRICE(int level) {
        return (long) (DEFAULT_PRICE * Math.pow(PRICE_INCREMENT, level - 1));
    }

    public static final int CRIT_INCREMENT = 5;///< The increment in critical hit chance with each level.

    private int critChance;///< The critical hit chance to be upgraded to.

    /**
     * Constructor with parameters.
     *
     * @param x The x coordinate of the top-left corner of the upgrade.
     * @param y The y coordinate of the top-left corner of the upgrade.
     */
    public CritUpgrade(int x, int y) {
        super(x, y);
        icon = GUIAssets.crit;
        // Init prices
        price = GET_PRICE(level);
        upgradeName.SetText("CRITICAL CHANCE");
        // Set action listener for buy button
        buyButton.AddActionListener(actionEvent -> Buy());
    }

    @Override
    protected void UpdateDescription() {
        price = GET_PRICE(level);
        if (critChance > 100) {
            isMaxed = true;
            priceText.SetText(MAX_TEXT);
            description.get(0).SetText(MAX_TEXT);
        } else {
            priceText.SetText(LargeNumberHandler.ParseLongInt(price) + " XP");
            description.get(0).SetText("NEXT: " + critChance + " %");
        }
        description.get(1).SetText("CURRENT: " + (critChance - CRIT_INCREMENT) + " %");
    }

    @Override
    protected void Buy() {
        long playerXP = GetNewExperience();
        if (playerXP < 0) {
            return;
        }
        GlobalReferences.GetPlayer().SetExperience(playerXP);
        GlobalReferences.GetPlayer().SetCritChance(critChance);
        // Update values
        level++;
        critChance += CRIT_INCREMENT;
        UpdateDescription();
        NotifyAllObservers(UpgradeEvent.CRIT_UPGRADE_BOUGHT);
    }

    @Override
    public void Init() {
        int critChance = GlobalReferences.GetPlayer().GetCritChance();
        level = critChance / CRIT_INCREMENT + 1;
        this.critChance = critChance + CRIT_INCREMENT;
        UpdateDescription();
    }
}
