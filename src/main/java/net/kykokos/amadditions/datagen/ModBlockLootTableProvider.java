package net.kykokos.amadditions.datagen;

import net.kykokos.amadditions.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {

        add(ModBlocks.POLISHED_SULFUR_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.POLISHED_SULFUR_SLAB.get()));
        add(ModBlocks.SULFUR_BRICK_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.SULFUR_BRICK_SLAB.get()));

        dropSelf(ModBlocks.SULFUR_BLOCK.get());
        dropSelf(ModBlocks.SULFUR_SPIKE.get());
        dropSelf(ModBlocks.POLISHED_SULFUR.get());
        dropSelf(ModBlocks.SULFUR_BRICKS.get());
        dropSelf(ModBlocks.POLISHED_SULFUR_STAIRS.get());
        dropSelf(ModBlocks.POLISHED_SULFUR_WALL.get());
        dropSelf(ModBlocks.SULFUR_BRICK_STAIRS.get());
        dropSelf(ModBlocks.SULFUR_BRICK_WALL.get());


        /*add(ModBlocks.IDK_BLOCK.get(),
                block -> create-some-drop(ModBlocks.IDK_BLOCK.get(), ModItems.IDK_ITEM.get()));*/
    }


    protected LootTable.Builder createMultipleOreDrops(Block pBlock, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))));
    }


    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
