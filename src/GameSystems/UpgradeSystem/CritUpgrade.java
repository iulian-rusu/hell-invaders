package GameSystems.UpgradeSystem;

import Assets.Images.GUIAssets;
import Entities.Player;
import GameSystems.EventSystem.Events.UpgradeEvent;
import GameSystems.StatsSystem.LargeNumberHandler;

import java.awt.*;

public class CritUpgrade extends Upgrade {
    //price update parameters
    public static final long DEFAULT_PRICE=75;
    public static final double PRICE_INCREMENT=1.3;
    public static long GET_PRICE(int level){return (long)(DEFAULT_PRICE*Math.pow(PRICE_INCREMENT,level-1));}
    //crit update parameters
    public static final int CRIT_INCREMENT=5;

    private int critChance=5;

    public CritUpgrade(int x, int y){
        super(x,y);
        icon = GUIAssets.crit;
        //init prices and damage
        this.price=DEFAULT_PRICE;
        this.priceText.SetText("PRICE: "+ LargeNumberHandler.ParseLongInt(DEFAULT_PRICE)+" EXP");
        this.valueText.SetText(String.valueOf(this.critChance));
        //set action listener for buy button
        this.buyButton.AddActionListener(actionEvent -> {
            Buy();
        });
        //update description
        description.get(0).SetText("GET MORE CRITICAL HITS");
        description.get(0).SetColor(Color.ORANGE);
        UpdateDescription();
    }

    private void UpdateDescription(){
        if(critChance>100){
            buyButton.Block(GUIAssets.buy_button_blocked);
            this.priceText.SetText(MAX_TEXT);
            this.valueText.SetText(MAX_TEXT);
        }else {
            this.priceText.SetText("PRICE: " + LargeNumberHandler.ParseLongInt(this.price) + " EXP");
            this.valueText.SetText(String.valueOf(this.critChance));
        }
        description.get(1).SetText("CURRENT: "+String.valueOf(this.critChance-CRIT_INCREMENT)+" %");
    }

    @Override
    protected void Buy(){
        long playerXP=GetNewExperience();
        if(playerXP<0){
            return;
        }
        Player.GetInstance().SetExperience(playerXP);
        Player.GetInstance().SetCritChance(this.critChance);
        //update values
        this.level++;
        this.critChance+=CRIT_INCREMENT;
        this.price=GET_PRICE(this.level);
        UpdateDescription();
        NotifyAllObservers(UpgradeEvent.CRIT_UPGRADE_BOUGHT);
    }
}
