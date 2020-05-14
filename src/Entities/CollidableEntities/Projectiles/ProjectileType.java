package Entities.CollidableEntities.Projectiles;

import GameSystems.EventSystem.Events.AudioEvent;

/**
 * @brief Holds all projectile types.
 */
public enum ProjectileType {
    FIRE(AudioEvent.PLAY_FIRE_SHOOT,1),
    FROST(AudioEvent.PLAY_FROST_SHOOT,2),
    ARCANE(AudioEvent.PLAY_ARCANE_SHOOT,3),
    ENEMY(AudioEvent.PLAY_FIRE_SHOOT,0);

    public AudioEvent sfxEvent;///< An audio event that happens when the projectile is shot.
    public int value;

    /**
     * Constructor with parameters.
     *
     * @param event The audio event that will happen when this type of projectile will be shot.
     */
    ProjectileType(AudioEvent event, int v) {
        this.sfxEvent = event;
        this.value = v;
    }
}
