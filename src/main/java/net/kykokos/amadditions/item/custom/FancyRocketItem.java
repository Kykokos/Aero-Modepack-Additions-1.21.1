package net.kykokos.amadditions.item.custom;


import net.kykokos.amadditions.entity.custom.FancyRocketEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class FancyRocketItem extends Item {

    private static final int FLIGHT_DURATION = 2;

    public FancyRocketItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, net.minecraft.world.InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide()) {
            Vec3 spawnPos = getSpawnPosition(level, player);

            FancyRocketEntity rocket = FancyRocketEntity.shoot(
                    level, spawnPos.x, spawnPos.y, spawnPos.z, FLIGHT_DURATION
            );
            level.addFreshEntity(rocket);

            level.playSound(null, spawnPos.x, spawnPos.y, spawnPos.z,
                    SoundEvents.FIREWORK_ROCKET_LAUNCH, SoundSource.NEUTRAL, 1.0f, 1.0f);

            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }

        player.swing(hand);
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    private Vec3 getSpawnPosition(Level level, Player player) {
        Vec3 eyePos = player.getEyePosition(1.0f);
        Vec3 lookDir = player.getViewVector(1.0f);
        Vec3 endPos = eyePos.add(lookDir.scale(5.0));

        BlockHitResult hit = level.clip(new ClipContext(
                eyePos, endPos,
                ClipContext.Block.OUTLINE,
                ClipContext.Fluid.NONE,
                player
        ));

        if (hit.getType() == HitResult.Type.BLOCK) {

            Vec3 hitLoc = hit.getLocation();
            Vec3 faceNormal = Vec3.atLowerCornerOf(hit.getDirection().getNormal());
            return hitLoc.add(faceNormal.scale(0.2));
        }

        return new Vec3(player.getX(), player.getY() + 0.1, player.getZ());
    }
}
