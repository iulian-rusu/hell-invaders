package Game;

import Assets.Images.ImageLoader;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class LoadingScreen implements Runnable {
    private volatile boolean isLoading = true;
    private final GameWindow wnd;
    private final BufferedImage bgLoad =ImageLoader.LoadImage("/backgrounds/bg_load.png");

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

    public void Stop(){
        isLoading=false;
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
        Graphics g = bs.getDrawGraphics();
        g.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());
        g.drawImage(bgLoad,0,0,wnd.GetWndWidth(), wnd.GetWndHeight(),null);
        bs.show();
        g.dispose();
    }
}
