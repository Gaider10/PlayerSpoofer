package me.andrew.playerspoofer.mixin;

import me.andrew.playerspoofer.SpoofedPlayers;
import net.minecraft.client.MinecraftClientGame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClientGame.class)
public abstract class MinecraftClientGameMixin {
    @Inject(
            method = "onStartGameSession",
            at = @At("HEAD")
    )
    private void inject_onStartGameSession(CallbackInfo ci) {
        SpoofedPlayers.resetSpoofedPlayers();
    }

    @Inject(
            method = "onLeaveGameSession",
            at = @At("HEAD")
    )
    private void inject_onLeaveGameSessionn(CallbackInfo ci) {
        SpoofedPlayers.resetSpoofedPlayers();
    }
}
