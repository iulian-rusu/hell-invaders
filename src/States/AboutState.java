package States;

import Assets.Images.BackgroundAssets;
import GUI.GUIButton;
import GUI.Text.GUIText;

import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

/**
 * @brief Implements the "About" page of the game.
 */
public class AboutState extends ReversibleState {
    public static final int LINE_SPACING = 50;///< Spacing between text lines.
    public static final int DESCRIPTION_FONT_SIZE = 75;///< Font size of game description.
    public static final int INFO_FONT_SIZE = DESCRIPTION_FONT_SIZE - 10;///< Smaller font size for other information.
    public static final int DESCRIPTION_LEFT_X = 160;///< The x coordinatete of the left side of the description text.
    public static final int DESCRIPTION_TOP_Y = 150;///< The y coordiante of the top of the description text.
    public static final int INFO_LEFT_X = DESCRIPTION_LEFT_X + 70;///< The x coordinate of the left side of additional info text.
    public static final int INFO_RIGHT_X = INFO_LEFT_X + 220;///< The x coordinate of the right side of additional info text.
    public static final Color TEXT_COLOR = new Color(255, 252, 241);///< The default text color.

    public static final int MAX_SCROLL_OFFSET = 470;///< The maximum amount the user can scroll down.
    public static final int SCROLL_SENSIBILITY = 15;///< The speed at which the page scrolls.

    private final ArrayList<GUIText> desciption;///< The game description.
    private int scrollOffset;///< The current scroll amount of the page.

    /**
     * Constructor without parameters.
     */
    public AboutState() {
        // Back button
        allButtons.get(0).AddActionListener(actionEvent -> StateManager.GetInstance().SetCurrentState(StateIndex.MENU_STATE));
        // Description contains all lines of the about text
        desciption = new ArrayList<>(30);
        AddGameplayDescription();
        AddMechanicsDescription();
        AddCredits();
    }

    @Override
    public void Init() {
        super.Init();
        for (GUIButton button : allButtons) {
            button.Translate(0, scrollOffset);
        }
        scrollOffset = 0;
    }

    @Override
    public void Draw(Graphics2D g2d) {
        g2d.drawImage(BackgroundAssets.bgGameDark, 0, 0, null);
        g2d.translate(0, -scrollOffset);
        for (GUIText text : desciption) {
            text.Draw(g2d);
        }
        g2d.translate(0, scrollOffset);
        for (GUIButton b : allButtons) {
            b.Draw(g2d);
        }
    }

    @Override
    public void MouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
        int before = this.scrollOffset;
        this.scrollOffset += SCROLL_SENSIBILITY * mouseWheelEvent.getWheelRotation();
        // Check bounds
        this.scrollOffset = Math.min(MAX_SCROLL_OFFSET, this.scrollOffset);
        this.scrollOffset = Math.max(0, this.scrollOffset);
        // Update button positions
        int delta = scrollOffset - before;
        for (GUIButton button : allButtons) {
            button.Translate(0, -delta);
            button.MouseMoved(mouseWheelEvent.getPoint());
        }
    }

    /**
     * Adds some gameplay desciprion to the "About" page.
     */
    private void AddGameplayDescription() {
        desciption.add(new GUIText("    'HELL INVADERS'",
                DESCRIPTION_LEFT_X, DESCRIPTION_TOP_Y, DESCRIPTION_FONT_SIZE, Color.RED));
        desciption.add(new GUIText(" IS A GAME ABOUT SURVIVING AN INVASION",
                DESCRIPTION_LEFT_X + 385, DESCRIPTION_TOP_Y, DESCRIPTION_FONT_SIZE, TEXT_COLOR));
        desciption.add(new GUIText("OF DEMONS FROM HELL (IF THAT WASN'T OBVIOUS).",
                DESCRIPTION_LEFT_X, DESCRIPTION_TOP_Y + LINE_SPACING, DESCRIPTION_FONT_SIZE, TEXT_COLOR));
        desciption.add(new GUIText("ENEMIES WILL BECOME STRONGER EACH DAY ,  BUT THEY WILL",
                DESCRIPTION_LEFT_X, DESCRIPTION_TOP_Y + 2 * LINE_SPACING, DESCRIPTION_FONT_SIZE, TEXT_COLOR));
        desciption.add(new GUIText("ALSO DROP MORE EXPERIENCE FOR ABILITY UPGRADES. ",
                DESCRIPTION_LEFT_X, DESCRIPTION_TOP_Y + 3 * LINE_SPACING, DESCRIPTION_FONT_SIZE, TEXT_COLOR));
    }

    /**
     * Adds the description of the mechanics of the game.
     */
    private void AddMechanicsDescription() {
        desciption.add(new GUIText("CONTROLS:",
                INFO_LEFT_X, DESCRIPTION_TOP_Y + 5 * LINE_SPACING, INFO_FONT_SIZE, Color.ORANGE));
        desciption.add(new GUIText("MOUSE ONLY  -  CLICK ON THE MAP TO SHOOT PROJECTILES",
                INFO_RIGHT_X, DESCRIPTION_TOP_Y + 5 * LINE_SPACING, INFO_FONT_SIZE, TEXT_COLOR));
        desciption.add(new GUIText("ENEMIES:",
                INFO_LEFT_X, DESCRIPTION_TOP_Y + 6 * LINE_SPACING, INFO_FONT_SIZE, Color.ORANGE));
        desciption.add(new GUIText("MONSTER  -  ATTACKS FROM MELEE RANGE",
                INFO_RIGHT_X, DESCRIPTION_TOP_Y + 6 * LINE_SPACING, INFO_FONT_SIZE, TEXT_COLOR));
        desciption.add(new GUIText("DRAGON  -  ATTACKS FROM LONG RANGE",
                INFO_RIGHT_X, DESCRIPTION_TOP_Y + 7 * LINE_SPACING, INFO_FONT_SIZE, TEXT_COLOR));
        desciption.add(new GUIText("SPELLS:",
                INFO_LEFT_X, DESCRIPTION_TOP_Y + 8 * LINE_SPACING, INFO_FONT_SIZE, Color.ORANGE));
        desciption.add(new GUIText("FIRE",
                INFO_RIGHT_X, DESCRIPTION_TOP_Y + 8 * LINE_SPACING, INFO_FONT_SIZE, new Color(255, 151, 28)));
        desciption.add(new GUIText("  -  NO SPECIAL ABILITIY",
                INFO_RIGHT_X + 75, DESCRIPTION_TOP_Y + 8 * LINE_SPACING, INFO_FONT_SIZE, TEXT_COLOR));
        desciption.add(new GUIText("FROST",
                INFO_RIGHT_X, DESCRIPTION_TOP_Y + 9 * LINE_SPACING, INFO_FONT_SIZE, new Color(0, 255, 247)));
        desciption.add(new GUIText("  -  HITS SLOW ENEMIES",
                INFO_RIGHT_X + 115, DESCRIPTION_TOP_Y + 9 * LINE_SPACING, INFO_FONT_SIZE, TEXT_COLOR));
        desciption.add(new GUIText("ARCANE",
                INFO_RIGHT_X, DESCRIPTION_TOP_Y + 10 * LINE_SPACING, INFO_FONT_SIZE, Color.MAGENTA));
        desciption.add(new GUIText("  -  CRITS DEAL 4x DAMAGE",
                INFO_RIGHT_X + 150, DESCRIPTION_TOP_Y + 10 * LINE_SPACING, INFO_FONT_SIZE, TEXT_COLOR));
        desciption.add(new GUIText("LEVELS:",
                INFO_LEFT_X, DESCRIPTION_TOP_Y + 11 * LINE_SPACING, INFO_FONT_SIZE, Color.ORANGE));
        desciption.add(new GUIText("THERE ARE 365 LEVELS  (DAYS)",
                INFO_RIGHT_X, DESCRIPTION_TOP_Y + 11 * LINE_SPACING, INFO_FONT_SIZE, TEXT_COLOR));
        desciption.add(new GUIText("EACH DAY CAN BE PLAYED ON 3 DIFFICULTIES",
                INFO_RIGHT_X, DESCRIPTION_TOP_Y + 12 * LINE_SPACING, INFO_FONT_SIZE, TEXT_COLOR));
    }

    /**
     * Adds game credits.
     */
    private void AddCredits() {
        desciption.add(new GUIText("CREDITS: ",
                INFO_LEFT_X + 450, DESCRIPTION_TOP_Y + 14 * LINE_SPACING, INFO_FONT_SIZE, Color.ORANGE));
        desciption.add(new GUIText("GAME IDEA:",
                INFO_LEFT_X, DESCRIPTION_TOP_Y + 15 * LINE_SPACING, 50, Color.ORANGE));
        desciption.add(new GUIText("IULIAN RUSU ,  AC TUIASI 2020 ",
                INFO_RIGHT_X, DESCRIPTION_TOP_Y + 15 * LINE_SPACING, 50, Color.LIGHT_GRAY));
        desciption.add(new GUIText("SOURCE CODE:",
                INFO_LEFT_X, DESCRIPTION_TOP_Y + 16 * LINE_SPACING, 50, Color.ORANGE));
        desciption.add(new GUIText("IULIAN RUSU  -  https://github.com/iulian-rusu/hell-invaders",
                INFO_RIGHT_X, DESCRIPTION_TOP_Y + 16 * LINE_SPACING, 50, Color.LIGHT_GRAY));
        desciption.add(new GUIText("TEXTURES:",
                INFO_LEFT_X, DESCRIPTION_TOP_Y + 17 * LINE_SPACING, 50, Color.ORANGE));
        desciption.add(new GUIText("IULIAN RUSU ,  GOOGLE IMAGE SEARCH ",
                INFO_RIGHT_X, DESCRIPTION_TOP_Y + 17 * LINE_SPACING, 50, Color.LIGHT_GRAY));
        desciption.add(new GUIText("MUSIC:",
                INFO_LEFT_X, DESCRIPTION_TOP_Y + 18 * LINE_SPACING, 50, Color.ORANGE));
        desciption.add(new GUIText("DEATH  -  LEPROSY ( 8 BIT ) ,  © 1988  COMBAT RECORDS",
                INFO_RIGHT_X, DESCRIPTION_TOP_Y + 18 * LINE_SPACING, 50, Color.LIGHT_GRAY));
        desciption.add(new GUIText("DEATH  -  ALTERING THE FUTURE ( 8 BIT ) ,  © 1990  COMBAT RECORDS",
                INFO_RIGHT_X, DESCRIPTION_TOP_Y + 19 * LINE_SPACING, 50, Color.LIGHT_GRAY));
        desciption.add(new GUIText("DEATH  -  WITHIN THE MIND ( 8 BIT ) ,  © 1990  COMBAT RECORDS",
                INFO_RIGHT_X, DESCRIPTION_TOP_Y + 20 * LINE_SPACING, 50, Color.LIGHT_GRAY));
        desciption.add(new GUIText("DEATH  -  MISANTHROPE ( 8 BIT ) ,  © 1995  ROADRUNNER RECORDS",
                INFO_RIGHT_X, DESCRIPTION_TOP_Y + 21 * LINE_SPACING, 50, Color.LIGHT_GRAY));
        desciption.add(new GUIText("SOUND EFFECTS FROM freesound.org",
                INFO_RIGHT_X, DESCRIPTION_TOP_Y + 22 * LINE_SPACING, 50, Color.LIGHT_GRAY));
        desciption.add(new GUIText("COPYRIGHT © 2020 IULIAN RUSU .  ALL RIGHTS RESERVED.",
                INFO_RIGHT_X + 35, DESCRIPTION_TOP_Y + 23 * LINE_SPACING, 35, Color.LIGHT_GRAY));
    }
}
