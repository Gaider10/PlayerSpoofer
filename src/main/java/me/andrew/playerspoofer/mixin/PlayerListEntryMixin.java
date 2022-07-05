package me.andrew.playerspoofer.mixin;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import me.andrew.playerspoofer.accessor.PlayerListEntryAccessor;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerListEntry.class)
public abstract class PlayerListEntryMixin implements PlayerListEntryAccessor {
    private Runnable texturesLoadedCallback = null;

    @Override
    public void playerspoofer$setTexturesLoadedCallback(Runnable texturesLoadedCallback) {
        this.texturesLoadedCallback = texturesLoadedCallback;
    }

    @Inject(
            method = "method_2956",
            at = @At("HEAD")
    )
    private void inject_method_2956(MinecraftProfileTexture.Type type, Identifier identifier, MinecraftProfileTexture texture, CallbackInfo ci) {
        if (type == MinecraftProfileTexture.Type.SKIN && this.texturesLoadedCallback != null) {
            this.texturesLoadedCallback.run();
            this.texturesLoadedCallback = null;
        }
    }
}
