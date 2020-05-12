package GUI;

import Entities.Entity;
import GameSystems.EventSystem.Events.CombatEvent;
import GameSystems.EventSystem.Events.GameEvent;
import GameSystems.EventSystem.Observer;

import java.awt.*;
import java.util.function.Function;

/**
 * @brief A generic class that provides an API for a status bar that tracks some value according to a function.
 *
 * @param <T> A type that extends Entity.
 */
public class GUIStatusBar<T extends Entity> implements Observer {
    public static final int DEFAULT_WIDTH = 70;///< The default width of the bar.
    public static final int DEFAULT_HEIGHT = 5;///< The default height of the bar.
    public static final int DEFAULT_Y_OFFSET = -20;///< The default offset of the bar above the target entity.

    private final Rectangle bar;///< The Rectangle object that defines the boundaries of the bar.
    private Color color;///< The color of the bar.
    private double currentValue;///< The current value of the bar.
    private double maxValue;///< The value corresponding to the full bar.
    private int maxWidth = DEFAULT_WIDTH;///< The width of the bar.
    private final T target;///< The entity whose values is being tracked by the bar.
    private final Function<T, Long> getter;///< A callback function that returns the tracked value from the target.

    /**
     * Constructor with parameters.
     *
     * @param target The target object. Must extend the Entity abstract class.
     * @param getter A Function<T, Long> object that receives a lambda expression used as a callback function.
     * @see java.util.function.Function;
     */
    public GUIStatusBar(T target, Function<T, Long> getter) {
        this.target = target;
        this.getter = getter;
        color = Color.RED;
        maxValue = getter.apply(target);
        currentValue = maxValue;
        bar = new Rectangle(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * Sets the position of the status bar.
     *
     * @param x The new x coordinate.
     * @param y The new y coordinate.
     */
    public void SetPosition(int x, int y) {
        bar.x = x;
        bar.y = y;
    }

    /**
     * Sets the new bounds of the status bar.
     *
     * @param w The new width.
     * @param h The new height.
     */
    public void SetSize(int w, int h) {
        bar.width = w;
        bar.height = h;
        maxWidth = w;
    }

    /**
     * Sets the color of the status bar.
     *
     * @param c The new color of the bar.
     */
    public void SetColor(Color c) {
        color = c;
    }

    /**
     * Updates the bounds of the bar when the current value changes.
     */
    public void ResizeBar() {
        currentValue = getter.apply(target);
        bar.setBounds(bar.x, bar.y, (int) (maxWidth * currentValue * 1.0 / maxValue), bar.height);
    }

    /**
     * Draws the status bar on the screen.
     *
     * @param g A java Graphics object.
     */
    public void Draw(Graphics g) {
        g.setColor(color);
        g.fillRect(bar.x, bar.y, bar.width, bar.height);
    }

    @Override
    public void OnNotify(GameEvent e) {
        if (!(e instanceof CombatEvent))
            return;
        switch ((CombatEvent) e) {
            case STATUS_BAR_RESET:
                // Current value becomes new max value
                maxValue = getter.apply(target);
            case STATUS_BAR_UPDATE:
                ResizeBar();
        }
    }
}
