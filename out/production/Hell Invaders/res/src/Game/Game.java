package Game;

import Assets.Audio.AudioManager;
import Assets.Images.GUIAssets;
import Assets.Images.ImageLoader;
import SQL.DatabaseManager;
import States.StateIndex;
import States.StateManager;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferStrategy;

/**
 * @author Iulian Rusu, https://github.com/iulian-rusu
 * @brief Class that runs the main game loop.
 * <p>
 * The Game class extends the MouseAdapter abstract class to handle mouse events.
 * This class also implements the Runnable interface to run the game loop in a new thread.
 * <p>
 * @see java.lang.Runnable
 * @see java.awt.event.MouseAdapter;
 * @see java.awt.event.MouseEvent;
 * @see java.awt.event.MouseWheelEvent;
 */
public class Game extends MouseAdapter implements Runnable {
    public static int difficulty = 1;///< The current game difficulty.
    Cursor targetCursor;///< The cursor of the game.
    LoadingScreen loadingScreen;///< Reference to a LoadingScreen which renders while the game is loading.
    private GameWindow wnd;///< The window of the game.
    private volatile boolean isRunning;///< Flag that indicates if the game is running.
    private StateManager stateManager;///< Reference to the StateManager object. Used to manage internal game states.

    /**
     * Constructor without parameters.
     */
    public Game() {
        isRunning = false;
    }

    /**
     * Initializes all aspects of the game.
     */
    private void InitGame() {
        // Create database if necessary
        DatabaseManager.CreateDatabase();
        difficulty = DatabaseManager.GetGameData("Difficulty");
        // Create game window
        wnd = new GameWindow("Hell Invaders");
        wnd.BuildGameWindow();
        // Start loading thread
        loadingScreen = new LoadingScreen(wnd);
        Thread loadThread = new Thread(loadingScreen);
        loadThread.start();
        // Load image assets
        ImageLoader.Init();
        // Load audio assets
        AudioManager.Init();
        // Load all game states
        stateManager = StateManager.GetInstance();
        // Set initial state
        stateManager.SetCurrentState(StateIndex.MENU_STATE);
        // Enable mouse events
        Canvas wndCanvas = wnd.GetCanvas();
        wndCanvas.addMouseListener(this);
        wndCanvas.addMouseMotionListener(this);
        wndCanvas.addMouseWheelListener(this);
        // Init cursors
        targetCursor = Toolkit.getDefaultToolkit().createCustomCursor(GUIAssets.target_cursor, new Point(15, 15), "target");
    }

    /**
     * Overrides the run() method of the Runnable interface. Gets automatically called when the game thread is started.
     */
    @Override
    public void run() {
        InitGame();
        long oldTime = System.nanoTime();
        long curentTime;
        final int framesPerSecond = 60;
        final double timeFrame = 1000000000.0 / framesPerSecond;
        // Stop loading thread
        loadingScreen.Stop();
        while (isRunning) {
            curentTime = System.nanoTime();
            if ((curentTime - oldTime) >= timeFrame) {
                Update();
                Draw();
                oldTime = curentTime;
            }
        }
    }

    /**
     * Creates a new thread and runs the game loop in it.
     */
    public synchronized void StartGame() {
        if (!isRunning) {
            isRunning = true;
            Thread gameThread = new Thread(this);
            gameThread.start();
        }
    }

    /**
     * Handles the update cycle of the game loop.
     */
    private void Update() {
        stateManager.Update();
        // Handle cursor change
        Canvas canvas = wnd.GetCanvas();
        if (stateManager.GetCurrentStateIndex() == StateIndex.PLAY_STATE) {
            if (canvas.getCursor() != targetCursor) {
                canvas.setCursor(targetCursor);
            }
        } else if (canvas.getCursor() == targetCursor) {
            canvas.setCursor(Cursor.getDefaultCursor());
        }
    }

    /**
     * Handles the draw cycle of the game loop.
     */
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
        if (!wnd.isFullscreen) {
            g2d.scale(1, 0.95);
        }
        g2d.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());
        // Draw elements in the current state
        stateManager.Draw(g2d);
        bs.show();
        g2d.dispose();

    }

    /**
     * Called when the mouse is pressed.
     *
     * @param mouseEvent The event associated with the mouse press.
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Point pressPoint = mouseEvent.getPoint();
        if (!wnd.isFullscreen) {
            pressPoint.y /= GameWindow.Y_SCALE_FACTOR;
        }
        stateManager.GetCurrentState().MousePressed(pressPoint);
    }

    /**
     * Called when the mouse is moved
     *
     * @param mouseEvent The event associated with the mouse move.
     */
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point movePoint = mouseEvent.getPoint();
        if (!wnd.isFullscreen) {
            movePoint.y /= GameWindow.Y_SCALE_FACTOR;
        }
        stateManager.GetCurrentState().MouseMoved(movePoint);
    }

    /**
     * Called when the mouse wheel is moved
     *
     * @param e The event associated with the mouse wheel move.
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        stateManager.GetCurrentState().MouseWheelMoved(e);
    }
}

