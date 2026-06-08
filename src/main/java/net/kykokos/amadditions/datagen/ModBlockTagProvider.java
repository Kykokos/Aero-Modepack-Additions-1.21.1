package net.kykokos.amadditions.datagen;

import net.kykokos.amadditions.AeroModepackAdditions;
import net.kykokos.amadditions.block.ModBlocks;
import net.kykokos.amadditions.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, AeroModepackAdditions.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.SULFUR_BLOCK.get())
                .add(ModBlocks.POLISHED_SULFUR.get())
                .add(ModBlocks.POLISHED_SULFUR_SLAB.get())
                .add(ModBlocks.POLISHED_SULFUR_STAIRS.get())
                .add(ModBlocks.POLISHED_SULFUR_WALL.get())
                .add(ModBlocks.SULFUR_BRICKS.get())
                .add(ModBlocks.SULFUR_BRICK_SLAB.get())
                .add(ModBlocks.SULFUR_BRICK_STAIRS.get())
                .add(ModBlocks.SULFUR_BRICK_WALL.get());

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.SULFUR_BLOCK.get())
                .add(ModBlocks.POLISHED_SULFUR.get())
                .add(ModBlocks.POLISHED_SULFUR_SLAB.get())
                .add(ModBlocks.POLISHED_SULFUR_STAIRS.get())
                .add(ModBlocks.POLISHED_SULFUR_WALL.get())
                .add(ModBlocks.SULFUR_BRICKS.get())
                .add(ModBlocks.SULFUR_BRICK_SLAB.get())
                .add(ModBlocks.SULFUR_BRICK_STAIRS.get())
                .add(ModBlocks.SULFUR_BRICK_WALL.get());

        tag(BlockTags.WALLS)
                .add(ModBlocks.POLISHED_SULFUR_WALL.get())
                .add(ModBlocks.SULFUR_BRICK_WALL.get());


        tag(ModTags.Blocks.SULFUR_BLOCK)
                .add(ModBlocks.SULFUR_BLOCK.get());
    }
}
