package GUI.Text;

import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class GUITimedDecorator extends GUITextDecorator {
    private int frameCount = -1;
    private final int lifetime;

    public GUITimedDecorator(@NotNull GUITextComponent target, int lifetime) {
        super(target);
        this.lifetime=lifetime;
    }

    @Override
    public void Draw(Graphics g) {
        Tick();
        target.Draw(g);
    }

    private void Tick() {
        if (lifetime >= 0 && lifetime <= ++frameCount) {
            target.SetActive(false);
        }
    }
}
