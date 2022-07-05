package me.andrew.playerspoofer.mixin;

import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import me.andrew.playerspoofer.SpoofedPlayers;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.ApiServices;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Shadow @Final private YggdrasilAuthenticationService authenticationService;

    @Shadow @Final public File runDirectory;

    @Inject(
            method = "<init>",
            at = @At("RETURN")
    )
    private void inject_init(CallbackInfo ci) {
        ApiServices apiServices = ApiServices.create(this.authenticationService, this.runDirectory);
        apiServices.userCache().setExecutor((MinecraftClient)(Object) this);
        SpoofedPlayers.setApiServices(apiServices);
    }
}
