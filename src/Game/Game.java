package Game;

import Assets.Audio.AudioManager;
import Assets.Images.GUIAssets;
import Assets.Images.ImageLoader;
import States.StateManager;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferStrategy;

public class Game extends MouseAdapter implements Runnable {
    //TODO: add upgrade buttons
    //TODO: save player stats into SQL databse and unlock resume button
    //TODO: implement player stats (max level/total enemies killed/max single spell hit)
    //class that implements the main game loop

    public static int DIFFICULTY = 1;

    private GameWindow wnd;
    private LoadingScreen load;
    Cursor targetCursor, baseCursor;
    private boolean runState;
    private StateManager stateManager;

    public Game() {
        runState = false;
    }

    private void InitGame() {
        wnd = new GameWindow("Hell Invaders");
        wnd.BuildGameWindow();
        //start loading thread
        load=new LoadingScreen(wnd);
        Thread loadThread=new Thread(load);
        loadThread.start();
        //load assets
        ImageLoader.Init();
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
        wndCanvas.addMouseWheelListener(this);
        //init cursors
        targetCursor = Toolkit.getDefaultToolkit().createCustomCursor(GUIAssets.target_cursor, new Point(15,15), "target");
    }

    @Override
    public void run() {
        InitGame();
        long oldTime = System.nanoTime();
        long curentTime;
        final int framesPerSecond = 60;
        final double timeFrame = 1000000000.0 / framesPerSecond;
        //stop loading thread
        load.Stop();
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
        Canvas canvas=wnd.GetCanvas();
        if(stateManager.GetCurrentStateIndex() == StateManager.StateIndex.GAME_STATE){
            if(canvas.getCursor()!=targetCursor) {
                canvas.setCursor(targetCursor);
            }
        }else if(canvas.getCursor()==targetCursor){
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
    public void mouseWheelMoved(MouseWheelEvent e) { stateManager.GetCurrentState().mouseWheelMoved(e); }
}

