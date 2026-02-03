package com.liquor.hardcoreastagesitem.events;

import com.alessandro.astages.api.event.player.StageAddedPlayerEvent;
import com.alessandro.astages.api.event.player.StageRemovedPlayerEvent;
import com.liquor.hardcoreastagesitem.utils.Getitemlist;
import com.liquor.hardcoreastagesitem.utils.ModelOperation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AstagesEvent {
    @SubscribeEvent
    public static void onStageRemoved(StageRemovedPlayerEvent event) {
        Player player = event.getPlayer();
        ModelOperation.replaceModel(Getitemlist.getUnknownItemList(player), true);
    }
    @SubscribeEvent
    public static void onStageAdd(StageAddedPlayerEvent event) {
        Player player = event.getPlayer();
        ModelOperation.replaceModel(Getitemlist.getItem(player), false);
    }
}
