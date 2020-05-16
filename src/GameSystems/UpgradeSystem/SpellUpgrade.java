package GameSystems.UpgradeSystem;

import Assets.Images.GUIAssets;
import Entities.CollidableEntities.Projectiles.ProjectileType;
import Game.GlobalReferences;
import GameSystems.EventSystem.Events.UpgradeEvent;
import GameSystems.NumberSystem.LargeNumberHandler;

import java.awt.image.BufferedImage;

/**
 * @brief Provides an upgrade mechanic for the spell type of the player.
 */
public class SpellUpgrade extends Upgrade {
    public static final long DEFAULT_PRICE = 9000;///< The default price of the first purchase.
    public static final double PRICE_INCREMENT = 100.0;///< The amount by which the next purchase increments.
    private final String[] spellProgression =
            {"FIRE", "FROST", "ARCANE", MAX_TEXT};///< An array containing all upgrade names.
    private final BufferedImage[] spellIcons =
            {null, GUIAssets.upgrade_frost, GUIAssets.upgrade_arcane, GUIAssets.upgrade_arcane};///< An array containing all icons.
    private int spellIndex;///< The index of the current upgrade in the array.
    /**
     * Constructor with parameters.
     *
     * @param x The x coordinate of the top-left corner of the upgrade.
     * @param y The y coordinate of the top-left corner of the upgrade.
     */
    public SpellUpgrade(int x, int y) {
        super(x, y);
        // Init prices
        price = GET_PRICE(level);
        upgradeName.SetText("SPELL TYPE");
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

    @Override
    protected void UpdateDescription() {
        price = GET_PRICE(level);
        if (this.spellProgression[spellIndex].equals(MAX_TEXT)) {
            isMaxed = true;
            priceText.SetText(MAX_TEXT);
            description.get(0).SetText(MAX_TEXT);
        } else {
            priceText.SetText(LargeNumberHandler.ParseLongInt(price) + " XP");
            description.get(0).SetText("NEXT: " + spellProgression[spellIndex] + " SPELL");
        }
        description.get(1).SetText("CURRENT: " + spellProgression[spellIndex - 1] + " SPELL");
        icon = spellIcons[spellIndex];
    }

    @Override
    protected void Buy() {
        long playerXP = GetNewExperience();
        if (playerXP < 0) {
            return;
        }
        GlobalReferences.GetPlayer().SetExperience(playerXP);
        GlobalReferences.GetPlayer().SetProjectileType(ProjectileType.valueOf(spellProgression[spellIndex]));
        // Update values
        level++;
        spellIndex++;
        UpdateDescription();
        NotifyAllObservers(UpgradeEvent.SPELL_UPGRADE_BOUGHT);
    }

    @Override
    public void Init() {
        level = GlobalReferences.GetPlayer().GetProjectileType().value;
        isMaxed = false;
        spellIndex = level;
        UpdateDescription();
    }
}
