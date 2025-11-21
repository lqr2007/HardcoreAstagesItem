package com.liquor.hardcoreastagesitem.utils;

import com.liquor.hardcoreastagesitem.HardcoreAstagesItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class modelOperation {

    @SuppressWarnings("unchecked")
    public static void replaceModel(List<Item> itemList, String method) {

        Minecraft minecraft = Minecraft.getInstance();
        ModelManager modelManager = minecraft.getModelManager();

        ResourceLocation unknownRes = ResourceLocation.parse("hardcoreastagesitem:unknown_item");
        ModelResourceLocation unknownModelLoc = new ModelResourceLocation(unknownRes, "inventory");
        BakedModel unknownModel = modelManager.getModel(unknownModelLoc);

        Map<ModelResourceLocation, BakedModel> bakedRegistry;

        try {
            Field field = ModelManager.class.getDeclaredField("bakedRegistry");
            field.setAccessible(true);
            bakedRegistry = (Map<ModelResourceLocation, BakedModel>) field.get(modelManager);

        } catch (Exception e) {
            HardcoreAstagesItem.LOGGER.error("Unable to access bakedRegistry field!", e);
            return;
        }

        for (Item item : itemList) {

            ResourceLocation registryName = BuiltInRegistries.ITEM.getKey(item);

            ModelResourceLocation modelLoc = new ModelResourceLocation(registryName, "inventory");

            if (method.equals("lock")) {

                BakedModel originalModel = modelManager.getModel(modelLoc);

                HardcoreAstagesItem.replacedMap.put(registryName.toString(), originalModel);

                bakedRegistry.put(modelLoc, unknownModel);
                HardcoreAstagesItem.LOGGER.debug("Locked model: {} to unknown", registryName);
            } else if (method.equals("unlock")) {

                BakedModel original = HardcoreAstagesItem.replacedMap.get(registryName.toString());

                if (original == null) {
                    HardcoreAstagesItem.LOGGER.warn("No original model stored for {}, cannot unlock.", registryName);
                    continue;
                }

                bakedRegistry.put(modelLoc, original);
                HardcoreAstagesItem.LOGGER.debug("Unlocked model: {} to restored", registryName);
            } else {
                HardcoreAstagesItem.LOGGER.warn("Unknown method '{}' used in replaceModel()", method);
                return;
            }
        }

        minecraft.getItemRenderer().onResourceManagerReload(minecraft.getResourceManager());
    }
}
