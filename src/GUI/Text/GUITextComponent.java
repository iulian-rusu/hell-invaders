package GUI.Text;

import java.awt.*;

/**
 * @brief Provides an API for interacting with graphical text elements.
 */
public interface GUITextComponent {
    /**
     * Draws the text on the screen.
     *
     * @param g A Java Graphics object.
     */
    void Draw(Graphics g);

    /**
     * Sets the text content of the object.
     *
     * @param t A Java String representing the new text content.
     */
    void SetText(String t);

    /**
     * Sets the text color.
     *
     * @param c The new color of the text.
     */
    void SetColor(Color c);

    /**
     * Sets the font size of the text.
     *
     * @param newSize The new font size.
     */
    void SetFontSize(float newSize);

    /**
     * Sets the new font of the text.
     *
     * @param f A Java Font object.
     */
    void SetFont(Font f);

    /**
     * Used to check the current status of the object.
     *
     * @return A boolean value indicating if the current object is active.
     */
    boolean IsActive();

    /**
     * Changes the active state of the object.
     *
     * @param active A boolean flag.
     */
    void SetActive(boolean active);
}
