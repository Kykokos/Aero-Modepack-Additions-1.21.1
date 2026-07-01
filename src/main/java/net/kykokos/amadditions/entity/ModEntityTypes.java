package net.kykokos.amadditions.entity;

import net.kykokos.amadditions.AeroModepackAdditions;
import net.kykokos.amadditions.entity.custom.FancyRocketEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


public class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, AeroModepackAdditions.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<FancyRocketEntity>> FANCY_ROCKET =
            ENTITY_TYPES.register("fancy_rocket",
                    () -> EntityType.Builder.<FancyRocketEntity>of(FancyRocketEntity::new, MobCategory.MISC)
                            .sized(0.25f, 0.25f)
                            .clientTrackingRange(4)
                            .updateInterval(10)
                            .build("fancy_rocket"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
