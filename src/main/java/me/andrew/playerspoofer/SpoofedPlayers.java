package me.andrew.playerspoofer;

import me.andrew.playerspoofer.accessor.AbstractClientPlayerEntityAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ApiServices;

import java.util.HashMap;
import java.util.Map;

public class SpoofedPlayers {
    public static final Map<String, SpoofedPlayerData> SPOOFED_PLAYERS = new HashMap<>();
    private static ApiServices apiServices = null;

    public static void resetSpoofedPlayers() {
        SPOOFED_PLAYERS.clear();
    }

    public static void setApiServices(ApiServices apiServices) {
        SpoofedPlayers.apiServices = apiServices;
    }

    public static void spoof(String player, String skinName, String name) {
        Text displayName = Text.literal(name.replace('&', '§'));

        SpoofedPlayerData spoofedPlayerData = SPOOFED_PLAYERS.get(player);
        if (spoofedPlayerData == null) {
            spoofedPlayerData = new SpoofedPlayerData();
            SPOOFED_PLAYERS.put(player, spoofedPlayerData);

            for (AbstractClientPlayerEntity playerEntity : MinecraftClient.getInstance().world.getPlayers()) {
                if (playerEntity.getGameProfile().getName().equals(player)) {
                    AbstractClientPlayerEntityAccessor playerEntityAccessor = (AbstractClientPlayerEntityAccessor) playerEntity;
                    playerEntityAccessor.playerspoofer$setSpoofedPlayerData(spoofedPlayerData);
                }
            }
        }

        spoofedPlayerData.setDisplayName(displayName);

        SpoofedPlayerData spoofedPlayerData1 = spoofedPlayerData;
        apiServices.userCache().findByNameAsync(skinName, gameProfileOptional -> {
            gameProfileOptional.ifPresent(spoofedPlayerData1::setSkinProfile);
        });
    }

    public static void unspoof(String player) {
        SpoofedPlayerData spoofedPlayerData = SPOOFED_PLAYERS.remove(player);
        if (spoofedPlayerData == null) return;

        for (AbstractClientPlayerEntity playerEntity : MinecraftClient.getInstance().world.getPlayers()) {
            AbstractClientPlayerEntityAccessor playerEntityAccessor = (AbstractClientPlayerEntityAccessor) playerEntity;
            if (playerEntityAccessor.playerspoofer$getSpoofedPlayerData() == spoofedPlayerData) {
                playerEntityAccessor.playerspoofer$setSpoofedPlayerData(null);
            }
        }
    }
}
