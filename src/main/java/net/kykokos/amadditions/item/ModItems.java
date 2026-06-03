package net.kykokos.amadditions.item;

import net.kykokos.amadditions.AeroModepackAdditions;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public  static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AeroModepackAdditions.MODID);

    public static final DeferredItem<Item> ELECTROR = ITEMS.register("electror",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE)));

    public static final DeferredItem<Item> ELECTROR_POWDER = ITEMS.register("electror_powder",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
