package GameSystems.UpgradeSystem;

import Assets.Images.GUIAssets;
import Entities.CollidableEntities.Projectiles.ProjectileType;
import Entities.Player;
import GameSystems.EventSystem.Events.UpgradeEvent;
import GameSystems.NumberSystem.LargeNumberHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class SpellUpgrade extends Upgrade {
    //price update parameters
    public static final long DEFAULT_PRICE = 9000;
    public static final double PRICE_INCREMENT = 100.0;

    public static long GET_PRICE(int level) {
        return (long) (DEFAULT_PRICE * Math.pow(PRICE_INCREMENT, level - 1));
    }

    private final HashMap<String, ProjectileType> spellMap;
    private final String[] spellProgression = {"FIRE", "FROST", "ARCANE", MAX_TEXT};
    private final BufferedImage[] spellIcons = {null, GUIAssets.upgrade_frost, GUIAssets.upgrade_arcane, GUIAssets.upgrade_arcane};
    private int spellIndex = 1;

    public SpellUpgrade(int x, int y) {
        super(x, y);
        spellMap = new HashMap<>();
        spellMap.put(spellProgression[0], ProjectileType.FIRE);
        spellMap.put(spellProgression[1], ProjectileType.FROST);
        spellMap.put(spellProgression[2], ProjectileType.ARCANE);
        //init prices
        this.price = DEFAULT_PRICE;
        this.upgradeName.SetText("SPELL TYPE");
        //set action listener for buy button
        this.buyButton.AddActionListener(actionEvent -> Buy());
        //update description
        description.get(0).SetColor(Color.ORANGE);
        UpdateDescription();
    }

    private void UpdateDescription() {
        if (this.spellProgression[this.spellIndex].equals(MAX_TEXT)) {
            isMaxed=true;
            this.priceText.SetText(MAX_TEXT);
            description.get(0).SetText(MAX_TEXT);
        } else {
            this.priceText.SetText(LargeNumberHandler.ParseLongInt(this.price) + " XP");
            description.get(0).SetText("NEXT: " + this.spellProgression[this.spellIndex] + " SPELL");
        }
        description.get(1).SetText("CURRENT: " + this.spellProgression[this.spellIndex - 1] + " SPELL");
        icon = spellIcons[spellIndex];
    }

    @Override
    protected void Buy() {
        long playerXP = GetNewExperience();
        if (playerXP < 0) {
            return;
        }
        Player.GetInstance().SetExperience(playerXP);
        Player.GetInstance().SetProjectileType(this.spellMap.get(this.spellProgression[this.spellIndex]));
        //update values
        this.level++;
        this.spellIndex++;
        this.price = GET_PRICE(this.level);
        UpdateDescription();
        NotifyAllObservers(UpgradeEvent.SPELL_UPGRADE_BOUGHT);
    }
}
