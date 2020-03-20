package GUI;

import java.awt.*;
import Assets.FontAssets;

public class GUIText implements GUIElement {
    private String text;
    private Font font;
    private Color color;
    private int x,y;

    public GUIText(String text, float size, Color c){
        this(text,0,0,size);
        color=c;
    }
    public GUIText(String text, float size){
        this(text,0,0,size);
    }
    public GUIText(String text, int x, int y,float size) {
        this.text = text;
        this.x=x;
        this.y=y;
        font=FontAssets.mainFont.deriveFont(size);
        color=Color.WHITE;
    }

    public void SetPosition(int x, int y){
        this.x=x;
        this.y=y;
    }

    public void SetColor(Color c){
        this.color=c;
    }

    public void SetFontSize(float newSize){
        font=font.deriveFont(newSize);
    }

    public void SetFont(Font f){
        float fontSize=font.getSize();
        font=f.deriveFont(fontSize);
    }

    public float GetFontSize(){
        return font.getSize();
    }

    public String GetText(){
        return text;
    }

    @Override
    public void Init() {

    }

    @Override
    public void Draw(Graphics g) {
        Graphics2D g2d=(Graphics2D)g;
        g2d.setColor(color);
        g2d.setFont(font);
        g2d.drawString(text,x,y);
    }
}
