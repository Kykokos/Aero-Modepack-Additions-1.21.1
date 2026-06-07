package net.kykokos.amadditions.datagen;

import net.kykokos.amadditions.AeroModepackAdditions;
import net.kykokos.amadditions.block.ModBlocks;
import net.kykokos.amadditions.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, AeroModepackAdditions.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.ELECTROR.get());
        basicItem(ModItems.ELECTROR_POWDER.get());
        basicItem(ModItems.SULFUR_CHUNK.get());
        basicItem(ModItems.SULFUR_DUST.get());

        wallItem(ModBlocks.POLISHED_SULFUR_WALL, ModBlocks.POLISHED_SULFUR);
        wallItem(ModBlocks.SULFUR_BRICK_WALL, ModBlocks.SULFUR_BRICKS);
    }

    public void buttonItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/button_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(AeroModepackAdditions.MODID,
                        "block/" + baseBlock.getId().getPath()));
    }

    public void fenceItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/fence_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(AeroModepackAdditions.MODID,
                        "block/" + baseBlock.getId().getPath()));
    }

    public void wallItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  ResourceLocation.fromNamespaceAndPath(AeroModepackAdditions.MODID,
                        "block/" + baseBlock.getId().getPath()));
    }
}
