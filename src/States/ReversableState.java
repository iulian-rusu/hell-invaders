package States;

import Assets.GUIAssets;
import GUI.GUIButton;

import java.util.ArrayList;

public abstract class ReversableState extends State {
    protected static final int backButtonX = 25;
    protected static final int backButtonY = 25;
    public ReversableState(){
        allButtons= new ArrayList<>(1);
        allButtons.add(new GUIButton(GUIAssets.back_button,GUIAssets.back_button_hovered,backButtonX,backButtonY,buttonW,buttonH));
    }
}
