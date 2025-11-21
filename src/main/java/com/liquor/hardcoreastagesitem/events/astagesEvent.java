package com.liquor.hardcoreastagesitem.events;

import com.alessandro.astages.event.custom.actions.StageAddedPlayerEvent;
import com.alessandro.astages.event.custom.actions.StageRemovedPlayerEvent;
import com.liquor.hardcoreastagesitem.utils.getItemList;
import com.liquor.hardcoreastagesitem.utils.modelOperation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;

public class astagesEvent {
    @SubscribeEvent
    public static void onStageRemoved(StageRemovedPlayerEvent event) {
        Player player = event.getEntity();
        modelOperation.replaceModel(getItemList.getUnknownItemList(player), "lock");
    }
    @SubscribeEvent
    public static void onStageAdd(StageAddedPlayerEvent event) {
        Player player = event.getEntity();
        modelOperation.replaceModel(getItemList.getUnlockItemList(player), "unlock");
    }
}
