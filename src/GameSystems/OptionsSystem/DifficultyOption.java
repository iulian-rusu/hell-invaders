package GameSystems.OptionsSystem;

import Assets.Images.GUIAssets;
import Entities.CollidableEntities.Enemies.Dragon;
import Entities.CollidableEntities.Enemies.Enemy;
import Entities.CollidableEntities.Enemies.Monster;
import Entities.Player;
import GUI.GUIButton;
import GUI.GUIText;
import Game.Game;
import Game.GameWindow;
import States.AboutState;

import java.awt.*;
import java.util.ArrayList;

public class DifficultyOption {
    //text parameters
    public static final int DIFFICULTY_TEXT_X = (GameWindow.wndDimension.width - GUIButton.BUTTON_W) / 2 - 175;
    public static final int LINE_SPACING = 50;
    public static final int INFO_FONT_SIZE = AboutState.INFO_FONT_SIZE;
    public static final int DESCRIPTION_FONT_SIZE = INFO_FONT_SIZE + 30;
    public static final int DESCRIPTION_LEFT_X = 300;
    public static final int DESCRIPTION_TOP_Y = 310;
    public static final int INFO_LEFT_X = DESCRIPTION_LEFT_X + 500;
    //button parameters
    public static final int DIFFICULTY_BUTTON_X = DIFFICULTY_TEXT_X + 360;
    public static final int DIFFICULTY_BUTTON_Y = 160;

    private final GUIText difficultyText;
    private final GUIButton difficultyButton;
    private final ArrayList<GUIText> difficultyDescription;

    public DifficultyOption() {
        difficultyButton = new GUIButton(GUIAssets.easy_button, GUIAssets.easy_button_hovered, DIFFICULTY_BUTTON_X, DIFFICULTY_BUTTON_Y,
                GUIButton.BUTTON_W, GUIButton.BUTTON_H);
        difficultyButton.AddActionListener(actionEvent -> NextDifficulty());
        difficultyText = new GUIText("  DIFFICULTY:",
                DIFFICULTY_TEXT_X, DIFFICULTY_BUTTON_Y + 50, DESCRIPTION_FONT_SIZE, AboutState.TEXT_COLOR);
        //description
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
                DESCRIPTION_LEFT_X, DESCRIPTION_TOP_Y + 6 * LINE_SPACING, INFO_FONT_SIZE, Color.RED));
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
        Update();
    }

    public GUIButton GetButtonHandle() {
        return difficultyButton;
    }

    private void NextDifficulty() {
        //update game difficulty (
        Game.DIFFICULTY = (Game.DIFFICULTY) % 3 + 1;
        switch (Game.DIFFICULTY) {
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
        //update description
        Update();
    }

    private void Update() {
        difficultyDescription.get(2).SetText(Monster.GET_DEFAULT_HEALTH() + "  HEALTH  x  " + Enemy.HEALTH_INCREMENT + "  PER LEVEL");
        difficultyDescription.get(4).SetText(Enemy.GET_DEFAULT_DAMAGE() + "  DAMAGE");
        difficultyDescription.get(6).SetText(Dragon.GET_DEFAULT_HEALTH() + "  HEALTH  x  " + Enemy.HEALTH_INCREMENT + "  PER LEVEL");
        difficultyDescription.get(8).SetText(Dragon.GET_DEFAULT_DAMAGE() + "  DAMAGE  x  " + Game.DIFFICULTY +
                ((Game.DIFFICULTY > 1) ? "  PROJECTILES" : "  PROJECTILE"));
        difficultyDescription.get(10).SetText(Player.GET_DEFAULT_HEALTH() + "  HEALTH");
        difficultyDescription.get(12).SetText(Player.GET_DEFAULT_MANA() + "  MANA");
        difficultyDescription.get(14).SetText(Player.GET_DEFAULT_EXPERIENCE_GAIN() + "  XP  x  " + Player.EXPERIENCE_INCREMENT + "  PER LEVEL");
    }

    public void Draw(Graphics g) {
        difficultyText.Draw(g);
        for (GUIText t : difficultyDescription) {
            t.Draw(g);
        }
    }
}
