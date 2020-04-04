package GUI;

import Entities.CollidableEntities.Enemies.Enemy;
import EventSystem.Events.CombatEvent;
import EventSystem.Events.GameEvent;
import EventSystem.Observer;

import java.awt.*;
import java.util.function.Function;

public class GUIEnemyHealthBar implements Observer {
    public static final int DEFAULT_WIDTH=70;
    public static final int DEFAULT_HEIGHT=5;
    public static final int DEFAULT_Y_OFFSET=-20;

    private Rectangle bar;
    private double currentValue;
    private double maxValue;
    private Enemy target;
    private Function<Enemy, Integer> getter;

    public GUIEnemyHealthBar(Enemy target, Function<Enemy, Integer> getter){
        this.target=target;
        this.getter=getter;
        maxValue=getter.apply(target);
        currentValue=maxValue;
        bar=new Rectangle(
                target.GetHitBoxX()+(Enemy.DEFAULT_WIDTH-DEFAULT_WIDTH)/2,
                target.GetHitBoxY()+DEFAULT_Y_OFFSET,
                DEFAULT_WIDTH,
                DEFAULT_HEIGHT);
    }

    public void ResizeBar(){
        currentValue=getter.apply(target);
        bar.setBounds(bar.x,bar.y,(int)(DEFAULT_WIDTH*currentValue/maxValue),bar.height);
    }

    public void Draw(Graphics g){
        g.setColor(Color.RED);
        g.fillRect(
                target.GetHitBoxX()+(Enemy.DEFAULT_WIDTH-DEFAULT_WIDTH)/2,
                target.GetHitBoxY()+DEFAULT_Y_OFFSET,
                bar.width,
                bar.height);
    }

    @Override
    public void OnNotify(GameEvent e) {
        if(!(e.GetType() == GameEvent.GameEventType.CombatEvent))
            return;
        if(e==CombatEvent.MONSTER_HIT){
            ResizeBar();
        }
    }
}
