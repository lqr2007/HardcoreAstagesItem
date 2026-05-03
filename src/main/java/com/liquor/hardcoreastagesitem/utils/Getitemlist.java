package com.liquor.hardcoreastagesitem.utils;

import com.alessandro.astages.api.holder.AHolder;
import com.alessandro.astages.api.util.AStagesUtils;
import com.alessandro.astages.engine.ARestrictionManager;
import com.alessandro.astages.engine.server.manager.AItemManager;
import com.alessandro.astages.engine.server.restriction.item.AItemRestriction;
import com.liquor.hardcoreastagesitem.HardcoreAstagesItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Getitemlist {

    static AItemManager itemServerManager = ARestrictionManager.ITEM_INSTANCE;

    public static List<Item> getItem(Player player) {
        Set<String> playerStage = new HashSet<>(AStagesUtils.getStages(AHolder.player(player)));
        Set<Item> unknownItem = new HashSet<>(getUnknownItemList(player));

        HardcoreAstagesItem.LOGGER.debug("get player stages: {}", playerStage);

        Set<Item> unlock = new HashSet<>();

        for (AItemRestriction rule : itemServerManager.getRegistry().getItemRestrictions()) {
            if (playerStage.contains(rule.getStage())) {
                for (Item item : rule.getItems()) {
                    if (!unknownItem.contains(item)) {
                        unlock.add(item);
                        HardcoreAstagesItem.LOGGER.debug("get unlock items: {}", item);
                    }
                }
            }
            HardcoreAstagesItem.LOGGER.debug("item: {}, stage: {}", rule.getItems(), rule.getStage());
        }
        return new ArrayList<>(unlock);
    }

    public static List<Item> getUnknownItemList(Player player) {
        Set<String> playerStage = new HashSet<>(AStagesUtils.getStages(AHolder.player(player)));

        Set<Item> unknown = new HashSet<>();

        for (AItemRestriction rule : itemServerManager.getRegistry().getItemRestrictions()) {
            if (!playerStage.contains(rule.getStage())) {
                unknown.addAll(rule.getItems());
                HardcoreAstagesItem.LOGGER.debug("get unknown items: {}", rule.getItems());
            }
        }

        return new ArrayList<>(unknown);
    }
}
