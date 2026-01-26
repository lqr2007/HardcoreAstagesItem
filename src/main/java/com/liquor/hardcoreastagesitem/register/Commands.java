package com.liquor.hardcoreastagesitem.register;

import com.liquor.hardcoreastagesitem.utils.Getitemlist;
import com.liquor.hardcoreastagesitem.utils.ModelOperation;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;

public class Commands {
    @SubscribeEvent
    public static void registerCommands(RegisterClientCommandsEvent event) {
        Player player = Minecraft.getInstance().player;
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(net.minecraft.commands.Commands.literal("reloadmodel").requires(cs -> cs.hasPermission(0)).executes(context -> {
            if (player != null) {
                ModelOperation.replaceModel(Getitemlist.getUnknownItemList(player), "lock");
                ModelOperation.replaceModel(Getitemlist.getUnlockItemList(player), "unlock");
            }
            return 1;
        }));
    }
}
