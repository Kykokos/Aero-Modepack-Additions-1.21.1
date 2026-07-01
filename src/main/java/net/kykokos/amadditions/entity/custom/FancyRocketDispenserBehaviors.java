package net.kykokos.amadditions.entity.custom;

import net.kykokos.amadditions.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

public class FancyRocketDispenserBehaviors {

    public static void register() {
        DispenserBlock.registerBehavior(ModItems.FANCY_ROCKET.get(), new DefaultDispenseItemBehavior() {
            @Override
            protected ItemStack execute(BlockSource source, ItemStack stack) {
                Level level = source.level();
                Direction facing = source.state().getValue(DispenserBlock.FACING);
                BlockPos pos = source.pos().relative(facing);

                double x = pos.getX() + 0.5;
                double y = pos.getY() + 0.1;
                double z = pos.getZ() + 0.5;

                FancyRocketEntity rocket = FancyRocketEntity.shoot(level, x, y, z, 2);

                double speed = 0.5;
                rocket.setDeltaMovement(
                        facing.getStepX() * speed,
                        facing.getStepY() * speed + 0.05,
                        facing.getStepZ() * speed
                );

                level.addFreshEntity(rocket);
                level.playSound(null, pos, SoundEvents.FIREWORK_ROCKET_LAUNCH,
                        SoundSource.NEUTRAL, 1.0f, 1.0f);

                stack.shrink(1);
                return stack;
            }
        });
    }
}
