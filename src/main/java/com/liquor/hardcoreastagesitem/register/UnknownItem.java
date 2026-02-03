package com.liquor.hardcoreastagesitem.register;

import com.liquor.hardcoreastagesitem.HardcoreAstagesItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class UnknownItem {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, HardcoreAstagesItem.MODID);

    public static final RegistryObject<Item> UNKNOWN_ITEM = ITEMS.register("unknown_item", () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
