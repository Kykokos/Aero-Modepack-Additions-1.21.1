package net.kykokos.amadditions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EventBusSubscriber
public class RuntimeRecipeInjector {

    @SuppressWarnings("unchecked")
    @SubscribeEvent
    public static void onServerAboutToStart(ServerAboutToStartEvent event) {
        RecipeManager recipeManager = event.getServer().getRecipeManager();

        ResourceLocation fillingId = ResourceLocation.fromNamespaceAndPath("create", "filling");
        RecipeType<?> fillingType = BuiltInRegistries.RECIPE_TYPE.get(fillingId);
        RecipeSerializer<?> fillingSerializer = BuiltInRegistries.RECIPE_SERIALIZER.get(fillingId);

        if (fillingType == null || fillingSerializer == null) {
            System.err.println("[AmAdditions] Create Spout registry wasn't found! Is create mod active?");
            return;
        }

        TagKey<Item> foodTagKey = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "foods"));
        var itemRegistry = event.getServer().registryAccess().registryOrThrow(Registries.ITEM);
        List<Item> foodItems = new ArrayList<>();

        var tagOptional = itemRegistry.getTag(foodTagKey);
        if (tagOptional.isPresent()) {
            for (Holder<Item> holder : tagOptional.get()) {
                foodItems.add(holder.value());
            }
        } else {
            for (Item item : BuiltInRegistries.ITEM) {
                if (item.components().has(DataComponents.FOOD)) {
                    foodItems.add(item);
                }
            }
        }

        List<RecipeHolder<?>> newRecipesList = new ArrayList<>();

        for (Item item : foodItems) {
            ResourceLocation itemKey = BuiltInRegistries.ITEM.getKey(item);
            if (itemKey.getNamespace().equals("minecraft") && itemKey.getPath().equals("air")) continue;

            JsonObject json = new JsonObject();
            json.addProperty("type", "create:filling");

            JsonArray ingredients = new JsonArray();
            JsonObject ingredientItem = new JsonObject();
            ingredientItem.addProperty("item", itemKey.toString());
            ingredients.add(ingredientItem);

            JsonObject ingredientFluid = new JsonObject();
            ingredientFluid.addProperty("fluid", "minecraft:water");
            ingredientFluid.addProperty("type", "fluid_stack");
            ingredientFluid.addProperty("amount", 250);
            ingredients.add(ingredientFluid);
            json.add("ingredients", ingredients);

            JsonArray results = new JsonArray();
            JsonObject resultItem = new JsonObject();
            resultItem.addProperty("id", itemKey.toString());

            JsonObject components = new JsonObject();
            components.addProperty("amadditions:with_electrolytes", true);
            resultItem.add("components", components);
            results.add(resultItem);
            json.add("results", results);

            ResourceLocation recipeId = ResourceLocation.fromNamespaceAndPath(
                    "amadditions", "filling_" + itemKey.getNamespace() + "_" + itemKey.getPath()
            );

            try {
                Recipe<?> recipe = (Recipe<?>) fillingSerializer.codec().codec()
                        .parse(JsonOps.INSTANCE, json)
                        .getOrThrow(error -> new IllegalStateException("Create recipe parsing failed: " + error));

                newRecipesList.add(new RecipeHolder<>(recipeId, recipe));
            } catch (Exception e) {
                System.err.println("[AmAdditions] Parsing failed for " + itemKey + ": " + e.getMessage());
            }
        }

        try {
            int mapsInjected = 0;
            int multimapsInjected = 0;

            for (Field field : RecipeManager.class.getDeclaredFields()) {
                field.setAccessible(true);
                Object obj = field.get(recipeManager);
                if (obj == null) continue;

                if (obj instanceof Map<?, ?> standardMap) {
                    if (!standardMap.isEmpty()) {
                        Object firstKey = standardMap.keySet().iterator().next();
                        Object firstValue = standardMap.values().iterator().next();


                        if (firstKey instanceof ResourceLocation && firstValue instanceof RecipeHolder<?>) {
                            Map<ResourceLocation, RecipeHolder<?>> mutableByName = new HashMap<>((Map<ResourceLocation, RecipeHolder<?>>) standardMap);
                            for (RecipeHolder<?> holder : newRecipesList) {
                                mutableByName.put(holder.id(), holder);
                            }
                            field.set(recipeManager, Map.copyOf(mutableByName));
                            mapsInjected++;
                        }

                        else if (firstKey instanceof RecipeType<?> && firstValue instanceof Map<?, ?>) {
                            Map<RecipeType<?>, Map<ResourceLocation, RecipeHolder<?>>> mutableByType = new HashMap<>((Map<RecipeType<?>, Map<ResourceLocation, RecipeHolder<?>>>) standardMap);
                            Map<ResourceLocation, RecipeHolder<?>> fillingRecipes = new HashMap<>(mutableByType.getOrDefault(fillingType, Map.of()));
                            for (RecipeHolder<?> holder : newRecipesList) {
                                fillingRecipes.put(holder.id(), holder);
                            }
                            mutableByType.put(fillingType, fillingRecipes);
                            field.set(recipeManager, Map.copyOf(mutableByType));
                            mapsInjected++;
                        }
                    }
                }

                else if (obj instanceof Multimap<?, ?> multimap) {
                    if (!multimap.isEmpty()) {
                        Object firstKey = multimap.keySet().iterator().next();
                        Object firstValue = multimap.values().iterator().next();

                        if (firstKey instanceof RecipeType<?> && firstValue instanceof RecipeHolder<?>) {
                            ImmutableListMultimap.Builder<RecipeType<?>, RecipeHolder<?>> builder = ImmutableListMultimap.builder();

                            builder.putAll((Multimap<RecipeType<?>, RecipeHolder<?>>) multimap);

                            for (RecipeHolder<?> holder : newRecipesList) {
                                builder.put(fillingType, holder);
                            }

                            field.set(recipeManager, builder.build());
                            multimapsInjected++;
                        }
                    }
                }
            }

            System.out.println("[AmAdditions] Successfully injected " + newRecipesList.size() +
                    " recipes to game (Modified maps: " + mapsInjected + ", multimaps: " + multimapsInjected + ")");

        } catch (Exception e) {
            System.err.println("[AmAdditions] The final runtime write failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
