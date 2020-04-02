package Entities;

import java.awt.*;

public abstract class Entity {
    protected int frameCount;
    protected int x;
    protected int y;

    public int GetX() {
        return x;
    }

    public int GetY() {
        return y;
    }

    public void Update() {
        frameCount = (frameCount + 1) % 60;
    }

    public abstract void Draw(Graphics g);
}
