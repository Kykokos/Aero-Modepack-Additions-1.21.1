package net.kykokos.amadditions.entity.custom;

import com.lowdragmc.photon.client.fx.*;
import net.kykokos.amadditions.Config;
import net.kykokos.amadditions.entity.ModEntityTypes;
import net.kykokos.amadditions.item.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class FancyRocketEntity extends Entity implements ItemSupplier {
    private static final ResourceLocation THRUSTER_FX = ResourceLocation.parse("amadditions:firework_thruster");
    private static final ResourceLocation EXPLOSION_FX = ResourceLocation.parse("amadditions:firework_explosion");

    private static final EntityDataAccessor<Integer> DATA_FLIGHT_DURATION =
            SynchedEntityData.defineId(FancyRocketEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_LIFE_TIME =
            SynchedEntityData.defineId(FancyRocketEntity.class, EntityDataSerializers.INT);

    private int life = 0;
    private EntityEffectExecutor thrusterEffect;

    public FancyRocketEntity(EntityType<? extends FancyRocketEntity> type, Level level) {
        super(type, level);
    }

    public static FancyRocketEntity shoot(Level level, double x, double y, double z, int flightDuration) {
        FancyRocketEntity rocket = new FancyRocketEntity(ModEntityTypes.FANCY_ROCKET.get(), level);
        rocket.setPos(x, y, z);
        rocket.setFlightDuration(Math.max(1, flightDuration));

        int computedLifeTime = (flightDuration + 1) * 10
                + level.getRandom().nextInt(6)
                + level.getRandom().nextInt(7);
        rocket.setLifeTime(computedLifeTime);

        rocket.setDeltaMovement(
                level.getRandom().triangle(0.0, 0.002297),
                0.05,
                level.getRandom().triangle(0.0, 0.002297)
        );
        return rocket;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_FLIGHT_DURATION, 1);
        builder.define(DATA_LIFE_TIME, 0);
    }

    public void setFlightDuration(int v) { entityData.set(DATA_FLIGHT_DURATION, v); }
    public int getFlightDuration()       { return entityData.get(DATA_FLIGHT_DURATION); }
    public void setLifeTime(int v)       { entityData.set(DATA_LIFE_TIME, v); }
    public int getLifeTime()             { return entityData.get(DATA_LIFE_TIME); }

    @Override
    public void tick() {
        super.tick();

        if (level().isClientSide() && thrusterEffect == null) {
            startThrusterEffect();
        }

        Vec3 motion = getDeltaMovement();
        setDeltaMovement(motion.x * 1.15, motion.y + 0.04, motion.z * 1.15);
        move(MoverType.SELF, getDeltaMovement());

        if (level().isClientSide() && random.nextInt(2) == 0) {
            level().addParticle(ParticleTypes.SMOKE,
                    getX() + (random.nextDouble() - 0.5) * 0.1,
                    getY() - 0.3,
                    getZ() + (random.nextDouble() - 0.5) * 0.1,
                    0.0, 0.0, 0.0);
        }

        life++;
        if (!level().isClientSide() && life > getLifeTime()) {
            discard();
        }
    }

    private void startThrusterEffect() {
        FX fx = FXHelper.getFX(THRUSTER_FX);
        if (fx == null) return;
        if (!Config.ENABLE_EFFECTS.get()) return;
        thrusterEffect = new EntityEffectExecutor(fx, level(), this, EntityEffectExecutor.AutoRotate.NONE);
        thrusterEffect.setForcedDeath(false);
        thrusterEffect.start();
    }

    private void stopThrusterEffect() {
        if (thrusterEffect != null) {
            thrusterEffect.setForcedDeath(true);
            thrusterEffect = null;
        }
    }

    @Override
    public void onClientRemoval() {
        spawnExplosionEffect();
        level().playLocalSound(getX(), getY(), getZ(),
                SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.NEUTRAL, 3.0f, 1.0f, false);
        stopThrusterEffect();
    }

    private void spawnExplosionEffect() {
        FX fx = FXHelper.getFX(EXPLOSION_FX);
        if (fx == null) return;
        if (!Config.ENABLE_EFFECTS.get()) return;

        BlockEffectExecutor explosion = new BlockEffectExecutor(fx, level(), blockPosition());
        explosion.setAllowMulti(true);
        explosion.start();
    }

    @Override
    public void remove(RemovalReason reason) {
        if (level().isClientSide()) {
            stopThrusterEffect();
        }
        if (!level().isClientSide() && reason == RemovalReason.DISCARDED) {
            applyExplosionDamage();
        }
        super.remove(reason);
    }

    private void applyExplosionDamage() {
        double radius = 4.0;
        float maxDamage = 6.0f;

        AABB area = new AABB(
                getX() - radius, getY() - radius, getZ() - radius,
                getX() + radius, getY() + radius, getZ() + radius
        );

        List<LivingEntity> nearby = level().getEntitiesOfClass(LivingEntity.class, area);

        for (LivingEntity target : nearby) {
            if (target.getId() == this.getId()) continue;
            double dist = target.position().distanceTo(position());
            if (dist >= radius) continue;
            float falloff = (float)(1.0 - dist / radius);
            float damage = maxDamage * falloff;
            if (damage > 0.5f) {
                target.hurt(level().damageSources().explosion(null, null), damage);
            }
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        this.life = tag.getInt("Life");
        setLifeTime(tag.getInt("LifeTime"));
        setFlightDuration(tag.getInt("FlightDuration"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("Life", this.life);
        tag.putInt("LifeTime", getLifeTime());
        tag.putInt("FlightDuration", getFlightDuration());
    }

    @Override public boolean isPickable() { return false; }
    @Override public boolean isPushable() { return false; }
    @Override public ItemStack getItem() { return new ItemStack(ModItems.FANCY_ROCKET.get()); }
}