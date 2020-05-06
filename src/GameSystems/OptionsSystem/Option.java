package GameSystems.OptionsSystem;

import GUI.GUIButton;

import java.awt.*;

public interface Option {
    void Draw(Graphics g);
    GUIButton GetButtonHandle();
}
