package Entities;

import GameSystems.EventSystem.Observable;

import java.awt.*;

public abstract class Entity extends Observable{
    protected int frameCount=-1;
    
    public void Update() {
        frameCount = (frameCount + 1) % 60;
    }

    public abstract void Draw(Graphics g);

}
