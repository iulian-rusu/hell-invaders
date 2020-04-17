package Entities.CollidableEntities.Enemies;

import Assets.Images.EnemyAssets;
import Entities.CollidableEntities.Projectiles.Projectile;
import Entities.CollidableEntities.Projectiles.ProjectileFactory;
import Entities.CollidableEntities.Projectiles.ProjectileType;
import Entities.Player;
import GameSystems.EventSystem.Events.AudioEvent;
import Game.Game;
import Game.GameWindow;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public class Dragon extends Enemy {
    //projectile parameters
    public static final Point DEFAULT_TARGET_POINT = new Point(Player.PLAYER_X + Player.PLAYER_W, Player.PLAYER_Y + Player.PLAYER_H / 2);
    public static int GET_DEFAULT_DAMAGE() { return 15 - 2 * Game.DIFFICULTY; }
    //health parameters
    public static int GET_DEFAULT_HEALTH() { return 25 * Game.DIFFICULTY; }
    public static int GET_HEALTH_INCREMENT() { return 3 * Game.DIFFICULTY; }
    public static long GET_ACTUAL_HEALTH(int level){
        long ans = GET_DEFAULT_HEALTH() + (long) (Math.pow(HEALTH_INCREMENT, level - 1) * GET_HEALTH_INCREMENT());
        //test for overflow
        if (ans < 0) {
            ans=-ans;
        }
        return ans;
    }
    //size parameters
    public static final int DEFAULT_HEIGHT = 100;
    public static final int DEFAULT_HITBOX_WIDTH = (int) (DEFAULT_WIDTH * 0.6);
    public static final int DEFAULT_HITBOX_HEIGHT = (int) (DEFAULT_HEIGHT * 0.6);
    public static final int DEFAULT_ATTACK_TRANSITION_X = GameWindow.wndDimension.width / 2;

    private ArrayList<Projectile> projectiles;

    public Dragon(int x, int y, int level) {
        super(x, y, DEFAULT_HITBOX_WIDTH, DEFAULT_HITBOX_HEIGHT, DEFAULT_WIDTH, DEFAULT_HEIGHT, level);
    }

    @Override
    protected void InitHealth() {
        this.health = GET_ACTUAL_HEALTH(this.level);
    }

    @Override
    public void Update() {
        //it's active if it's alive of if there are projectiles still flying
        isActive = (health > 0 || (projectiles != null && projectiles.size() > 0));
        super.Update();
        if (projectiles != null) {
            projectiles.forEach(Projectile::Update);
            projectiles.removeIf(projectile -> !projectile.isActive);
        }
    }

    @Override
    protected void Attack() {
        //only attack if it's alive and enough frames have passed since last attack
        if (health > 0 && framesSinceLastAttack >= FRAMES_BETWEEN_ATTACKS) {
            framesSinceLastAttack = 0;
            if (projectiles == null) {
                projectiles = new ArrayList<>(1);
            }
            //add new projectile
            NotifyAllObservers(AudioEvent.PLAY_DRAGON_SHOOT);
            Point from = new Point(x, y + DEFAULT_HEIGHT / 2);
            Point to=new Point(DEFAULT_TARGET_POINT);
            to.y= Math.max(from.y, 315);
            Projectile[] toBeAdded = ProjectileFactory.MakeProjectile(ProjectileType.ENEMY,
                    from, to, GET_DEFAULT_DAMAGE(), Game.DIFFICULTY, 0);
            if (toBeAdded != null) {
                projectiles.addAll(Arrays.asList(toBeAdded));
            }
        }
    }

    @Override
    public void Draw(Graphics g) {
        if (isVisile) {
            BufferedImage currentFrame = EnemyAssets.dragon_frames[(frameCount / 12) % 5];
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
