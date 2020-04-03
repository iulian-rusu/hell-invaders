package States;

import Audio.AudioManager;
import Audio.BackgroundMusicAssets;
import Entities.Enemies.Monster;
import Entities.Entity;
import Entities.EntityManager;
import Entities.Enemies.Enemy;
import Entities.Player;
import Entities.Projectiles.Projectile;
import GUI.GUIButton;
import Game.GameWindow;
import Assets.BackgroundAssets;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.util.*;

public class GameState extends ReversibleState {

    private Player p;
    //enemies and projectiles are separated for more efficient collision checking
    private ArrayList<Enemy> allEnemies;
    private ArrayList<Projectile> allProjectiles;
    private Rectangle clickBox;

    public GameState() {
        allButtons.get(0).AddActionListener(actionEvent -> {
            StateManager.GetInstance().SetCurrentState(StateManager.StateIndex.UPGRADE_STATE);
            AudioManager.GetInstance().Stop(BackgroundMusicAssets.gameMusic);
        });

        allEnemies = new ArrayList<>() {
            public boolean add(Enemy mt) {
                int index = Collections.binarySearch(this, mt);
                if (index < 0)
                    index = ~index;
                super.add(index, mt);
                return true;
            }
        };
        allProjectiles = new ArrayList<>();
        p = new Player();

        Dimension screenSize = GameWindow.wndDimension;
        clickBox = new Rectangle(200, 100, screenSize.width - 200, screenSize.height - 100);
    }

    @Override
    public void Init() {
        super.Init();
        AudioManager.GetInstance().Play(BackgroundMusicAssets.gameMusic);
        allProjectiles.clear();
        allEnemies.clear();

        allEnemies.add(new Monster(1700, 430));
        allEnemies.add(new Monster(1700, 370));
        allEnemies.add(new Monster(1700, 300));
    }

    @Override
    public void Update() {
        super.Update();
        for (Enemy e : allEnemies) {
            e.Update();
        }
        for (Projectile p : allProjectiles) {
            p.Update();
        }
        p.Update();
        //check for collisions and eventually delete inactive entities
        EntityManager.Update(allEnemies, allProjectiles);
    }

    @Override
    public void Draw(GameWindow wnd) {
        BufferStrategy bs = wnd.GetCanvas().getBufferStrategy();
        Graphics g = bs.getDrawGraphics();
        g.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());
        g.drawImage(BackgroundAssets.bg_game, 0, 0, null);
        p.Draw(g);
        try {
            for (Enemy e : allEnemies) {
                e.Draw(g);
            }
            for (Projectile p : allProjectiles) {
                p.Draw(g);
            }
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }
        for (GUIButton b : allButtons) {
            b.Draw(g);
        }
        bs.show();
        g.dispose();
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
            AudioManager.GetInstance().Stop(BackgroundMusicAssets.gameMusic);
            StateManager.GetInstance().SetCurrentState(StateManager.StateIndex.UPGRADE_STATE);
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        super.mousePressed(mouseEvent);
        if (clickBox.contains(mouseEvent.getPoint())) {
            Projectile[] toBeAdded = p.FireProjectile(mouseEvent.getPoint());
            allProjectiles.addAll(Arrays.asList(toBeAdded));
        }
    }
}
