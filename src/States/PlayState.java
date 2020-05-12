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
import java.util.ConcurrentModificationException;

/**
 *  @brief Implements the gameplay.
 */
public class PlayState extends ReversibleState implements GameSystems.EventSystem.Observer {
    public static final int BATTLEFIELD_Y = 180;///< The y coordinate whenre the battlefield starts.
    public static final int BATTLEFIELD_HEIGHT = 250;///< The height of the battlefield.

    private final Player player;///< A reference to the player object.
    private final ExperiencePanel experiencePanel;///< A reference to the experience panel.
    private final ArrayList<Enemy> allEnemies;///< List that holds all active enemies.
    private final ArrayList<Projectile> allProjectiles;///< List that holds all active projectiles.
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
        try {
            allEnemies.forEach(Enemy::Update);
            allProjectiles.forEach(Projectile::Update);
            player.Update();
            // Check for collisions and eventually delete inactive entities
            CleanCombatText();
            allCombatText.addAll(CollisionManager.Update(allEnemies, allProjectiles));
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }
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
     * Finalizes the current level and transitions the game to the reuired state.
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
        try {
            for (Enemy e : allEnemies) {
                e.Draw(g2d);
            }
            for (Projectile p : allProjectiles) {
                p.Draw(g2d);
            }
            for (GUITextComponent t : allCombatText) {
                t.Draw(g2d);
            }
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }
        player.Draw(g2d);
        experiencePanel.Draw(g2d);
        for (GUIButton b : allButtons) {
            b.Draw(g2d);
        }
        // Draw the same information as in UpgradeState
        for (GUIText t : UpgradeState.infoText) {
            t.Draw(g2d);
        }
    }

    @Override
    public void MousePressed(Point pressPoint) {
        super.MousePressed(pressPoint);
        if (clickBox.contains(pressPoint)) {
            Projectile[] toBeAdded = player.ShootProjectile(pressPoint);
            // Check if player had enough mana to shoot
            if (toBeAdded != null) {
                allProjectiles.addAll(Arrays.asList(toBeAdded));
            }
        }
    }

    @Override
    public void OnNotify(GameEvent e) {
        if (!(e instanceof CombatEvent))
            return;
        switch ((CombatEvent) e) {
            case LEVEL_LOSS:
            case LEVEL_WIN:
                Finish();
                break;
        }
    }
}
