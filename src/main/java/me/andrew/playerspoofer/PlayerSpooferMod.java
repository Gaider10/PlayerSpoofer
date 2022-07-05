package me.andrew.playerspoofer;

import me.andrew.playerspoofer.command.ModCommands;
import net.fabricmc.api.ModInitializer;

public class PlayerSpooferMod implements ModInitializer {
    public static final String MOD_ID = "playerspoofer";

    @Override
    public void onInitialize() {
        ModCommands.init();


    }
}
