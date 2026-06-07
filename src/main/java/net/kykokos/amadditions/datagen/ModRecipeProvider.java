package net.kykokos.amadditions.datagen;

import net.kykokos.amadditions.AeroModepackAdditions;
import net.kykokos.amadditions.block.ModBlocks;
import net.kykokos.amadditions.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {

        //List<ItemLike> IDK_SMELTRABLES = List.of(ModItems.RAW_IDK, ModBlocks.IDK_ORE, ModBlocks.DEEFSLATE_IDK_ORE);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_SULFUR.get(), 4)
                .pattern("SS ")
                .pattern("SS ")
                .pattern("   ")
                .define('S', ModBlocks.SULFUR_BLOCK)
                .unlockedBy("has_sulfur_block", has(ModBlocks.SULFUR_BLOCK)).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.SULFUR_BLOCK.get())
                .requires(ModItems.SULFUR_CHUNK, 9)
                .unlockedBy("has_sulfur_block", has(ModBlocks.SULFUR_BLOCK.get())).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.SULFUR_BLOCK.get())
                .requires(ModItems.SULFUR_SPIKE, 9)
                .unlockedBy("has_sulfur_block", has(ModBlocks.SULFUR_BLOCK.get())).save(recipeOutput, "amadditions:sulfur_block_from_spike");

        stairBuilder(ModBlocks.POLISHED_SULFUR_STAIRS.get(), Ingredient.of(ModBlocks.POLISHED_SULFUR)).group("polished_sulfur")
                        .unlockedBy("has_sulfur_block", has(ModBlocks.SULFUR_BLOCK)).save(recipeOutput);
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_SULFUR_SLAB.get(), ModBlocks.POLISHED_SULFUR.get());
        wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_SULFUR_WALL.get(), ModBlocks.POLISHED_SULFUR.get());

        stairBuilder(ModBlocks.SULFUR_BRICK_STAIRS.get(), Ingredient.of(ModBlocks.SULFUR_BRICKS)).group("sulfur_bricks")
                .unlockedBy("has_sulfur_block", has(ModBlocks.SULFUR_BLOCK)).save(recipeOutput);
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SULFUR_BRICK_SLAB.get(), ModBlocks.SULFUR_BRICKS.get());
        wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SULFUR_BRICK_WALL.get(), ModBlocks.SULFUR_BRICKS.get());

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.SULFUR_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_SULFUR.get(), 1)
                .unlockedBy("has_sulfur_block", has(ModBlocks.SULFUR_BLOCK.get()))
                .save(recipeOutput, "amadditions:polished_sulfur_from_stonecutting");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.SULFUR_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, ModBlocks.SULFUR_BRICKS.get(), 1)
                .unlockedBy("has_sulfur_block", has(ModBlocks.SULFUR_BLOCK.get()))
                .save(recipeOutput, "amadditions:sulfur_bricks_from_stonecutting");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.SULFUR_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_SULFUR_SLAB.get(), 2)
                .unlockedBy("has_sulfur_block", has(ModBlocks.SULFUR_BLOCK.get()))
                .save(recipeOutput, "amadditions:polished_sulfur_slab_from_stonecutting");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.POLISHED_SULFUR.get()), RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_SULFUR_SLAB.get(), 2)
                .unlockedBy("has_sulfur_block", has(ModBlocks.SULFUR_BLOCK.get()))
                .save(recipeOutput, "amadditions:polished_sulfur_slab_from_stonecutting_2");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.SULFUR_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_SULFUR_STAIRS.get(), 1)
                .unlockedBy("has_sulfur_block", has(ModBlocks.SULFUR_BLOCK.get()))
                .save(recipeOutput, "amadditions:polished_sulfur_stairs_from_stonecutting");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.POLISHED_SULFUR.get()), RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_SULFUR_STAIRS.get(), 1)
                .unlockedBy("has_sulfur_block", has(ModBlocks.SULFUR_BLOCK.get()))
                .save(recipeOutput, "amadditions:polished_sulfur_stairs_from_stonecutting_2");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.SULFUR_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_SULFUR_WALL.get(), 1)
                .unlockedBy("has_sulfur_block", has(ModBlocks.SULFUR_BLOCK.get()))
                .save(recipeOutput, "amadditions:polished_sulfur_wall_from_stonecutting");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.POLISHED_SULFUR.get()), RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_SULFUR_WALL.get(), 1)
                .unlockedBy("has_sulfur_block", has(ModBlocks.SULFUR_BLOCK.get()))
                .save(recipeOutput, "amadditions:polished_sulfur_wall_from_stonecutting_2");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.SULFUR_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, ModBlocks.SULFUR_BRICK_SLAB.get(), 2)
                .unlockedBy("has_sulfur_block", has(ModBlocks.SULFUR_BLOCK.get()))
                .save(recipeOutput, "amadditions:sulfur_brick_slab_from_stonecutting");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.SULFUR_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS, ModBlocks.SULFUR_BRICK_SLAB.get(), 2)
                .unlockedBy("has_sulfur_block", has(ModBlocks.SULFUR_BLOCK.get()))
                .save(recipeOutput, "amadditions:sulfur_brick_slab_from_stonecutting_2");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.SULFUR_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, ModBlocks.SULFUR_BRICK_STAIRS.get(), 1)
                .unlockedBy("has_sulfur_block", has(ModBlocks.SULFUR_BLOCK.get()))
                .save(recipeOutput, "amadditions:sulfur_brick_stairs_from_stonecutting");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.SULFUR_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS, ModBlocks.SULFUR_BRICK_STAIRS.get(), 1)
                .unlockedBy("has_sulfur_block", has(ModBlocks.SULFUR_BLOCK.get()))
                .save(recipeOutput, "amadditions:sulfur_brick_stairs_from_stonecutting_2");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.SULFUR_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, ModBlocks.SULFUR_BRICK_WALL.get(), 1)
                .unlockedBy("has_sulfur_block", has(ModBlocks.SULFUR_BLOCK.get()))
                .save(recipeOutput, "amadditions:sulfur_brick_wall_from_stonecutting");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.SULFUR_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS, ModBlocks.SULFUR_BRICK_WALL.get(), 1)
                .unlockedBy("has_sulfur_block", has(ModBlocks.SULFUR_BLOCK.get()))
                .save(recipeOutput, "amadditions:sulfur_brick_wall_from_stonecutting_2");




        //oreSmelting(recipeOutput, IDK_SMELTRABLES, RecipeCategory.MISC, ModItems.IDK.get(), 0.25f, 200, "idk");
        //oreBlasting(recipeOutput, IDK_SMELTRABLES, RecipeCategory.MISC, ModItems.IDK.get(), 0.25f, 100, "idk");

    }

    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                       List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, AeroModepackAdditions.MODID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
