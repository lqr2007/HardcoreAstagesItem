package com.liquor.hardcoreastagesitem;

import com.liquor.hardcoreastagesitem.events.astagesEvent;
import com.liquor.hardcoreastagesitem.register.commands;
import com.liquor.hardcoreastagesitem.register.unknownItem;
import com.liquor.hardcoreastagesitem.utils.getItemList;
import com.liquor.hardcoreastagesitem.utils.modelOperation;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import net.neoforged.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod(HardcoreAstagesItem.MODID)
public class HardcoreAstagesItem {
    public static final String MODID = "hardcoreastagesitem";

    public static final Logger LOGGER = LogUtils.getLogger();

    public static Map<String, BakedModel> replacedMap = new HashMap<>();

    public HardcoreAstagesItem(IEventBus modEventBus) {

        unknownItem.register(modEventBus);

        NeoForge.EVENT_BUS.addListener(this::onPlayerLoggedInEvent);
        NeoForge.EVENT_BUS.register(astagesEvent.class);
        NeoForge.EVENT_BUS.register(commands.class);
    }

    @SubscribeEvent
    public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();

        modelOperation.replaceModel(getItemList.getUnknownItemList(player), "lock");
        modelOperation.replaceModel(getItemList.getUnlockItemList(player), "unlock");
    }
}
