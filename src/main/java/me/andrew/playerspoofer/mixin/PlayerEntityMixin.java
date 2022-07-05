package me.andrew.playerspoofer.mixin;

import me.andrew.playerspoofer.SpoofedPlayerData;
import me.andrew.playerspoofer.accessor.AbstractClientPlayerEntityAccessor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Inject(
            method = "getDisplayName",
            at = @At("HEAD"),
            cancellable = true
    )
    private void inject_getDisplayName(CallbackInfoReturnable<Text> cir) {
        if (this instanceof AbstractClientPlayerEntityAccessor playerEntityAccessor) {
            SpoofedPlayerData spoofedPlayerData = playerEntityAccessor.playerspoofer$getSpoofedPlayerData();
            if (spoofedPlayerData != null && spoofedPlayerData.getDisplayName() != null) {
                cir.setReturnValue(spoofedPlayerData.getDisplayName());
            }
        }
    }
}
