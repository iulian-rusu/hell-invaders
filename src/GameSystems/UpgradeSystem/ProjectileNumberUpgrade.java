package GameSystems.UpgradeSystem;

import Assets.Images.GUIAssets;
import Entities.Player;
import GameSystems.EventSystem.Events.UpgradeEvent;
import GameSystems.StatsSystem.LargeNumberHandler;

import java.awt.*;

public class ProjectileNumberUpgrade extends Upgrade{
    //price update parameters
    public static final long DEFAULT_PRICE=300;
    public static final double PRICE_INCREMENT=10.0;
    public static long GET_PRICE(int level){return (long)(DEFAULT_PRICE*Math.pow(PRICE_INCREMENT,level-1));}

    private int numProjectiles=2;

    public ProjectileNumberUpgrade(int x, int y){
        super(x,y);
        icon = GUIAssets.projectiles;
        //init prices and damage
        this.price=DEFAULT_PRICE;
        this.priceText.SetText("PRICE: "+ LargeNumberHandler.ParseLongInt(DEFAULT_PRICE)+" EXP");
        this.valueText.SetText(String.valueOf(this.numProjectiles));
        //set action listener for buy button
        this.buyButton.AddActionListener(actionEvent -> {
            Buy();
        });
        //update description
        description.get(0).SetText("GET MORE PROJECILES");
        description.get(0).SetColor(Color.ORANGE);
        UpdateDescription();
    }

    private void UpdateDescription(){
        this.priceText.SetText("PRICE: "+LargeNumberHandler.ParseLongInt(this.price)+" EXP");
        this.valueText.SetText(String.valueOf(this.numProjectiles));
        description.get(1).SetText("CURRENT: "+String.valueOf(this.numProjectiles-1)
                +((numProjectiles>2)?" PROJECTILES":" PROJECTILE"));
    }

    @Override
    protected void Buy(){
        long playerXP=GetNewExperience();
        if(playerXP<0){
            return;
        }
        Player.GetInstance().SetExperience(playerXP);
        Player.GetInstance().SetNumProjectiles(this.numProjectiles);
        //update values
        this.level++;
        this.numProjectiles++;
        this.price= GET_PRICE(this.level);
        UpdateDescription();
        NotifyAllObservers(UpgradeEvent.PROJECTILE_UPGRADE_BOUGHT);
    }

}
