package net.kykokos.amadditions.datagen;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import net.kykokos.amadditions.AeroModepackAdditions;
import net.kykokos.amadditions.block.ModBlocks;
import net.kykokos.amadditions.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
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

        /*ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ELECTROR.get())
                .pattern(" I ")
                .pattern(" D ")
                .pattern(" K ")
                .define('I', Items.ECHO_SHARD)
                .define('D', Items.SCULK)
                .define('K', Items.LIGHTNING_ROD)
                .unlockedBy("has_echo_shard", has(Items.ECHO_SHARD)).save(recipeOutput);*/

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.SULFUR_BLOCK.get())
                .requires(ModItems.SULFUR_CHUNK, 9)
                .unlockedBy("has_sulfur_block", has(ModBlocks.SULFUR_BLOCK.get())).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.SULFUR_BLOCK.get())
                .requires(ModItems.SULFUR_SPIKE, 9)
                .unlockedBy("has_sulfur_block", has(ModBlocks.SULFUR_BLOCK.get())).save(recipeOutput, "amadditions:sulfur_block_from_spike");

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
