package GameSystems.UpgradeSystem;

import Assets.Images.GUIAssets;
import Entities.Player;
import Game.GlobalReferences;
import GameSystems.EventSystem.Events.UpgradeEvent;
import GameSystems.NumberSystem.LargeNumberHandler;

/**
 * @brief Provides an upgrade mechanic for the damage dealt by each projectle.
 */
public class DamageUpgrade extends Upgrade {
    public static final long DEFAULT_PRICE = 30;///< The default price of the first purchase.
    public static final double PRICE_INCREMENT = 1.23;///< The amount by which the next purchase increments.
    public static final double DAMAGE_INCREMENT = 1.17;///< The amount by which the next damage value increments.
    private long damage;///< The damage to be upgraded to.

    /**
     * Constructor with parameters.
     *
     * @param x The x coordinate of the top-left corner of the upgrade.
     * @param y The y coordinate of the top-left corner of the upgrade.
     */
    public DamageUpgrade(int x, int y) {
        super(x, y);
        icon = GUIAssets.damage;
        // Init prices and damage
        price = GET_PRICE(level);
        damage = GET_DAMAGE(level);
        upgradeName.SetText("SPELL DAMAGE");
        // Set action listener for the button
        buyButton.AddActionListener(actionEvent -> Buy());
    }

    /**
     * Returns the current price of the upgrade corresponding to its level.
     *
     * @param level The current level of the upgrade.
     * @return The price of the upgrade at the current level.
     */
    public static long GET_PRICE(int level) {
        return (long) (DEFAULT_PRICE * Math.pow(PRICE_INCREMENT, level - 1));
    }

    /**
     * Returns the current damage of the upgrade corresponding to its level.
     *
     * @param level The current level of the upgrade.
     * @return The damage of the upgrade at the current level.
     */
    public static long GET_DAMAGE(int level) {
        return (long) (Player.DEFAULT_DAMAGE * Math.pow(DAMAGE_INCREMENT, level));
    }

    @Override
    protected void UpdateDescription() {
        damage = GET_DAMAGE(level);
        price = GET_PRICE(level);
        this.priceText.SetText(LargeNumberHandler.ParseLongInt(price) + " XP");
        description.get(0).SetText("NEXT: " + LargeNumberHandler.ParseLongInt(damage) + " DAMAGE");
        description.get(1).SetText("CURRENT: " + LargeNumberHandler.ParseLongInt(GET_DAMAGE(level - 1)) + " DAMAGE");
    }

    @Override
    protected void Buy() {
        long playerXP = GetNewExperience();
        if (playerXP < 0) {
            return;
        }
        GlobalReferences.GetPlayer().SetExperience(playerXP);
        GlobalReferences.GetPlayer().SetProjectileDamage(damage, level + 1);
        // Update values
        level++;
        UpdateDescription();
        NotifyAllObservers(UpgradeEvent.DAMAGE_UPGRADE_BOUGHT);
    }

    @Override
    public void Init() {
        level = GlobalReferences.GetPlayer().GetProjectileDamageLevel();
        UpdateDescription();
    }
}
