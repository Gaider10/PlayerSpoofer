package me.andrew.playerspoofer;

import com.mojang.authlib.GameProfile;
import me.andrew.playerspoofer.accessor.PlayerListEntryAccessor;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.text.Text;

public class SpoofedPlayerData {
    private PlayerListEntry skinEntry;
    private Text displayName;

    public SpoofedPlayerData() {
        this.skinEntry = null;
        this.displayName = null;
    }

    public PlayerListEntry getSkinEntry() {
        return this.skinEntry;
    }

    public void setSkinProfile(GameProfile skinProfile) {
        PlayerListEntry skinEntry = new PlayerListEntry(new PlayerListS2CPacket.Entry(skinProfile, 0, null, null, null), null);
        ((PlayerListEntryAccessor) skinEntry).playerspoofer$setTexturesLoadedCallback(() -> {
            this.skinEntry = skinEntry;
        });
        skinEntry.getSkinTexture();
    }

    public Text getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(Text displayName) {
        this.displayName = displayName;
    }
}
