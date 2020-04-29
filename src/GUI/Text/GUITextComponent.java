package GUI.Text;

import java.awt.*;

public interface GUITextComponent {
    void Draw(Graphics g);
    void SetText(String t);
    void SetColor(Color c);
    void SetFontSize(float newSize);
    void SetFont(Font f);
    boolean IsActive();
    void SetActive(boolean active);
}
