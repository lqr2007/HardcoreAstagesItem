package com.liquor.hardcoreastagesitem;

import com.liquor.hardcoreastagesitem.events.AstagesEvent;
import com.liquor.hardcoreastagesitem.register.Command;
import com.liquor.hardcoreastagesitem.register.UnknownItem;
import com.liquor.hardcoreastagesitem.utils.Getitemlist;
import com.liquor.hardcoreastagesitem.utils.ModelOperation;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

import java.util.HashMap;
import java.util.Map;

@Mod(HardcoreAstagesItem.MODID)
public class HardcoreAstagesItem {
    public static final String MODID = "hardcoreastagesitem";

    public static final Logger LOGGER = LogUtils.getLogger();

    public static Map<String, BakedModel> replacedMap = new HashMap<>();

    public HardcoreAstagesItem() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        UnknownItem.register(modEventBus);

        MinecraftForge.EVENT_BUS.addListener(this::onPlayerLoggedInEvent);
        MinecraftForge.EVENT_BUS.register(AstagesEvent.class);
        MinecraftForge.EVENT_BUS.addListener(this::onRegisterCommands);
    }

    public void onRegisterCommands(RegisterClientCommandsEvent event) {
        Command.registerCommands(event);
    }

    @SubscribeEvent
    public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();

        ModelOperation.replaceModel(Getitemlist.getUnknownItemList(player), true);
        ModelOperation.replaceModel(Getitemlist.getItem(player), false);
    }
}
