package Entities.CollidableEntities.Enemies;

import Assets.Images.EnemyAssets;
import Entities.CollidableEntities.Projectiles.Projectile;
import Entities.CollidableEntities.Projectiles.ProjectileFactory;
import Entities.CollidableEntities.Projectiles.ProjectileType;
import Entities.Player;
import Game.Game;
import Game.GameWindow;
import GameSystems.EventSystem.Events.AudioEvent;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @brief Implements the Dragon type of enemy - a long ranged attacker.
 */
public class Dragon extends Enemy {
    public static final Point DEFAULT_TARGET_POINT =
            new Point(Player.PLAYER_X + Player.PLAYER_W, Player.PLAYER_Y + Player.PLAYER_H / 2);///< The point towards which to shoot
    public static final int DEFAULT_HEIGHT = 100;///< The default height of the texture box.
    public static final int DEFAULT_HITBOX_WIDTH = (int) (DEFAULT_WIDTH * 0.6);///< The width of the hitbox relative to the texture.
    public static final int DEFAULT_HITBOX_HEIGHT = (int) (DEFAULT_HEIGHT * 0.6);///< The height of the hitbox relative to the texture box.
    public static final int DEFAULT_ATTACK_TRANSITION_X = GameWindow.SCREEN_DIMENSION.width / 2;///< The default x coordinate for attack state trigger.

    private ArrayList<Projectile> projectiles;///< An ArrayList of all projectiles shot by the Dragon and that are still active.
    /**
     * Constructor with parameters.
     *
     * @param x     The x coordinate of the top-left corner of the hitbox.
     * @param y     The y coordinate of the top-left corner of the hitbox.
     * @param level The level of the monster.
     */
    public Dragon(int x, int y, int level) {
        super(x, y, DEFAULT_HITBOX_WIDTH, DEFAULT_HITBOX_HEIGHT, DEFAULT_WIDTH, DEFAULT_HEIGHT, level);
    }

    /**
     * Returns the default damage dealt based on the game difficulty.
     *
     * @return An int representing the damage value.
     */
    public static int GET_DEFAULT_DAMAGE() {
        return 15 - 2 * Game.difficulty;
    }

    /**
     * Returns the default health based on the game difficulty.
     *
     * @return An int representing the health value.
     */
    public static int GET_DEFAULT_HEALTH() {
        return 25 * Game.difficulty;
    }

    /**
     * Returns the health dealt based on the default health and level.
     *
     * @return An int representing the health value.
     */
    public static long GET_ACTUAL_HEALTH(int level) {
        long ans = (long) (Math.pow(HEALTH_INCREMENT, level - 1) * GET_DEFAULT_HEALTH());
        // Test for overflow
        if (ans < 0) {
            ans = -ans;
        }
        return ans;
    }

    @Override
    protected void InitHealth() {
        this.health = GET_ACTUAL_HEALTH(this.level);
    }

    @Override
    public void Update() {
        // It's active if it's alive or if there are projectiles still flying
        isActive = (health > 0 || (projectiles != null && projectiles.size() > 0));
        super.Update();
        if (projectiles != null) {
            projectiles.forEach(Projectile::Update);
            projectiles.removeIf(projectile -> !projectile.isActive);
        }
    }

    @Override
    protected void Attack() {
        // Only attack if it's alive and enough frames have passed since last attack
        if (health > 0 && framesSinceLastAttack >= FRAMES_BETWEEN_ATTACKS) {
            framesSinceLastAttack = 0;
            if (projectiles == null) {
                projectiles = new ArrayList<>(1);
            }
            // Addd new projectile
            NotifyAllObservers(AudioEvent.PLAY_DRAGON_SHOOT);
            Point from = new Point(x, y + DEFAULT_HEIGHT / 2);
            Point to = new Point(DEFAULT_TARGET_POINT);
            to.y = Math.max(from.y, 315);
            Projectile[] toBeAdded = ProjectileFactory.MakeProjectile(ProjectileType.ENEMY,
                    from, to, GET_DEFAULT_DAMAGE(), Game.difficulty, 0);
            if (toBeAdded != null) {
                projectiles.addAll(Arrays.asList(toBeAdded));
            }
        }
    }

    @Override
    public void Draw(Graphics g) {
        if (isVisile) {
            BufferedImage currentFrame = EnemyAssets.dragonFrames[(frameCount / 12) % 5];
            g.drawImage(currentFrame, x, y, textureBox.width, textureBox.height, null);
        }
        if (projectiles != null) {
            for (Projectile p : projectiles) {
                p.Draw(g);
            }
        }
        super.Draw(g);
    }

    @Override
    public int GetHeight() {
        return DEFAULT_HEIGHT;
    }

    @Override
    public int GetHitBoxYOffset() {
        return 0;
    }

    @Override
    protected int GetAttackTransitionX() {
        return DEFAULT_ATTACK_TRANSITION_X;
    }
}
