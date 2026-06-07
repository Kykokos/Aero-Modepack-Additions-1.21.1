package net.kykokos.amadditions.datagen;

import net.kykokos.amadditions.AeroModepackAdditions;
import net.kykokos.amadditions.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, AeroModepackAdditions.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.SULFUR_BLOCK);
        blockWithItem(ModBlocks.POLISHED_SULFUR);
        blockWithItem(ModBlocks.SULFUR_BRICKS);

        stairsBlock(ModBlocks.POLISHED_SULFUR_STAIRS.get(), blockTexture(ModBlocks.POLISHED_SULFUR.get()));
        stairsBlock(ModBlocks.SULFUR_BRICK_STAIRS.get(), blockTexture(ModBlocks.SULFUR_BRICKS.get()));

        slabBlock(ModBlocks.POLISHED_SULFUR_SLAB.get(), blockTexture(ModBlocks.POLISHED_SULFUR.get()), blockTexture(ModBlocks.POLISHED_SULFUR.get()));
        slabBlock(ModBlocks.SULFUR_BRICK_SLAB.get(), blockTexture(ModBlocks.SULFUR_BRICKS.get()), blockTexture(ModBlocks.SULFUR_BRICKS.get()));

        wallBlock(ModBlocks.POLISHED_SULFUR_WALL.get(), blockTexture(ModBlocks.POLISHED_SULFUR.get()));
        wallBlock(ModBlocks.SULFUR_BRICK_WALL.get(), blockTexture(ModBlocks.SULFUR_BRICKS.get()));

        blockItem(ModBlocks.POLISHED_SULFUR_SLAB);
        blockItem(ModBlocks.POLISHED_SULFUR_STAIRS);
        blockItem(ModBlocks.SULFUR_BRICK_SLAB);
        blockItem(ModBlocks.SULFUR_BRICK_STAIRS);

    }

    private void blockWithItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }

    private void blockItem(DeferredBlock<?> deferredBlock) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("amadditions:block/" + deferredBlock.getId().getPath()));
    }

    private void blockItem(DeferredBlock<?> deferredBlock, String appendix) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("amadditions:block/" + deferredBlock.getId().getPath() + appendix));
    }
}
