package GUI.Text;

import java.awt.*;

import Assets.FontAssets;

public class GUIText implements GUITextComponent{
    protected boolean isActive=true;
    protected String text;
    protected Font font;
    protected Color color;
    protected int x, y;

    public GUIText(String text, int x, int y, float size) {
        this.text = text;
        this.x = x;
        this.y = y;
        font = FontAssets.mainFont.deriveFont(size);
        color = Color.WHITE;
    }

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
        isActive=active;
    }
}