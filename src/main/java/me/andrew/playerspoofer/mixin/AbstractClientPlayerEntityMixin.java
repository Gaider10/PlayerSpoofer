package me.andrew.playerspoofer.mixin;

import com.mojang.authlib.GameProfile;
import me.andrew.playerspoofer.SpoofedPlayerData;
import me.andrew.playerspoofer.SpoofedPlayers;
import me.andrew.playerspoofer.accessor.AbstractClientPlayerEntityAccessor;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.encryption.PlayerPublicKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerEntityMixin extends PlayerEntity implements AbstractClientPlayerEntityAccessor {
    public AbstractClientPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile, @Nullable PlayerPublicKey publicKey) {
        super(world, pos, yaw, gameProfile, publicKey);
    }

    private SpoofedPlayerData spoofedPlayerData = null;

    @Override
    public SpoofedPlayerData playerspoofer$getSpoofedPlayerData() {
        return this.spoofedPlayerData;
    }

    @Override
    public void playerspoofer$setSpoofedPlayerData(SpoofedPlayerData spoofedPlayerData) {
        this.spoofedPlayerData = spoofedPlayerData;
    }

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void inject_init(CallbackInfo ci) {
        this.spoofedPlayerData = SpoofedPlayers.SPOOFED_PLAYERS.get(this.getGameProfile().getName());
    }

    @Inject(
            method = "getPlayerListEntry",
            at = @At("HEAD"),
            cancellable = true
    )
    private void inject_getPlayerListEntry(CallbackInfoReturnable<PlayerListEntry> cir) {
        if (this.spoofedPlayerData != null && this.spoofedPlayerData.getSkinEntry() != null) {
            cir.setReturnValue(this.spoofedPlayerData.getSkinEntry());
        }
    }
}
