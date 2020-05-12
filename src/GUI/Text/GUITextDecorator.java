package GUI.Text;

import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 *  @brief A decorator used to dynamically change text properties at runtime.
 */
public abstract class GUITextDecorator implements GUITextComponent {
    protected GUITextComponent target;///< The target GUITextComponent object.

    /**
     * Constructor with parameters.
     *
     * @param target A non-null object representing the object to decorate.
     */
    public GUITextDecorator(@NotNull GUITextComponent target) {
        this.target = target;
    }

    @Override
    public void Draw(Graphics g) {
        target.Draw(g);
    }

    @Override
    public void SetText(String t) {
        target.SetText(t);
    }

    @Override
    public void SetColor(Color c) {
        target.SetColor(c);
    }

    @Override
    public void SetFontSize(float newSize) {
        target.SetFontSize(newSize);
    }

    @Override
    public void SetFont(Font f) {
        target.SetFont(f);
    }

    @Override
    public boolean IsActive() {
        return target.IsActive();
    }

    @Override
    public void SetActive(boolean active) {
        target.SetActive(active);
    }
}
