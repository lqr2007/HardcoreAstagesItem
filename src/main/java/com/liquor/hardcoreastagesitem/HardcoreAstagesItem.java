package com.liquor.hardcoreastagesitem;

import com.liquor.hardcoreastagesitem.events.AstagesEvent;
import com.liquor.hardcoreastagesitem.register.Commands;
import com.liquor.hardcoreastagesitem.register.UnknownItem;
import com.liquor.hardcoreastagesitem.utils.Getitemlist;
import com.liquor.hardcoreastagesitem.utils.ModelOperation;
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

        UnknownItem.register(modEventBus);

        NeoForge.EVENT_BUS.addListener(this::onPlayerLoggedInEvent);
        NeoForge.EVENT_BUS.register(AstagesEvent.class);
        NeoForge.EVENT_BUS.register(Commands.class);
    }

    @SubscribeEvent
    public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();

        ModelOperation.replaceModel(Getitemlist.getUnknownItemList(player), true);
        ModelOperation.replaceModel(Getitemlist.getItem(player), false);
    }
}
