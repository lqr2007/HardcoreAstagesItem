package com.liquor.hardcoreastagesitem;

import net.minecraft.client.resources.model.BakedModel;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.common.NeoForge;
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

    public static boolean isExecuted = false;

    public HardcoreAstagesItem(IEventBus modEventBus) {

        UnknownItem.register(modEventBus);

        NeoForge.EVENT_BUS.register(RebakeModel.class);
        NeoForge.EVENT_BUS.addListener(this::onPlayerEnterWorld);

    }

    @SubscribeEvent
    private void onPlayerEnterWorld(RenderGuiEvent.Post event) {
        if (!isExecuted) {
            isExecuted = true;
            RebakeModel.onStageRemoved(null);
            RebakeModel.reloadModel();
        }
    }
}
