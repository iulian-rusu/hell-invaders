package Game;

import Assets.Audio.AudioManager;
import Assets.Images.GUIAssets;
import Assets.Images.ImageLoader;
import Entities.Player;
import States.StateManager;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferStrategy;

public class Game extends MouseAdapter implements Runnable {
    //TODO: save player stats into SQL databse
    //class that implements the main game loop

    public static int DIFFICULTY = 1;

    private GameWindow wnd;
    Cursor targetCursor;
    private volatile boolean runState;
    private StateManager stateManager;
    LoadingScreen loadingScreen;

    public Game() {
        runState = false;
    }

    private void InitGame() {
        wnd = new GameWindow("Hell Invaders");
        wnd.BuildGameWindow();
        //start loading thread
        loadingScreen = new LoadingScreen(wnd);
        Thread loadThread = new Thread(loadingScreen);
        loadThread.start();
        //load assets
        ImageLoader.Init();
        //load audio
        AudioManager.Init();
        //create main player
        GlobalReferences.player = new Player();
        //load all game states
        stateManager = StateManager.GetInstance();
        //set initial state
        stateManager.SetCurrentState(StateManager.StateIndex.MENU_STATE);
        //enable mouse events
        Canvas wndCanvas = wnd.GetCanvas();
        wndCanvas.addMouseListener(this);
        wndCanvas.addMouseMotionListener(this);
        wndCanvas.addMouseWheelListener(this);
        //init cursors
        targetCursor = Toolkit.getDefaultToolkit().createCustomCursor(GUIAssets.target_cursor, new Point(15, 15), "target");
    }

    @Override
    public void run() {
        InitGame();
        long oldTime = System.nanoTime();
        long curentTime;
        final int framesPerSecond = 60;
        final double timeFrame = 1000000000.0 / framesPerSecond;
        //stop loading thread
        loadingScreen.Stop();
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
        //handle cursor change
        Canvas canvas = wnd.GetCanvas();
        if (stateManager.GetCurrentStateIndex() == StateManager.StateIndex.GAME_STATE) {
            if (canvas.getCursor() != targetCursor) {
                canvas.setCursor(targetCursor);
            }
        } else if (canvas.getCursor() == targetCursor) {
            canvas.setCursor(Cursor.getDefaultCursor());
        }
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
        assert bs != null;
        Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();
        if (!wnd.isFullScreen) {
            g2d.scale(1, 0.95);
        }
        g2d.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());
        //draw elements in the current state
        stateManager.Draw(g2d);
        bs.show();
        g2d.dispose();

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Point pressPoint = mouseEvent.getPoint();
        if (!wnd.isFullScreen) {
            pressPoint.y /= GameWindow.Y_SCALE_FACTOR;
        }
        stateManager.GetCurrentState().MousePressed(pressPoint);
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point movePoint = mouseEvent.getPoint();
        if (!wnd.isFullScreen) {
            movePoint.y /= GameWindow.Y_SCALE_FACTOR;
        }
        stateManager.GetCurrentState().MouseMoved(movePoint);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        stateManager.GetCurrentState().MouseWheelMoved(e);
    }
}

