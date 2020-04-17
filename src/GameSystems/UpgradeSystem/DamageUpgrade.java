package GameSystems.UpgradeSystem;

import Assets.Images.GUIAssets;
import Entities.Player;
import GameSystems.EventSystem.Events.UpgradeEvent;
import GameSystems.StatsSystem.LargeNumberHandler;

import java.awt.*;


public class DamageUpgrade extends Upgrade{
    //price update parameters
    public static final long DEFAULT_PRICE=30;
    public static final double PRICE_INCREMENT=1.12;
    public static long GET_PRICE(int level){return (long)(DEFAULT_PRICE*Math.pow(PRICE_INCREMENT,level-1));}
    //damage parameters
    public static final double DAMAGE_INCREMENT=1.11;
    public static long GET_DAMAGE(int level){ return (long)(Player.DEFAULT_DAMAGE*Math.pow(DAMAGE_INCREMENT,level)); }

    private long damage;

    public DamageUpgrade(int x, int y){
        super(x,y);
        icon = GUIAssets.damage;
        //init prices and damage
        this.price=DEFAULT_PRICE;
        this.damage= GET_DAMAGE(level);
        this.priceText.SetText("PRICE: "+LargeNumberHandler.ParseLongInt(DEFAULT_PRICE)+" EXP");
        this.valueText.SetText(LargeNumberHandler.ParseLongInt(this.damage));
        //set action listener for buy button
        this.buyButton.AddActionListener(actionEvent -> {
            Buy();
        });
        //update description
        description.get(0).SetText("GET MORE DAMAGE");
        description.get(0).SetColor(Color.ORANGE);
        UpdateDescription();
    }

    private void UpdateDescription(){
        this.priceText.SetText("PRICE: "+LargeNumberHandler.ParseLongInt(this.price)+" EXP");
        this.valueText.SetText(LargeNumberHandler.ParseLongInt(this.damage));
        description.get(1).SetText("CURRENT: "+LargeNumberHandler.ParseLongInt(GET_DAMAGE(this.level-1))+" DAMAGE");
    }

    @Override
    protected void Buy(){
        long playerXP=GetNewExperience();
        if(playerXP<0){
            return;
        }
        Player.GetInstance().SetExperience(playerXP);
        Player.GetInstance().SetProjectileDamage(this.damage);
        //update values
        this.level++;
        this.damage= GET_DAMAGE(this.level);
        this.price= GET_PRICE(this.level);
        UpdateDescription();
        NotifyAllObservers(UpgradeEvent.DAMAGE_UPGRADE_BOUGHT);
    }
}
