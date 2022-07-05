package me.andrew.playerspoofer.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import me.andrew.playerspoofer.SpoofedPlayers;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class ModCommands {
    public static void init() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(literal("spoof")
                    .then(argument("player", StringArgumentType.string())
                            .suggests((context, builder) -> CommandSource.suggestMatching(context.getSource().getPlayerNames(), builder))
                            .then(argument("skin", StringArgumentType.string())
                                    .then(argument("name", StringArgumentType.string())
                                            .executes(context -> {
                                                String player = StringArgumentType.getString(context, "player");
                                                String skin = StringArgumentType.getString(context, "skin");
                                                String name = StringArgumentType.getString(context, "name");

                                                SpoofedPlayers.spoof(player, skin, name);
//                                                context.getSource().sendFeedback(Text.literal("Spoofed " + player));

                                                return 0;
                                            })
                                    )
                            )
                    )
            );

            dispatcher.register(literal("unspoof")
                    .then(argument("player", StringArgumentType.string())
                            .suggests((context, builder) -> CommandSource.suggestMatching(SpoofedPlayers.SPOOFED_PLAYERS.keySet(), builder))
                            .executes(context -> {
                                String player = StringArgumentType.getString(context, "player");

                                if (!SpoofedPlayers.SPOOFED_PLAYERS.containsKey(player)) {
                                    context.getSource().sendError(Text.literal(player + " is not spoofed!"));
                                } else {
                                    SpoofedPlayers.unspoof(player);
//                                    context.getSource().sendFeedback(Text.literal("Unspoofed " + player));
                                }

                                return 0;
                            })
                    )
            );
        });
    }
}
