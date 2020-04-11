package GUI;

import Entities.Entity;
import EventSystem.Events.CombatEvent;
import EventSystem.Events.GameEvent;
import EventSystem.Observer;

import java.awt.*;
import java.util.function.Function;

public class GUIStatusBar<T extends Entity> implements Observer {

    public static final int DEFAULT_WIDTH = 70;
    public static final int DEFAULT_HEIGHT = 5;
    public static final int DEFAULT_Y_OFFSET = -20;//offset above target entity hitbox

    private final Rectangle bar;
    private Color color;
    private double currentValue;
    private double maxValue;
    private int maxWidth = DEFAULT_WIDTH;
    private final T target;
    private final Function<T, Integer> getter;

    public GUIStatusBar(T target, Function<T, Integer> getter) {
        this.target = target;
        this.getter = getter;
        color = Color.red;
        maxValue = getter.apply(target);
        currentValue = maxValue;
        bar = new Rectangle(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public void SetPosition(int x, int y) {
        bar.x = x;
        bar.y = y;
    }

    public void SetSize(int w, int h) {
        bar.width = w;
        bar.height = h;
        maxWidth = w;
    }

    public void SetColor(Color c) {
        color = c;
    }

    public void ResizeBar() {
        currentValue = getter.apply(target);
        bar.setBounds(bar.x, bar.y, (int) (maxWidth * currentValue * 1.0 / maxValue), bar.height);
    }

    public void Draw(Graphics g) {
        g.setColor(color);
        g.fillRect(bar.x, bar.y, bar.width, bar.height);
    }

    @Override
    public void OnNotify(GameEvent e) {
        if (!(e.GetType() == GameEvent.GameEventType.CombatEvent))
            return;
        switch ((CombatEvent) e) {
            case STATUS_BAR_RESET:
                //current value becomes new max value
                maxValue = getter.apply(target);
            case STATUS_BAR_UPDATE:
                ResizeBar();
        }
    }
}
