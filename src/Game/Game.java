package Game;

import Game.GameWindow.GameWindow;
import Game.Graphics.Assets;
import States.StateManager;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;

public class Game extends MouseAdapter implements Runnable {
    //TODO: figure out state transitions made by buttons
    //TODO: add music managers
    //TODO: add option to save data and figure out the resume button
    //TODO: add all states
    //class that implements the main game loop
    private GameWindow wnd;
    private boolean runState;
    private StateManager stateManager;

    public Game() {
        runState = false;
    }

    private void InitGame() {
        wnd = new GameWindow("Hell Invaders");
        wnd.BuildGameWindow();
        Assets.Init();
        stateManager=StateManager.GetInstance();
        Canvas wndCanvas=wnd.GetCanvas();
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

