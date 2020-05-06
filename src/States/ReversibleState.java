package States;

import Assets.Images.GUIAssets;
import GUI.GUIButton;

import java.util.ArrayList;

/*! \class ReversibleState
    \brief Represents a state from which you can return to the previous game state.

 */
public abstract class ReversibleState extends State {
    public static final int BACK_BUTTON_X = 25;///< The x coordinate of the "Back" button.
    public static final int BACK_BUTTON_Y = 25;///< The y coordinate of the "Back" button.

    /*! \fn  public ReversibleState()
        \brief Constructor that creates the state and adds a "Back" button.
         All reversible states have a "Back" button in the top left corner.
     */
    public ReversibleState(){
        allButtons= new ArrayList<>(1);
        allButtons.add(new GUIButton(GUIAssets.back_button,GUIAssets.back_button_hovered,
                BACK_BUTTON_X, BACK_BUTTON_Y, GUIButton.BUTTON_W, GUIButton.BUTTON_H));
    }
}
