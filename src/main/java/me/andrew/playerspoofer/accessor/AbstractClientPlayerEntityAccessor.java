package me.andrew.playerspoofer.accessor;

import me.andrew.playerspoofer.SpoofedPlayerData;

public interface AbstractClientPlayerEntityAccessor {

    SpoofedPlayerData playerspoofer$getSpoofedPlayerData();

    void playerspoofer$setSpoofedPlayerData(SpoofedPlayerData spoofedPlayerData);
}
