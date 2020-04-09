package Game;

import Audio.AudioManager;
import Assets.AssetInitializer;
import States.StateManager;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;

public class Game extends MouseAdapter implements Runnable {
    //TODO: add upgrade buttons
    //TODO: make enemies attack
    //TODO: add level system and difficulties
    //TODO: save player stats into SQL databse and unlock resume button
    //TODO: implement player stats (max level/total enemies killed/max single spell hit)
    //class that implements the main game loop

    public static int DIFFICULTY=2;

    private GameWindow wnd;
    private boolean runState;
    private StateManager stateManager;

    public Game() {
        runState = false;
    }

    private void InitGame() {
        wnd = new GameWindow("Hell Invaders");
        wnd.BuildGameWindow();
        //load assets
        AssetInitializer.Init();
        //load audio
        AudioManager.GetInstance();
        //load all game states
        stateManager = StateManager.GetInstance();
        //game starts in emnu state
        stateManager.SetCurrentState(StateManager.StateIndex.MENU_STATE);
        //enable mouse events
        Canvas wndCanvas = wnd.GetCanvas();
        wndCanvas.addMouseListener(this);
        wndCanvas.addMouseMotionListener(this);
    }

    @Override
    public void run() {
        InitGame();
        long oldTime = System.nanoTime();
        long curentTime;
        final int framesPerSecond = 60;
        final double timeFrame = 1000000000.0 / framesPerSecond;
        while (runState) {
            curentTime = System.nanoTime();
            if ((curentTime - oldTime) >= timeFrame) {
                Update();
                Draw();
                oldTime = curentTime;
            }
        }
    }

    public synchronized void StartGame() {
        if (!runState) {
            runState = true;
            Thread gameThread = new Thread(this);
            gameThread.start();
        }
    }

    private void Update() {
        stateManager.Update();
    }

    private void Draw() {
        BufferStrategy bs = wnd.GetCanvas().getBufferStrategy();
        if (bs == null) {
            try {
                wnd.GetCanvas().createBufferStrategy(3);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        stateManager.Draw(wnd);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        stateManager.GetCurrentState().mousePressed(mouseEvent);
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        stateManager.GetCurrentState().mouseMoved(mouseEvent);
    }

}

