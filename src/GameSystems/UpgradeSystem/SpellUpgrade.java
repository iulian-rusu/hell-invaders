package GameSystems.UpgradeSystem;

import Assets.Images.GUIAssets;
import Entities.CollidableEntities.Projectiles.ProjectileType;
import Entities.Player;
import Game.GlobalReferences;
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
        price = GET_PRICE(level);
        upgradeName.SetText("SPELL TYPE");
        //set action listener for buy button
        buyButton.AddActionListener(actionEvent -> Buy());
        //update description
        description.get(0).SetColor(Color.ORANGE);
        UpdateDescription();
    }

    private void UpdateDescription() {
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
        GlobalReferences.player.SetExperience(playerXP);
        GlobalReferences.player.SetProjectileType(spellMap.get(spellProgression[spellIndex]));
        //update values
        level++;
        spellIndex++;
        price = GET_PRICE(level);
        UpdateDescription();
        NotifyAllObservers(UpgradeEvent.SPELL_UPGRADE_BOUGHT);
    }

    @Override
    protected void LoadDataFromDB() {

    }
}
