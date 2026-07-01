package net.kykokos.amadditions.util;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;

@EventBusSubscriber(value = Dist.CLIENT)
public class PhotonKeybindHandler {
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (PhotonClearKeybind.CLEAR_PHOTON_EFFECTS.consumeClick()) {
            var mc = net.minecraft.client.Minecraft.getInstance();
            if (mc.player != null) {
                mc.player.connection.sendCommand("photon_client clear_particles");
                mc.player.displayClientMessage(net.minecraft.network.chat.Component.literal("§aPhoton effects cleared"), true);
            }
        }
    }
}

