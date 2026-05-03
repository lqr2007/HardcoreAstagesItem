package com.liquor.hardcoreastagesitem.register;

import com.liquor.hardcoreastagesitem.utils.Getitemlist;
import com.liquor.hardcoreastagesitem.utils.ModelOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;

public class Command {
    @SubscribeEvent
    public static void registerCommands(RegisterClientCommandsEvent event) {
        event.getDispatcher().register(
                Commands.literal("reloadmodel")
                        .executes(context -> {
                            Player player = Minecraft.getInstance().player;
                            if (player != null) {
                                ModelOperation.replaceModel(Getitemlist.getUnknownItemList(player), true);
                                ModelOperation.replaceModel(Getitemlist.getItem(player), false);
                                player.sendSystemMessage(Component.literal("Done!"));
                            }
                            return 1;
                        })
        );
    }
}
