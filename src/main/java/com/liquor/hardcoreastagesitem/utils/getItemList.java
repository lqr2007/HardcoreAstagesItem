package com.liquor.hardcoreastagesitem.utils;

import com.alessandro.astages.capability.AProvider;
import com.alessandro.astages.capability.PlayerStage;
import com.alessandro.astages.core.ARestrictionManager;
import com.alessandro.astages.core.server.manager.AItemManager;
import com.alessandro.astages.core.server.restriction.item.AItemRestriction;
import com.liquor.hardcoreastagesitem.HardcoreAstagesItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class getItemList {

    public static List<Item> unlockItems = new ArrayList<>();
    public static List<Item> unknownItems = new ArrayList<>();

    public static List<Item> getUnlockItemList(Player player) {
        PlayerStage playerStageData = player.getData(AProvider.PLAYER_STAGE);
        AItemManager itemManager = ARestrictionManager.ITEM_INSTANCE;
        List<AItemRestriction> itemRestrictions = itemManager.getItemRestrictions();

        unlockItems.clear();

        for (AItemRestriction restriction : itemRestrictions) {
            String stage = restriction.getStage();

            if (playerStageData.getStages().contains(stage)) {
                unlockItems.addAll(restriction.getItems());
            }
        }

        HardcoreAstagesItem.LOGGER.info("Get Unlock Item List: {}", unlockItems);
        return unlockItems;
    }

    public static List<Item> getUnknownItemList(Player player) {
        PlayerStage playerStageData = player.getData(AProvider.PLAYER_STAGE);
        AItemManager itemManager = ARestrictionManager.ITEM_INSTANCE;
        List<AItemRestriction> itemRestrictions = itemManager.getItemRestrictions();

        unknownItems.clear();

        for (AItemRestriction restriction : itemRestrictions) {
            String stage = restriction.getStage();

            if (!playerStageData.getStages().contains(stage)) {
                unknownItems.addAll(restriction.getItems());
            }
        }

        HardcoreAstagesItem.LOGGER.info("Get Unknown Item List: {}", unknownItems);
        return unknownItems;
    }
}
