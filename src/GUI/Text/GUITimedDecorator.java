package GUI.Text;

import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * @brief Adds the ability to create timed text that becomes inactive after a period of time.
 */
public class GUITimedDecorator extends GUITextDecorator {
    private final int lifetime;///< The number of frames while the text is active.
    private int frameCount = -1;///< Counts the duration of the text.

    /**
     * Constructor with parameters.
     *
     * @param target   A (non-null) object representing the object to decorate.
     * @param lifetime The duration of the text.
     */
    public GUITimedDecorator(@NotNull GUITextComponent target, int lifetime) {
        super(target);
        this.lifetime = lifetime;
    }

    /**
     * Called each frame. Increments the frame count and makes the text inactive if needed.
     */
    private void Tick() {
        if (lifetime >= 0 && lifetime <= ++frameCount) {
            target.SetActive(false);
        }
    }

    @Override
    public void Draw(Graphics g) {
        Tick();
        target.Draw(g);
    }
}
