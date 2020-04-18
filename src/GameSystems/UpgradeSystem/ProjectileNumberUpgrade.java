package GameSystems.UpgradeSystem;

import Assets.Images.GUIAssets;
import Entities.Player;
import GameSystems.EventSystem.Events.UpgradeEvent;
import GameSystems.NumberSystem.LargeNumberHandler;

import java.awt.*;

public class ProjectileNumberUpgrade extends Upgrade{
    //price update parameters
    public static final long DEFAULT_PRICE=500;
    public static final double PRICE_INCREMENT=100.0;
    public static long GET_PRICE(int level){return (long)(DEFAULT_PRICE*Math.pow(PRICE_INCREMENT,level-1));}

    private int numProjectiles=2;

    public ProjectileNumberUpgrade(int x, int y){
        super(x,y);
        icon = GUIAssets.projectiles;
        //init prices
        this.price=DEFAULT_PRICE;
        this.upgradeName.SetText("PROJECTILES");
        //set action listener for buy button
        this.buyButton.AddActionListener(actionEvent -> Buy());
        //update description
        description.get(0).SetColor(Color.ORANGE);
        UpdateDescription();
    }

    private void UpdateDescription(){
        this.priceText.SetText(LargeNumberHandler.ParseLongInt(this.price)+" XP");
        description.get(0).SetText("NEXT: "+ this.numProjectiles +" PROJECTILES");
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
