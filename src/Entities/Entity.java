package Entities;

import Events.Observable;

import java.awt.*;

public abstract class Entity extends Observable implements Comparable<Entity>{
    protected int frameCount=-1;
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

    @Override
    public int compareTo(Entity entity) {
        return Integer.compare(this.y,entity.y);
    }
}
