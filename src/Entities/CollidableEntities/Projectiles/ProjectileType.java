package Entities.CollidableEntities.Projectiles;

import GameSystems.EventSystem.Events.AudioEvent;

/**
 * @brief Holds all projectile types.
 */
public enum ProjectileType {
    FIRE(AudioEvent.PLAY_FIRE_SHOOT),
    FROST(AudioEvent.PLAY_FROST_SHOOT),
    ARCANE(AudioEvent.PLAY_ARCANE_SHOOT),
    ENEMY(AudioEvent.PLAY_FIRE_SHOOT);

    public AudioEvent sfxEvent;///< An audio event that happens when the projectile is shot.

    /**
     * Constructor with parameters.
     *
     * @param event The audio event that will happen when this type of projectile will be shot.
     */
    ProjectileType(AudioEvent event){
        this.sfxEvent=event;
    }
}
