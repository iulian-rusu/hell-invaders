package GUI.Text;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @brief Extends the GUIText class by adding a background panel for the text.
 */
public class GUITextPanel extends GUIText {
    public static final Color DEFAULT_YELLOW_COLOR = new Color(121, 85, 72);///< The default shade for the yellow panel.
    public static final Color DEFAULT_GREEN_COLOR = new Color(27, 94, 32);///< The default shade for the green panel.
    public static final int DEFAULT_FONT_SIZE = 60;///< The default font size.

    private final Rectangle box;///< The Rectangle object that defines the bounds of the panel.
    private final BufferedImage image;///< The image representing the panel.

    /**
     * Constructor with parameters.
     *
     * @param text  A String object containing the actual text.
     * @param image The background image.
     * @param x     The x coordinate.
     * @param y     The y coordinate.
     * @param w     The width of the panel.
     * @param h     The height of the panel.
     */
    public GUITextPanel(String text, BufferedImage image, int x, int y, int w, int h) {
        super(text, x + 30, y - h / 2 + 70, DEFAULT_FONT_SIZE, DEFAULT_YELLOW_COLOR);
        this.image = image;
        this.box = new Rectangle(x, y, w, h);
    }

    /**
     * Constructor with parameters.
     *
     * @param text  A String object containing the actual text.
     * @param image The background image.
     * @param x     The x coordinate.
     * @param y     The y coordinate.
     * @param w     The width of the panel.
     * @param h     The height of the panel.
     * @param color The color of the text.
     */
    public GUITextPanel(String text, BufferedImage image, int x, int y, int w, int h, Color color) {
        this(text, image, x, y, w, h);
        SetColor(color);
    }

    @Override
    public void Draw(Graphics g) {
        g.drawImage(image, box.x, box.y, box.width, box.height, null);
        super.Draw(g);
    }
}
