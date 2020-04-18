package GUI;

import java.awt.*;

import Assets.FontAssets;

public class GUIText {
    public boolean isActive = true;

    private int frameCount = -1;
    private int lifetime = -1;
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

    public void Draw(Graphics g) {
        Tick();
        if (isActive) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(color);
            g2d.setFont(font);
            g2d.drawString(text, x, y);
        }
    }

    protected void Tick() {
        if (lifetime >= 0 && lifetime <= ++frameCount) {
            isActive = false;
        }
    }

    public void SetText(String t) {
        text = t;
    }

    public void SetDuration(int frames) {
        lifetime = frames;
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

    public void SedX(int x) {
        this.x = x;
    }

    public void SedY(int y) {
        this.y = y;
    }

    public void AddY(int offset){
        this.y+=offset;
    }

    public void AddX(int offset){
        this.x+=offset;
    }

    public int GetX() {
        return x;
    }

    public int GetY() {
        return y;
    }
}
