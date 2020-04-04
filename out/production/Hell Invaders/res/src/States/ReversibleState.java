package States;

import Assets.GUIAssets;
import GUI.GUIButton;

import java.util.ArrayList;

public abstract class ReversibleState extends State {
    protected static final int BACK_BUTTON_X = 25;
    protected static final int BACK_BUTTON_Y = 25;
    public ReversibleState(){
        allButtons= new ArrayList<>(1);
        //all reversible states have a back button in the top left corner
        allButtons.add(new GUIButton(GUIAssets.back_button,GUIAssets.back_button_hovered, BACK_BUTTON_X, BACK_BUTTON_Y, BUTTON_W, BUTTON_H));
    }
}
