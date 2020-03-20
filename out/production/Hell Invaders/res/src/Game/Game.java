package Game;

import Audio.AudioManager;
import Assets.AssetManager;
import States.StateManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;

public class Game extends MouseAdapter implements Runnable, KeyListener{
    //TODO: implement all states
    //TODO: implement player stats (current level/gold/upgrades/statistics page)
    //TODO: add enemies and animate them
    //TODO: add level system and difficulties
    //TODO: save player stats into SQL databse and unlock resume button
    //class that implements the main game loop
    private GameWindow wnd;
    private boolean runState;
    private StateManager stateManager;
    private AudioManager audioManager;

    public Game() {
        runState = false;
    }

    private void InitGame() {
        wnd = new GameWindow("Hell Invaders");
        wnd.BuildGameWindow();
        AssetManager.Init(wnd);
        audioManager=AudioManager.GetInstance();
        stateManager=StateManager.GetInstance();
        Canvas wndCanvas=wnd.GetCanvas();
        wndCanvas.addMouseListener(this);
        wndCanvas.addMouseMotionListener(this);
        wndCanvas.addKeyListener(this);
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

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        stateManager.GetCurrentState().keyPressed(keyEvent);
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}

