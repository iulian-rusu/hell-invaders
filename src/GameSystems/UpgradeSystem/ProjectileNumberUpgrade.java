package GameSystems.UpgradeSystem;

import Assets.Images.GUIAssets;
import Game.GlobalReferences;
import GameSystems.EventSystem.Events.UpgradeEvent;
import GameSystems.NumberSystem.LargeNumberHandler;

/**
 *  @brief Provides an upgrade mechanic for the number of projectiles shot by the player.
 */
public class ProjectileNumberUpgrade extends Upgrade {
    public static final long DEFAULT_PRICE = 500;///< The default price of the first purchase.
    public static final double PRICE_INCREMENT = 100.0;///< The amount by which the next purchase increments.

    /**
     * Returns the current price of the upgrade corresponding to its level.
     *
     * @param level The current level of the upgrade.
     * @return The price of the upgrade at the current level.
     */
    public static long GET_PRICE(int level) {
        return (long) (DEFAULT_PRICE * Math.pow(PRICE_INCREMENT, level - 1));
    }

    private int numProjectiles;///< The number of projectiles to be upgraded to.

    /**
     * Constructor with parameters.
     *
     * @param x The x coordinate of the top-left corner of the upgrade.
     * @param y The y coordinate of the top-left corner of the upgrade.
     */
    public ProjectileNumberUpgrade(int x, int y) {
        super(x, y);
        icon = GUIAssets.projectiles;
        // Init prices
        price = GET_PRICE(level);
        upgradeName.SetText("PROJECTILES");
        // Set action listener forthe button
        buyButton.AddActionListener(actionEvent -> Buy());
    }

    @Override
    protected void UpdateDescription() {
        price = GET_PRICE(level);
        this.priceText.SetText(LargeNumberHandler.ParseLongInt(price) + " XP");
        description.get(0).SetText("NEXT: " + this.numProjectiles + " PROJECTILES");
        description.get(1).SetText("CURRENT: " + (numProjectiles - 1)
                + ((numProjectiles > 2) ? " PROJECTILES" : " PROJECTILE"));
    }

    @Override
    protected void Buy() {
        long playerXP = GetNewExperience();
        if (playerXP < 0) {
            return;
        }
        GlobalReferences.GetPlayer().SetExperience(playerXP);
        GlobalReferences.GetPlayer().SetNumProjectiles(numProjectiles);
        // Update values
        level++;
        numProjectiles++;
        UpdateDescription();
        NotifyAllObservers(UpgradeEvent.PROJECTILE_UPGRADE_BOUGHT);
    }

    @Override
    public void Init() {
        level = GlobalReferences.GetPlayer().GetNumProjectiles();
        numProjectiles = level + 1;
        price = GET_PRICE(level);
        UpdateDescription();
    }

}
