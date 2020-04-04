package GUI;

import java.awt.*;

import Assets.FontAssets;

public class GUIText {
    private String text;
    private Font font;
    private Color color;
    private int x, y;

    public GUIText(String text, int x, int y, float size) {
        this.text = text;
        this.x = x;
        this.y = y;
        font = FontAssets.mainFont.deriveFont(size);
        color = Color.WHITE;
    }

    public GUIText(String text, int x, int y, float size, Color c) {
        this(text, x,y, size);
        color = c;
    }

    public void Draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);
        g2d.setFont(font);
        g2d.drawString(text, x, y);
    }

    public void SetPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void SetColor(Color c) {
        this.color = c;
    }

    public void SetFontSize(float newSize) {
        font = font.deriveFont(newSize);
    }

    public void SetFont(Font f) {
        float fontSize = font.getSize();
        font = f.deriveFont(fontSize);
    }

    public int GetX(){return x;}
    public int GetY(){return y;}
}
