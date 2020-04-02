package GUI;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GUITextPanel {
    public static final Color DEFAULT_YELLOW_COLOR = new Color(121, 85, 72);
    public static final Color DEFAULT_GREEN_COLOR = new Color(27, 94, 32);
    private static final int DEFAULT_FONT_SIZE = 70;

    private Rectangle box;
    private BufferedImage image;
    private GUIText text;

    public GUITextPanel(String text, BufferedImage image, int x, int y, int w, int h) {
        this.image = image;
        this.box = new Rectangle(x, y, w, h);
        int yOffset = y - h / 2 + 90;
        int xOffset = x + 30;
        this.text = new GUIText(text, xOffset, yOffset, DEFAULT_FONT_SIZE, DEFAULT_YELLOW_COLOR);
    }

    public GUITextPanel(String text, BufferedImage image, int x, int y, int w, int h, Color color) {
        this(text, image, x, y, w, h);
        this.text.SetColor(color);
    }

    public void Draw(Graphics g) {
        g.drawImage(image, box.x, box.y, box.width, box.height, null);
        text.Draw(g);
    }

}
