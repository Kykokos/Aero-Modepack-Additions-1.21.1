package net.kykokos.amadditions.item;

import com.lowdragmc.photon.client.fx.EntityEffectExecutor;
import com.lowdragmc.photon.client.fx.FX;
import com.lowdragmc.photon.client.fx.FXHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.item.ItemEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber(value = Dist.CLIENT)
public class ItemForceFIeldHandler {

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {

        if (event.getEntity() instanceof ItemEntity itemEntity && itemEntity.level().isClientSide()) {


            if (itemEntity.getItem().is(ModItems.ELECTROR)) {

                CompoundTag data = itemEntity.getPersistentData();
                if (!data.getBoolean("has_force_field_fx")) {

                    FX fx = FXHelper.getFX(ResourceLocation.parse("amadditions:force_field"));

                    new EntityEffectExecutor(fx, itemEntity.level(), itemEntity, EntityEffectExecutor.AutoRotate.NONE).start();

                    data.putBoolean("has_force_field_fx", true);
                }
            }
        }
    }
}
