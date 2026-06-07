package net.kykokos.amadditions.block;

import net.kykokos.amadditions.AeroModepackAdditions;
import net.kykokos.amadditions.block.custom.CustomDripstoneBlock;
import net.kykokos.amadditions.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static net.minecraft.world.level.block.Blocks.POINTED_DRIPSTONE;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(AeroModepackAdditions.MODID);

    public static final DeferredBlock<Block> SULFUR_BLOCK = registerBlock("sulfur_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops().sound(SoundType.TUFF)));

    public static final DeferredBlock<Block> POLISHED_SULFUR = registerBlock("polished_sulfur",
            () -> new Block(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops().sound(SoundType.TUFF)));

    public static final DeferredBlock<Block> SULFUR_BRICKS = registerBlock("sulfur_bricks",
            () -> new Block(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops().sound(SoundType.TUFF)));


    public static final DeferredBlock<StairBlock> POLISHED_SULFUR_STAIRS = registerBlock("polished_sulfur_stairs",
            () -> new StairBlock(ModBlocks.POLISHED_SULFUR.get().defaultBlockState(),
                    BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops().sound(SoundType.TUFF)));

    public static final DeferredBlock<StairBlock> SULFUR_BRICK_STAIRS = registerBlock("sulfur_brick_stairs",
            () -> new StairBlock(ModBlocks.SULFUR_BRICKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops().sound(SoundType.TUFF)));


    public static final DeferredBlock<SlabBlock> POLISHED_SULFUR_SLAB = registerBlock("polished_sulfur_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops().sound(SoundType.TUFF)));

    public static final DeferredBlock<SlabBlock> SULFUR_BRICK_SLAB = registerBlock("sulfur_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops().sound(SoundType.TUFF)));


    public static final DeferredBlock<WallBlock> POLISHED_SULFUR_WALL = registerBlock("polished_sulfur_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops().sound(SoundType.TUFF)));

    public static final DeferredBlock<WallBlock> SULFUR_BRICK_WALL = registerBlock("sulfur_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops().sound(SoundType.TUFF)));


    public static final DeferredBlock<Block> SULFUR_SPIKE = BLOCKS.registerBlock("sulfur_spike",
            CustomDripstoneBlock::new, BlockBehaviour.Properties.ofFullCopy(POINTED_DRIPSTONE).randomTicks());


    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name,block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }


    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
