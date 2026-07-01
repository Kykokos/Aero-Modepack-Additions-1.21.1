package net.kykokos.amadditions.util;


import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;


@EventBusSubscriber(value = Dist.CLIENT)
public class PhotonClearKeybind {
    public static final KeyMapping CLEAR_PHOTON_EFFECTS = new KeyMapping(
            "key.amadditions.clear_photon_effects",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_F8,
            "key.categories.amadditions"
    );

    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(CLEAR_PHOTON_EFFECTS);
    }
}
