package Entities.CollidableEntities;

import Assets.Audio.AudioManager;
import Entities.Entity;

import java.awt.*;

/**
 * @brief Implements a generic collidable entity - an entity which supports collisions and has a hitbox.
 */
public abstract class CollidableEntity extends Entity {
    public boolean isActive = true;///< Flag that indicates if the entity is still active. An inactive entity is removed from the map.
    protected Rectangle hitBox;///< The hitbox of the enitity.
    protected Rectangle textureBox;///< The texture box of the entity.
    protected int x;///< The x coordinate of the top-left corner of the hitbox.
    protected int y;///< The y coordinate of the top-left corner of the hitbox.
    protected boolean isCollisionActive = true;///< Flag that indicates if the entity is able to collide.

    /**
     * Constructor with parameters.
     *
     * @param x        The x coordinate of the top-left corner of the hitbox.
     * @param y        The y coordinate of the top-left corner of the hitbox.
     * @param hitboxW  The width of the hitbox.
     * @param hitboxH  The height of the hitbox.
     * @param textureW The width of the texture box.
     * @param textureH The height of the texture box.
     */
    public CollidableEntity(int x, int y, int hitboxW, int hitboxH, int textureW, int textureH) {
        this.x = x;
        this.y = y;
        hitBox = new Rectangle(hitboxW, hitboxH);
        textureBox = new Rectangle(textureW, textureH);
        // Init texture and hit box locations
        textureBox.setLocation(x, y);
        hitBox.setLocation(x + (textureBox.width - hitBox.width) / 2, y + (textureBox.height - hitBox.height) / 2 + GetHitBoxYOffset());
        // Add audio observer
        AddObserver(AudioManager.GetInstance());
    }

    /**
     * Changes the active status of the entity.
     *
     * @param f Boolean flag that becomes the new active status.
     */
    public void SetActive(boolean f) {
        isActive = f;
    }

    /**
     * Returns the x coordinate of the top-left corner of the hitbox.
     *
     * @return An int representing the coordinate.
     */
    public int GetTextureBoxX() {
        return textureBox.x;
    }

    /**
     * Returns the y coordinate of the top-left corner of the hitbox.
     *
     * @return An int representing the coordinate.
     */
    public int GetTextureBoxY() {
        return textureBox.y;
    }

    /**
     * Checks collision with other CollidableEntity object.
     *
     * @param other Another CollidableEntity instance.
     * @return A boolean flag that indicates if the collision occurs.
     * @see java.awt.Rectangle for intersection implementation.
     */
    public boolean CollidesWith(CollidableEntity other) {
        return isCollisionActive && other.isCollisionActive && this.hitBox.intersects(other.hitBox);
    }

    @Override
    public void Update() {
        super.Update();
        textureBox.setLocation(x, y);
        hitBox.setLocation(x + (textureBox.width - hitBox.width) / 2, y + (textureBox.height - hitBox.height) / 2 + GetHitBoxYOffset());
    }

    /**
     * Returns the y offset of the top of the hitbox relative to the top of the texture box.
     *
     * @return An int representing the offset.
     */
    public abstract int GetHitBoxYOffset();
}
