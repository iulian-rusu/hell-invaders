package States;

import Assets.Images.BackgroundAssets;
import Entities.CollidableEntities.CollisionManager;
import Entities.CollidableEntities.Enemies.Enemy;
import Entities.CollidableEntities.Projectiles.Projectile;
import Entities.Player;
import GUI.GUIButton;
import GUI.Text.GUIText;
import GUI.Text.GUITextComponent;
import Game.GameWindow;
import Game.GlobalReferences;
import GameSystems.EventSystem.Events.AudioEvent;
import GameSystems.EventSystem.Events.CombatEvent;
import GameSystems.EventSystem.Events.GameEvent;
import GameSystems.LevelSystem.LevelLoader;
import GameSystems.UpgradeSystem.ExperiencePanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @brief Implements the gameplay.
 */
public class PlayState extends ReversibleState implements GameSystems.EventSystem.Observer {
    public static final int BATTLEFIELD_Y = 180;///< The y coordinate whenre the battlefield starts.
    public static final int BATTLEFIELD_HEIGHT = 250;///< The height of the battlefield.

    private final Player player;///< A reference to the player object.
    private final ExperiencePanel experiencePanel;///< A reference to the experience panel.
    private final ArrayList<Enemy> allEnemies;///< List that holds all active enemies.
    private final ArrayList<Projectile> allProjectiles;///< List that holds all active projectiles.
    private final ArrayList<Projectile> projectilesToAdd;///< List of projectiles that will be added in the next Update() call.
    private final ArrayList<GUITextComponent> allCombatText;///< List that holds all text spawned during combat.
    private final Rectangle clickBox;///< Rectangle that defines the battlefield.
    private boolean isLevelWon;///< Flag that indicates whether the player has won the level.


    /**
     * Constructor without parameters.
     */
    public PlayState() {
        // Back button
        allButtons.get(0).AddActionListener(actionEvent -> {
            NotifyAllObservers(AudioEvent.STOP_CURRENT_STATE_MUSIC);
            NotifyAllObservers(AudioEvent.STOP_ALL_SFX);
            StateManager.GetInstance().SetCurrentState(StateIndex.UPGRADE_STATE);

        });
        // Create Enemy and Projectile lists
        allEnemies = new ArrayList<>() {
            // Sort enemies by y coordinate
            public boolean add(Enemy newEnemy) {
                int index = Collections.binarySearch(this, newEnemy);
                if (index < 0)
                    index = ~index;
                super.add(index, newEnemy);
                return true;
            }
        };
        allProjectiles = new ArrayList<>();
        projectilesToAdd = new ArrayList<>();
        allCombatText = new ArrayList<>();

        // Get player
        player = GlobalReferences.GetPlayer();
        player.AddObserver(this);
        experiencePanel = GlobalReferences.GetExperiencePanel();

        // Clickable field to fire projectiles
        Dimension screenSize = GameWindow.SCREEN_DIMENSION;
        clickBox = new Rectangle(200, 100, screenSize.width - 200, screenSize.height - 100);
    }

    @Override
    public void Init() {
        super.Init();
        NotifyAllObservers(AudioEvent.PLAY_CURRENT_STATE_MUSIC);
        isLevelWon = false;
        player.Init();
        // Load enemy waves
        LevelLoader.InitLevel(allProjectiles, allEnemies, player.GetLevel());
        allCombatText.clear();
    }

    @Override
    public void Update() {
        super.Update();
        // Add new projectiles if necessary
        if (projectilesToAdd.size() > 0) {
            allProjectiles.addAll(projectilesToAdd);
            projectilesToAdd.clear();
        }
        allEnemies.forEach(Enemy::Update);
        allProjectiles.forEach(Projectile::Update);
        player.Update();
        // Check for collisions and eventually delete inactive entities
        CleanCombatText();
        allCombatText.addAll(CollisionManager.Update(allEnemies, allProjectiles));

        CheckIfFinished();
    }

    /**
     * Checks if the current level has finished.
     */
    private void CheckIfFinished() {
        if (secondCount > 0 && allEnemies.size() == 0) {
            Finish();
        }
    }

    /**
     * Finalizes the current level and transitions the game to the required state.
     */
    private void Finish() {
        isLevelWon = player.GetHealth() > 0;
        if (isLevelWon) {
            NotifyAllObservers(AudioEvent.PLAY_WIN_SFX);
        }
        NotifyAllObservers(AudioEvent.STOP_CURRENT_STATE_MUSIC);
        NotifyAllObservers(AudioEvent.STOP_ALL_SFX);
        if (isLevelWon) {
            player.SetLevel(player.GetLevel() + 1);
            StateManager.GetInstance().SetCurrentState(StateIndex.WIN_STATE);
        } else {
            StateManager.GetInstance().SetCurrentState(StateIndex.LOSS_STATE);
        }
    }

    /**
     * Deletes text that is no longer active.
     */
    private void CleanCombatText() {
        allCombatText.removeIf(text -> !text.IsActive());
    }

    @Override
    public void Draw(Graphics2D g2d) {
        g2d.drawImage(BackgroundAssets.bgGameNormal, 0, 0, null);
        for (Enemy enemy : allEnemies) {
            enemy.Draw(g2d);
        }
        for (Projectile projectile : allProjectiles) {
            projectile.Draw(g2d);
        }
        for (GUITextComponent textComponent : allCombatText) {
            textComponent.Draw(g2d);
        }
        player.Draw(g2d);
        experiencePanel.Draw(g2d);
        for (GUIButton button : allButtons) {
            button.Draw(g2d);
        }
        // Draw the same information as in UpgradeState
        for (GUIText text : UpgradeState.infoText) {
            text.Draw(g2d);
        }
    }

    @Override
    public void MousePressed(Point pressPoint) {
        super.MousePressed(pressPoint);
        if (clickBox.contains(pressPoint)) {
            Projectile[] newProjectiles = player.ShootProjectile(pressPoint);
            // New projectiles might be null if the player didn't have enough mana to shoot
            if (newProjectiles != null) {
                projectilesToAdd.addAll(Arrays.asList(newProjectiles));
            }
        }
    }

    @Override
    public void OnNotify(GameEvent e) {
        if (!(e instanceof CombatEvent))
            return;
        if (e == CombatEvent.LEVEL_LOSS) {
            Finish();
        }
    }
}
