package Entities.CollidableEntities.Projectiles;

import GameSystems.EventSystem.Events.AudioEvent;

public enum ProjectileType {
    FIRE(AudioEvent.PLAY_FIRE_SHOOT),
    FROST(AudioEvent.PLAY_FROST_SHOOT),
    ARCANE(AudioEvent.PLAY_ARCANE_SHOOT),
    ENEMY(AudioEvent.PLAY_FIRE_SHOOT);

    public AudioEvent sfxEvent;

    ProjectileType(AudioEvent event){
        this.sfxEvent=event;
    }
}
