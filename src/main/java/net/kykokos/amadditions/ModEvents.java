package net.kykokos.amadditions;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

@EventBusSubscriber
public class ModEvents {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();

        if (stack.has(ModDataComponents.WITH_ELECTROLYTES.get()) && Boolean.TRUE.equals(stack.get(ModDataComponents.WITH_ELECTROLYTES.get()))) {

            List<Component> tooltip = event.getToolTip();
            if (!tooltip.isEmpty()) {

                Component originalName = tooltip.get(0);

                MutableComponent newName = originalName.copy().append(Component.translatable("component.amadditions.with_electrolytes").withStyle(ChatFormatting.AQUA));

                tooltip.set(0, newName);
            }
        }
    }
    @SubscribeEvent
    public static void onConsumeItem(LivingEntityUseItemEvent.Finish event) {
        ItemStack stack = event.getItem();
        LivingEntity entity = event.getEntity();

        if (!entity.level().isClientSide()) {
            if (stack.has(ModDataComponents.WITH_ELECTROLYTES.get()) && Boolean.TRUE.equals(stack.get(ModDataComponents.WITH_ELECTROLYTES.get()))) {

                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 3));
            }
        }
    }
}
