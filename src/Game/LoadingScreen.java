package Game;

import Assets.Images.ImageLoader;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

/**
 * @brief Displays a loading screen in a parallel thread while the main game thread is laoding data.
 */
public class LoadingScreen implements Runnable {
    private final GameWindow wnd;///< Reference to the main game window.
    private final BufferedImage bgLoad = ImageLoader.LoadImage("/backgrounds/bg_load.png");///< The image to be drawn as a loading screen.
    private volatile boolean isLoading = true;///< Boolean flag that indicates if the game is still loading.

    /**
     * Constructor with parameters.
     *
     * @param target A GameWindow object.
     */
    public LoadingScreen(GameWindow target) {
        wnd = target;
    }

    @Override
    public void run() {
        long oldTime = System.nanoTime();
        long curentTime;
        final int framesPerSecond = 60;
        final double timeFrame = 1000000000.0 / framesPerSecond;
        while (isLoading) {
            curentTime = System.nanoTime();
            if ((curentTime - oldTime) >= timeFrame) {
                Draw();
                oldTime = curentTime;
            }
        }
    }

    /**
     * Marks the isLoading member as false, stopping the loading thread.
     */
    public void Stop() {
        isLoading = false;
    }

    /**
     * Draws on the screen while the game is loading.
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
        g2d.drawImage(bgLoad, 0, 0, wnd.GetWndWidth(), wnd.GetWndHeight(), null);
        bs.show();
        g2d.dispose();
    }
}
