package com.liquor.hardcoreastagesitem.register;

import com.liquor.hardcoreastagesitem.HardcoreAstagesItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class unknownItem {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(HardcoreAstagesItem.MODID);

    public static final DeferredItem<Item> unknownItem = ITEMS.register("unknown_item",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
