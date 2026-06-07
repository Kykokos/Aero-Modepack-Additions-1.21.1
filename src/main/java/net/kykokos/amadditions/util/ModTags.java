package net.kykokos.amadditions.util;

import net.kykokos.amadditions.AeroModepackAdditions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {

        public static final TagKey<Block> SULFUR_BLOCK = BlockTags.create(ResourceLocation.fromNamespaceAndPath("c", "storage_blocks/sulfur"));

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(AeroModepackAdditions.MODID, name));
        }
    }

    public static class Items {

        //public static final TagKey<Item> SUPERDUPERIDK_ITEMS = createTag("superduperidk_items");


        public static final TagKey<Item> SULFUR_BLOCK_ITEM = ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "storage_blocks/sulfur"));
        public static final TagKey<Item> SULFUR_GEM = ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "gems/sulfur"));
        public static final TagKey<Item> SULFUR_DUST = ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "dusts/sulfur"));

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(AeroModepackAdditions.MODID, name));
        }
    }
}
