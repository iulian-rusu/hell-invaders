package States;

import Entities.CollidableEntities.CollisionManager;
import Entities.CollidableEntities.Enemies.Enemy;
import Entities.Player;
import Entities.CollidableEntities.Projectiles.Projectile;
import EventSystem.Events.AudioEvent;
import EventSystem.Events.CombatEvent;
import EventSystem.Events.GameEvent;
import GUI.GUIButton;
import GUI.GUIText;
import Game.Game;
import Game.GameWindow;
import Assets.BackgroundAssets;
import LevelSystem.LevelLoader;
import PlayerStats.ExperiencePanel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.util.*;

public class GameState extends ReversibleState implements EventSystem.Observer{

    public static final int BATTLEFIELD_Y = 180;
    public static final int BATTLEFIELD_HEIGHT = 250;

    public Player p;
    ExperiencePanel experiencePanel;
    //enemies and projectiles are separated for more efficient collision checking
    private final ArrayList<Enemy> allEnemies;
    private final ArrayList<Projectile> allProjectiles;
    private final ArrayList<GUIText> combatText;
    private final ArrayList<GUIText> infoText;
    private final Rectangle clickBox;
    private boolean isWon;

    public GameState() {
        //back button
        allButtons.get(0).AddActionListener(actionEvent -> {
            NotifyAllObservers(AudioEvent.STOP_CURRENT_STATE_MUSIC);
            NotifyAllObservers(AudioEvent.STOP_ALL_SFX);
            StateManager.GetInstance().SetCurrentState(StateManager.StateIndex.UPGRADE_STATE);

        });
        //create Enemy and Projectile lists
        allEnemies = new ArrayList<>() {
            //sort enemies by y coordinate
            public boolean add(Enemy newEnemy) {
                int index = Collections.binarySearch(this, newEnemy);
                if (index < 0)
                    index = ~index;
                super.add(index, newEnemy);
                return true;
            }
        };
        allProjectiles = new ArrayList<>();
        combatText = new ArrayList<>();

        //create player
        p = Player.GetInstance();
        p.AddObserver(this);
        experiencePanel=ExperiencePanel.GetInstance();

        //various text for player info
        int infoTextSize = 100;
        infoText = new ArrayList<>(2);
        infoText.add(new GUIText("EASY", GameWindow.wndDimension.width - 270,
                Player.MANABAR_Y+Player.HEALTHBAR_HEIGHT, infoTextSize));
        infoText.add(new GUIText("LEVEL 1", GameWindow.wndDimension.width / 2 - 100,
                BACK_BUTTON_Y + BUTTON_H, infoTextSize));

        //clickable field to fire projectiles
        Dimension screenSize = GameWindow.wndDimension;
        clickBox = new Rectangle(200, 100, screenSize.width - 200, screenSize.height - 100);
    }

    @Override
    public void Init() {
        super.Init();
        NotifyAllObservers(AudioEvent.PLAY_CURRENT_STATE_MUSIC);
        isWon = false;
        p.Init();
        //init info text
        InitText();
        //load enemy waves
        LevelLoader.InitLevel(allProjectiles, allEnemies, p.GetLevel());
        combatText.clear();
    }

    void InitText() {
        //init difficulty text
        String d = "EASY";
        Color c=Color.GREEN;
        switch (Game.DIFFICULTY) {
            case 1:
                break;
            case 2:
                d = "MEDIUM";
                c=Color.YELLOW;
                break;
            case 3:
                d = "HARD";
                c=Color.RED;
                break;
        }
        infoText.get(0).SetText(d);
        infoText.get(0).SetColor(c);
        //init level text
        infoText.get(1).SetText("DAY " + p.GetLevel());
    }

    @Override
    public void Update() {
        super.Update();
        try {
            allEnemies.forEach(Enemy::Update);
            allProjectiles.forEach(Projectile::Update);
            p.Update();
            //check for collisions and eventually delete inactive entities
            CleanCombatText();
            combatText.addAll(CollisionManager.GetInstance().Update(allEnemies, allProjectiles));
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }
        CheckIfFinished();
    }

    private void CheckIfFinished() {
        //if no enemies and it's not the beginning of the level -> level ended
        if (secondCount > 0 && allEnemies.size() == 0) {
           Finish();
        }
    }

    private void Finish(){
        //checks if level was lost or won and goes to the respective state
        isWon = p.GetHealth() > 0;
        if(isWon){
            NotifyAllObservers(AudioEvent.PLAY_WIN_SFX);
        }
        NotifyAllObservers(AudioEvent.STOP_CURRENT_STATE_MUSIC);
        NotifyAllObservers(AudioEvent.STOP_ALL_SFX);
        if (isWon) {
            p.SetLevel(p.GetLevel() + 1);
            StateManager.GetInstance().SetCurrentState(StateManager.StateIndex.WIN_STATE);
        } else {
            StateManager.GetInstance().SetCurrentState(StateManager.StateIndex.LOSS_STATE);
        }
    }

    private void CleanCombatText() {
        combatText.removeIf(text -> !text.isActive);
    }

    @Override
    public void Draw(GameWindow wnd) {
        BufferStrategy bs = wnd.GetCanvas().getBufferStrategy();
        Graphics g = bs.getDrawGraphics();
        g.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());
        g.drawImage(BackgroundAssets.bg_game, 0, 0, null);
        try {
            for (Enemy e : allEnemies) {
                e.Draw(g);
            }
            for (Projectile p : allProjectiles) {
                p.Draw(g);
            }
            for (GUIText t : combatText) {
                t.Draw(g);
            }
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }
        p.Draw(g);
        experiencePanel.Draw(g);
        for (GUIButton b : allButtons) {
            b.Draw(g);
        }
        for (GUIText t : infoText) {
            t.Draw(g);
        }
        bs.show();
        g.dispose();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        super.mousePressed(mouseEvent);
        if (clickBox.contains(mouseEvent.getPoint())) {
            Projectile[] toBeAdded = p.ShootProjectile(mouseEvent.getPoint());
            //check if player had enough mana to shoot
            if (toBeAdded != null) {
                allProjectiles.addAll(Arrays.asList(toBeAdded));
            }
        }
    }

    @Override
    public void OnNotify(GameEvent e) {
        if(e.GetType()!= GameEvent.GameEventType.CombatEvent)
            return;
        switch((CombatEvent)e){
            case LEVEL_LOSS:
            case LEVEL_WIN: Finish();
            break;
        }
    }
}
