package GUI.Text;

import Assets.FontAssets;

import java.awt.*;

/**
 * @brief Provides an API for a simple text object.
 */
public class GUIText implements GUITextComponent {
    protected boolean isActive = true;///< Boolean flag that indicates whether the text is active.
    protected String text;///< A string containing the actual text.
    protected Font font;///< The font of the text.
    protected Color color;///< The color of the text.
    protected int x;///< The x coodrinate of the text.
    protected int y;///< The y coordinate of the text.

    /**
     * Constructor with parameters.
     *
     * @param text A String object containing the actual text.
     * @param x    The x coordinate.
     * @param y    The y coordinate.
     * @param size The font size.
     */
    public GUIText(String text, int x, int y, float size) {
        this.text = text;
        this.x = x;
        this.y = y;
        font = FontAssets.mainFont.deriveFont(size);
        color = Color.WHITE;
    }

    /**
     * Constructor with parameters.
     *
     * @param text A String object containing the actual text.
     * @param x    The x coordinate.
     * @param y    The y coordinate.
     * @param size The font size.
     * @param c    The color of the text.
     */
    public GUIText(String text, int x, int y, float size, Color c) {
        this(text, x, y, size);
        color = c;
    }

    @Override
    public void Draw(Graphics g) {
        if (isActive) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(color);
            g2d.setFont(font);
            g2d.drawString(text, x, y);
        }
    }

    @Override
    public void SetText(String t) {
        text = t;
    }

    @Override
    public void SetColor(Color c) {
        this.color = c;
    }

    @Override
    public void SetFontSize(float newSize) {
        font = font.deriveFont(newSize);
    }

    @Override
    public void SetFont(Font f) {
        float fontSize = font.getSize();
        font = f.deriveFont(fontSize);
    }

    @Override
    public boolean IsActive() {
        return isActive;
    }

    @Override
    public void SetActive(boolean active) {
        isActive = active;
    }
}
