package GUI.Text;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GUITextPanel extends GUIText {
    public static final Color DEFAULT_YELLOW_COLOR = new Color(121, 85, 72);
    public static final Color DEFAULT_GREEN_COLOR = new Color(27, 94, 32);
    public static final int DEFAULT_FONT_SIZE = 60;

    private final Rectangle box;
    private final BufferedImage image;

    public GUITextPanel(String text, BufferedImage image, int x, int y, int w, int h) {
        super(text, x + 30, y - h / 2 + 70, DEFAULT_FONT_SIZE, DEFAULT_YELLOW_COLOR);
        this.image = image;
        this.box = new Rectangle(x, y, w, h);
    }

    public GUITextPanel(String text, BufferedImage image, int x, int y, int w, int h, Color color) {
        this(text, image, x, y, w, h);
        SetColor(color);
    }

    public void Draw(Graphics g) {
        g.drawImage(image, box.x, box.y, box.width, box.height, null);
        super.Draw(g);
    }
}
