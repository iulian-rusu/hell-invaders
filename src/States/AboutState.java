package States;

import GUI.GUIButton;
import GUI.GUIText;
import Game.GameWindow;
import Assets.Images.BackgroundAssets;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class AboutState extends ReversibleState {
    //text parameters
    public static final int LINE_SPACING = 50;
    public static final int DESCRIPTION_FONT_SIZE = 75;
    public static final int INFO_FONT_SIZE = DESCRIPTION_FONT_SIZE - 10;
    public static final int DESCRIPTION_LEFT_X = 150;
    public static final int DESCRIPTION_TOP_Y = 150;
    public static final int INFO_LEFT_X = DESCRIPTION_LEFT_X + 300;
    public static final int INFO_RIGHT_X = DESCRIPTION_LEFT_X + 80;
    public static final Color TEXT_COLOR = new Color(255, 252, 224);

    private final ArrayList<GUIText> desciption;

    public AboutState() {
        //back button
        allButtons.get(0).AddActionListener(actionEvent -> StateManager.GetInstance().SetCurrentState(StateManager.StateIndex.MENU_STATE));
        //description contains all lines of the about text
        desciption = new ArrayList<>(20);
        desciption.add(new GUIText("    'HELL INVADERS'",
                DESCRIPTION_LEFT_X, DESCRIPTION_TOP_Y, DESCRIPTION_FONT_SIZE, Color.RED));
        desciption.add(new GUIText(" IS A GAME ABOUT SURVIVING AN INVASION",
                DESCRIPTION_LEFT_X + 385, DESCRIPTION_TOP_Y, DESCRIPTION_FONT_SIZE, TEXT_COLOR));
        desciption.add(new GUIText("OF DEMONS FROM HELL (IF THAT WASN'T OBVIOUS).",
                DESCRIPTION_LEFT_X, DESCRIPTION_TOP_Y + LINE_SPACING, DESCRIPTION_FONT_SIZE, TEXT_COLOR));
        desciption.add(new GUIText("EACH DAY,  STRONGER ENEMIES WILL COME. BE PREPARED.",
                DESCRIPTION_LEFT_X , DESCRIPTION_TOP_Y + 2 * LINE_SPACING, DESCRIPTION_FONT_SIZE, TEXT_COLOR));
        desciption.add(new GUIText("EACH DAY,  YOU WILL HAVE TO UPGRADE YOUR ABILITIES.",
                DESCRIPTION_LEFT_X ,DESCRIPTION_TOP_Y + 3 * LINE_SPACING, DESCRIPTION_FONT_SIZE, TEXT_COLOR));
        desciption.add(new GUIText("CONTROLS:",
                INFO_RIGHT_X, DESCRIPTION_TOP_Y + 5 * LINE_SPACING, INFO_FONT_SIZE, Color.ORANGE));
        desciption.add(new GUIText("MOUSE ONLY  -  CLICK ON THE MAP TO SHOOT PROJECTILES",
                INFO_LEFT_X, DESCRIPTION_TOP_Y + 5 * LINE_SPACING, INFO_FONT_SIZE, TEXT_COLOR));
        desciption.add(new GUIText("ENEMIES:",
                INFO_RIGHT_X, DESCRIPTION_TOP_Y + 6 * LINE_SPACING, INFO_FONT_SIZE, Color.ORANGE));
        desciption.add(new GUIText("MONSTER  -  ATTACKS FROM MELEE RANGE",
                INFO_LEFT_X, DESCRIPTION_TOP_Y + 6 * LINE_SPACING, INFO_FONT_SIZE, TEXT_COLOR));
        desciption.add(new GUIText("DRAGON  -  ATTACKS FROM LONG RANGE",
                INFO_LEFT_X, DESCRIPTION_TOP_Y + 7 * LINE_SPACING, INFO_FONT_SIZE, TEXT_COLOR));
        desciption.add(new GUIText("SPELLS:",
                INFO_RIGHT_X, DESCRIPTION_TOP_Y + 8 * LINE_SPACING, INFO_FONT_SIZE, Color.ORANGE));
        desciption.add(new GUIText("FIRE",
                INFO_LEFT_X, DESCRIPTION_TOP_Y + 8 * LINE_SPACING, INFO_FONT_SIZE, new Color(255, 151, 28)));
        desciption.add(new GUIText("  -  NO SPECIAL ABILITIES",
                INFO_LEFT_X + 75, DESCRIPTION_TOP_Y + 8 * LINE_SPACING, INFO_FONT_SIZE, TEXT_COLOR));
        desciption.add(new GUIText("FROST",
                INFO_LEFT_X, DESCRIPTION_TOP_Y + 9 * LINE_SPACING, INFO_FONT_SIZE, new Color(0, 219, 255)));
        desciption.add(new GUIText("  -  HITS SLOW ENEMIES",
                INFO_LEFT_X + 115, DESCRIPTION_TOP_Y + 9 * LINE_SPACING, INFO_FONT_SIZE, TEXT_COLOR));
        desciption.add(new GUIText("ARCANE",
                INFO_LEFT_X, DESCRIPTION_TOP_Y + 10 * LINE_SPACING, INFO_FONT_SIZE, Color.MAGENTA));
        desciption.add(new GUIText("  -  CRITS DEAL 4X DAMAGE",
                INFO_LEFT_X + 150, DESCRIPTION_TOP_Y + 10 * LINE_SPACING, INFO_FONT_SIZE, TEXT_COLOR));
        desciption.add(new GUIText("LEVELS:",
                INFO_RIGHT_X, DESCRIPTION_TOP_Y + 11 * LINE_SPACING, INFO_FONT_SIZE, Color.ORANGE));
        desciption.add(new GUIText("THERE ARE 365 LEVELS  (DAYS)",
                INFO_LEFT_X, DESCRIPTION_TOP_Y + 11 * LINE_SPACING, INFO_FONT_SIZE, TEXT_COLOR));
        desciption.add(new GUIText("EACH DAY CAN BE PLAYED ON 3 DIFFICULTIES",
                INFO_LEFT_X, DESCRIPTION_TOP_Y + 12 * LINE_SPACING, INFO_FONT_SIZE, TEXT_COLOR));
        desciption.add(new GUIText("CREDITS: IULIAN RUSU ,  AC TUIASI 2020",
                DESCRIPTION_LEFT_X + 300, DESCRIPTION_TOP_Y + 14 * LINE_SPACING, 50, Color.LIGHT_GRAY));
    }

    @Override
    public void Draw(GameWindow wnd) {
        BufferStrategy bs = wnd.GetCanvas().getBufferStrategy();
        Graphics g = bs.getDrawGraphics();
        g.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());
        g.drawImage(BackgroundAssets.bg_game_dark, 0, 0, null);
        for (GUIButton b : allButtons) {
            b.Draw(g);
        }
        for (GUIText text : desciption) {
            text.Draw(g);
        }
        bs.show();
        g.dispose();
    }
}
