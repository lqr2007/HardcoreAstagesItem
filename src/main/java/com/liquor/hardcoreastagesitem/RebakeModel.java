package com.liquor.hardcoreastagesitem;

import com.alessandro.astages.event.custom.actions.StageAddedPlayerEvent;
import com.alessandro.astages.event.custom.actions.StageRemovedPlayerEvent;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class RebakeModel {

    public static void replaceModel(ModelResourceLocation originModel, BakedModel replaceModel, ModelManager modelManager) {
        Minecraft minecraft = Minecraft.getInstance();

        try {
            Field modelsField = ModelManager.class.getDeclaredField("bakedRegistry");
            modelsField.setAccessible(true);

            @SuppressWarnings("unchecked")
            Map<ModelResourceLocation, BakedModel> bakedRegistry =
                    (Map<ModelResourceLocation, BakedModel>) modelsField.get(modelManager);

            bakedRegistry.put(originModel, replaceModel);
            HardcoreAstagesItem.LOGGER.debug("Replaced Success: {} for {}", originModel, replaceModel);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            HardcoreAstagesItem.LOGGER.error("Replaced Failed: {} for {}", originModel, replaceModel);
            HardcoreAstagesItem.LOGGER.error("Exception: ", e);
        }

        minecraft.getItemRenderer().onResourceManagerReload(minecraft.getResourceManager());
    }

    public static void reloadModel() {
        Minecraft minecraft = Minecraft.getInstance();
        ModelManager modelManager = minecraft.getModelManager();

        if (ServerLifecycleHooks.getCurrentServer() != null) {
            for (ServerPlayer onlinePlayer : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
                List<Item> unlockItems = GetItemList.GetUnlockItemList(onlinePlayer);
                for (Item item : unlockItems) {
                    ResourceLocation itemRegistryName = BuiltInRegistries.ITEM.getKey(item);

                    ModelResourceLocation originModel = new ModelResourceLocation(itemRegistryName, "inventory");

                    BakedModel replaceModel = HardcoreAstagesItem.replacedMap.get(itemRegistryName.toString());

                    if (replaceModel == null) {
                        HardcoreAstagesItem.LOGGER.warn("No replacement model found for: {}", itemRegistryName);
                        continue;
                    }

                    replaceModel(originModel, replaceModel, modelManager);

                    HardcoreAstagesItem.LOGGER.debug("Reload Model Item Name: {}", itemRegistryName);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onStageRemoved(StageRemovedPlayerEvent event) {

        Minecraft minecraft = Minecraft.getInstance();
        ModelManager modelManager = minecraft.getModelManager();

        ResourceLocation unknownItemResource = ResourceLocation.parse("hardcoreastagesitem:unknown_item");
        ModelResourceLocation unknownModel = new ModelResourceLocation(unknownItemResource, "inventory");
        BakedModel replaceModel = modelManager.getModel(unknownModel);

        if (ServerLifecycleHooks.getCurrentServer() != null) {
            for (ServerPlayer onlinePlayer : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
                List<Item> unknownItems = GetItemList.GetUnknownItemList(onlinePlayer);
                HardcoreAstagesItem.LOGGER.debug("Get Unknown Item: {}", unknownItems);
                for (Item itemName : unknownItems) {
                    ResourceLocation originResource = ResourceLocation.parse(String.valueOf(itemName));
                    ModelResourceLocation originModel = new ModelResourceLocation(originResource, "inventory");
                    BakedModel rawModel = modelManager.getModel(originModel);

                    HardcoreAstagesItem.replacedMap.put(String.valueOf(itemName), rawModel);

                    RebakeModel.replaceModel(originModel, replaceModel, modelManager);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onStageAdd(StageAddedPlayerEvent event) {
        reloadModel();
    }

    @SubscribeEvent
    public static void registerCommands(RegisterClientCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(Commands.literal("reloadmodel").requires(cs -> cs.hasPermission(0)).executes(context -> {
            onStageRemoved(null);
            reloadModel();
            return 1;
        }));
    }
}
