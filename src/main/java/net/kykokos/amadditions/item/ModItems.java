package net.kykokos.amadditions.item;

import net.kykokos.amadditions.AeroModepackAdditions;
import net.kykokos.amadditions.block.ModBlocks;
import net.kykokos.amadditions.item.custom.FancyRocketItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.List;

public class ModItems {
    public  static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AeroModepackAdditions.MODID);

    public static final DeferredItem<Item> ELECTROR = ITEMS.register("electror",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE)));

    public static final DeferredItem<Item> ELECTROR_POWDER = ITEMS.register("electror_powder",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE)));

    public static final DeferredItem<Item> SULFUR_CHUNK = ITEMS.register("sulfur_chunk",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> SULFUR_DUST = ITEMS.register("sulfur_dust",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<BlockItem> SULFUR_SPIKE = ITEMS.register("sulfur_spike",
            () -> new BlockItem(ModBlocks.SULFUR_SPIKE.get(), new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.amadditions.sulfur_spike"));

                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> FANCY_ROCKET = ITEMS.register("fancy_rocket",
            () -> new FancyRocketItem(new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
