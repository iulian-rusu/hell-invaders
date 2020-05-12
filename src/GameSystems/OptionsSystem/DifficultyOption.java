package GameSystems.OptionsSystem;

import Assets.Images.GUIAssets;
import Entities.CollidableEntities.Enemies.Dragon;
import Entities.CollidableEntities.Enemies.Enemy;
import Entities.CollidableEntities.Enemies.Monster;
import Entities.Player;
import GUI.GUIButton;
import GUI.Text.GUIText;
import Game.Game;
import Game.GameWindow;
import SQL.DatabaseManager;
import States.AboutState;

import java.awt.*;
import java.util.ArrayList;

/**
 *  @brief Adds the option to change the game difficulty.
 */
public class DifficultyOption implements Option {
    public static final int DIFFICULTY_TEXT_X =
            (GameWindow.SCREEN_DIMENSION.width - GUIButton.BUTTON_W) / 2 - 175;///< The x coordinate of the option title's bottom-left corner.
    public static final int LINE_SPACING = 50;///< The space between lines in the desciption.
    public static final int INFO_FONT_SIZE = AboutState.INFO_FONT_SIZE;///< The font size of the additional information text.
    public static final int BIGGER_INFO_FONT_SIZE = INFO_FONT_SIZE + 30;///< The font size for bigger text.
    public static final int DESCRIPTION_LEFT_X = 300;///< The x coordinate of the description's bottom-left corner.
    public static final int DESCRIPTION_TOP_Y = 310;///< The y coordinate of the description's bottom-left corner.
    public static final int INFO_LEFT_X = DESCRIPTION_LEFT_X + 500;///< The x coordinate of the bottom-left corner of the second column of text.

    public static final int DIFFICULTY_BUTTON_X = DIFFICULTY_TEXT_X + 360;///< The x coordinate of the top-left corner of the button.
    public static final int DIFFICULTY_BUTTON_Y = 160;///< The y coordinate of the top-left corner of the button.

    private final GUIText difficultyText;///< The title text of the option.
    private final GUIButton difficultyButton;///< The button used to change the option.
    private final ArrayList<GUIText> difficultyDescription;///< Additional description of the option.

    /**
     * Constructor without parameters.
     */
    public DifficultyOption() {
        difficultyButton = new GUIButton(GUIAssets.easy_button, GUIAssets.easy_button_hovered, DIFFICULTY_BUTTON_X, DIFFICULTY_BUTTON_Y,
                GUIButton.BUTTON_W, GUIButton.BUTTON_H);
        difficultyButton.AddActionListener(actionEvent -> NextDifficulty());
        difficultyText = new GUIText("  DIFFICULTY:",
                DIFFICULTY_TEXT_X, DIFFICULTY_BUTTON_Y + 50, BIGGER_INFO_FONT_SIZE, AboutState.TEXT_COLOR);
        // Description text
        difficultyDescription = new ArrayList<>();
        difficultyDescription.add(new GUIText("THINGS AFFECTED BY DIFFICULTY: ",
                DIFFICULTY_TEXT_X, DESCRIPTION_TOP_Y, INFO_FONT_SIZE, AboutState.TEXT_COLOR));
        difficultyDescription.add(new GUIText("MONSTER BASE HEALTH: ",
                DESCRIPTION_LEFT_X, DESCRIPTION_TOP_Y + 2 * LINE_SPACING, INFO_FONT_SIZE, Color.ORANGE));
        difficultyDescription.add(new GUIText("",
                INFO_LEFT_X, DESCRIPTION_TOP_Y + 2 * LINE_SPACING, INFO_FONT_SIZE, AboutState.TEXT_COLOR));
        difficultyDescription.add(new GUIText("MONSTER BASE DAMAGE: ",
                DESCRIPTION_LEFT_X, DESCRIPTION_TOP_Y + 3 * LINE_SPACING, INFO_FONT_SIZE, Color.ORANGE));
        difficultyDescription.add(new GUIText("",
                INFO_LEFT_X, DESCRIPTION_TOP_Y + 3 * LINE_SPACING, INFO_FONT_SIZE, AboutState.TEXT_COLOR));
        difficultyDescription.add(new GUIText("DRAGON BASE HEALTH: ",
                DESCRIPTION_LEFT_X, DESCRIPTION_TOP_Y + 4 * LINE_SPACING, INFO_FONT_SIZE, Color.ORANGE));
        difficultyDescription.add(new GUIText("",
                INFO_LEFT_X, DESCRIPTION_TOP_Y + 4 * LINE_SPACING, INFO_FONT_SIZE, AboutState.TEXT_COLOR));
        difficultyDescription.add(new GUIText("DRAGON BASE DAMAGE: ",
                DESCRIPTION_LEFT_X, DESCRIPTION_TOP_Y + 5 * LINE_SPACING, INFO_FONT_SIZE, Color.ORANGE));
        difficultyDescription.add(new GUIText("",
                INFO_LEFT_X, DESCRIPTION_TOP_Y + 5 * LINE_SPACING, INFO_FONT_SIZE, AboutState.TEXT_COLOR));
        difficultyDescription.add(new GUIText("PLAYER HEALTH: ",
                DESCRIPTION_LEFT_X, DESCRIPTION_TOP_Y + 6 * LINE_SPACING, INFO_FONT_SIZE, new Color(253, 47, 47)));
        difficultyDescription.add(new GUIText("",
                INFO_LEFT_X, DESCRIPTION_TOP_Y + 6 * LINE_SPACING, INFO_FONT_SIZE, AboutState.TEXT_COLOR));
        difficultyDescription.add(new GUIText("PLAYER MANA: ",
                DESCRIPTION_LEFT_X, DESCRIPTION_TOP_Y + 7 * LINE_SPACING, INFO_FONT_SIZE, Color.CYAN));
        difficultyDescription.add(new GUIText("",
                INFO_LEFT_X, DESCRIPTION_TOP_Y + 7 * LINE_SPACING, INFO_FONT_SIZE, AboutState.TEXT_COLOR));
        difficultyDescription.add(new GUIText("EXPERIENCE GAIN: ",
                DESCRIPTION_LEFT_X, DESCRIPTION_TOP_Y + 8 * LINE_SPACING, INFO_FONT_SIZE, Color.GREEN));
        difficultyDescription.add(new GUIText("",
                INFO_LEFT_X, DESCRIPTION_TOP_Y + 8 * LINE_SPACING, INFO_FONT_SIZE, AboutState.TEXT_COLOR));

        UpdateButtonImages();
        UpdateDescription();
    }

    /**
     * Updates the game difficulty.
     */
    private void NextDifficulty() {
        Game.difficulty = (Game.difficulty) % 3 + 1;
        DatabaseManager.SaveGameData("Difficulty", Game.difficulty);
        UpdateButtonImages();
        UpdateDescription();
    }

    /**
     * Updates the image of the button to match the current difficulty.
     */
    private void UpdateButtonImages() {
        switch (Game.difficulty) {
            case 1:
                difficultyButton.SetImages(GUIAssets.easy_button, GUIAssets.easy_button_hovered);
                break;
            case 2:
                difficultyButton.SetImages(GUIAssets.medium_button, GUIAssets.medium_button_hovered);
                break;
            case 3:
                difficultyButton.SetImages(GUIAssets.hard_button, GUIAssets.hard_button_hovered);
                break;
        }
    }

    /**
     * Updates the description to match the current difficutly.
     */
    private void UpdateDescription() {
        difficultyDescription.get(2).SetText(Monster.GET_DEFAULT_HEALTH() + "  HEALTH  x  " + Enemy.HEALTH_INCREMENT + "  PER LEVEL");
        difficultyDescription.get(4).SetText(Enemy.GET_DEFAULT_DAMAGE() + "  DAMAGE");
        difficultyDescription.get(6).SetText(Dragon.GET_DEFAULT_HEALTH() + "  HEALTH  x  " + Enemy.HEALTH_INCREMENT + "  PER LEVEL");
        difficultyDescription.get(8).SetText(Dragon.GET_DEFAULT_DAMAGE() + "  DAMAGE  x  " + Game.difficulty +
                ((Game.difficulty > 1) ? "  PROJECTILES" : "  PROJECTILE"));
        difficultyDescription.get(10).SetText(Player.GET_DEFAULT_HEALTH() + "  HEALTH");
        difficultyDescription.get(12).SetText(Player.GET_DEFAULT_MANA() + "  MANA");
        difficultyDescription.get(14).SetText(Player.GET_DEFAULT_EXPERIENCE_GAIN() + "  XP  x  " + Player.EXPERIENCE_INCREMENT + "  PER LEVEL");
    }

    @Override
    public GUIButton GetButtonHandle() {
        return difficultyButton;
    }

    @Override
    public void Draw(Graphics g) {
        difficultyText.Draw(g);
        for (GUIText t : difficultyDescription) {
            t.Draw(g);
        }
    }
}
