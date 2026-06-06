package net.kykokos.amadditions.util;

import com.mojang.serialization.Codec;
import net.kykokos.amadditions.AeroModepackAdditions;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static net.neoforged.neoforge.registries.DeferredRegister.createDataComponents;


public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, AeroModepackAdditions.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> WITH_ELECTROLYTES =
            DATA_COMPONENT_TYPES.register("with_electrolytes", () -> DataComponentType.<Boolean>builder()
                    .persistent(Codec.BOOL)
                    .networkSynchronized(ByteBufCodecs.BOOL)
                    .build());
}
