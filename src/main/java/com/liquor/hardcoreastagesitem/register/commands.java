package com.liquor.hardcoreastagesitem.register;

import com.liquor.hardcoreastagesitem.utils.getItemList;
import com.liquor.hardcoreastagesitem.utils.modelOperation;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;

public class commands {
    @SubscribeEvent
    public static void registerCommands(RegisterClientCommandsEvent event) {
        Player player = Minecraft.getInstance().player;
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(Commands.literal("reloadmodel").requires(cs -> cs.hasPermission(0)).executes(context -> {
            if (player != null) {
                modelOperation.replaceModel(getItemList.getUnknownItemList(player), "lock");
                modelOperation.replaceModel(getItemList.getUnlockItemList(player), "unlock");
            }
            return 1;
        }));
    }
}
