package GameSystems.UpgradeSystem;

import Assets.Images.GUIAssets;
import Game.GlobalReferences;
import GameSystems.EventSystem.Events.UpgradeEvent;
import GameSystems.NumberSystem.LargeNumberHandler;

import java.awt.*;

public class CritUpgrade extends Upgrade {
    //price update parameters
    public static final long DEFAULT_PRICE = 100;
    public static final double PRICE_INCREMENT = 1.45;
    public static long GET_PRICE(int level){return (long)(DEFAULT_PRICE*Math.pow(PRICE_INCREMENT,level-1));}
    //crit update parameters
    public static final int CRIT_INCREMENT = 5;

    private int critChance = 5;

    public CritUpgrade(int x, int y){
        super(x,y);
        icon = GUIAssets.crit;
        //init prices
        price = GET_PRICE(level);
        upgradeName.SetText("CRITICAL CHANCE");
        //set action listener for buy button
        buyButton.AddActionListener(actionEvent -> Buy());
        //update description
        description.get(0).SetColor(Color.ORANGE);
        UpdateDescription();
    }

    private void UpdateDescription(){
        if(critChance>100){
            isMaxed = true;
            priceText.SetText(MAX_TEXT);
            description.get(0).SetText(MAX_TEXT);
        }else {
            priceText.SetText(LargeNumberHandler.ParseLongInt(price) + " XP");
            description.get(0).SetText("NEXT: "+ critChance +" %");
        }
        description.get(1).SetText("CURRENT: "+ (critChance - CRIT_INCREMENT) +" %");
    }

    @Override
    protected void Buy(){
        long playerXP = GetNewExperience();
        if(playerXP < 0){
            return;
        }
        GlobalReferences.player.SetExperience(playerXP);
        GlobalReferences.player.SetCritChance(critChance);
        //update values
        level++;
        critChance+=CRIT_INCREMENT;
        price=GET_PRICE(level);
        UpdateDescription();
        NotifyAllObservers(UpgradeEvent.CRIT_UPGRADE_BOUGHT);
    }

    @Override
    protected void LoadDataFromDB() {

    }
}
