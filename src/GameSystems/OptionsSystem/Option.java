package GameSystems.OptionsSystem;

import GUI.GUIButton;

import java.awt.*;

/**
 * @brief Provides an interface for adding options to the "Options" page of the game.
 */
public interface Option {
    /**
     * Draws the option description on the screen.
     *
     * @param g A Java Graphics object.
     */
    void Draw(Graphics g);

    /**
     * Called to save the option to the database.
     */
    void SaveData();

    /**
     * Returns a reference to the button that changes the option state.
     *
     * @return A GUIButton object that is responsible for interacting with the option.
     */
    GUIButton GetButtonHandle();
}
