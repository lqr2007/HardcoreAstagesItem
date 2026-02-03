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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelOperation {

    private static final Map<ResourceLocation, BakedModel> originalCache = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static void replaceModel(List<Item> itemList, boolean lock) {

        Minecraft mc = Minecraft.getInstance();
        ModelManager modelManager = mc.getModelManager();

        ResourceLocation unknownRes = ResourceLocation.parse("hardcoreastagesitem:unknown_item");
        ModelResourceLocation unknownLoc = new ModelResourceLocation(unknownRes, "inventory");
        BakedModel unknownModel = modelManager.getModel(unknownLoc);

        Map<ModelResourceLocation, BakedModel> bakedRegistry;

        try {
            Field field = ModelManager.class.getDeclaredField("bakedRegistry");
            field.setAccessible(true);
            bakedRegistry = (Map<ModelResourceLocation, BakedModel>) field.get(modelManager);
        } catch (Exception e) {
            HardcoreAstagesItem.LOGGER.error("Cannot access bakedRegistry", e);
            return;
        }

        for (Item item : itemList) {

            ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
            ModelResourceLocation modelLoc = new ModelResourceLocation(id, "inventory");

            if (lock) {

                // 如果已经锁过，不要重复保存
                if (originalCache.containsKey(id)) continue;

                BakedModel original = modelManager.getModel(modelLoc);

                originalCache.put(id, original);
                bakedRegistry.put(modelLoc, unknownModel);

                HardcoreAstagesItem.LOGGER.debug("LOCK {}", id);

            } else {

                BakedModel original = originalCache.remove(id);

                // 没锁过就不需要解锁
                if (original == null) continue;

                bakedRegistry.put(modelLoc, original);

                HardcoreAstagesItem.LOGGER.debug("UNLOCK {}", id);
            }
        }

        mc.getItemRenderer().onResourceManagerReload(mc.getResourceManager());
    }
}
