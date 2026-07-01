package net.kykokos.amadditions.item;

import com.lowdragmc.photon.client.fx.EntityEffectExecutor;
import com.lowdragmc.photon.client.fx.FX;
import com.lowdragmc.photon.client.gameobject.IFXObject;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class BobbingEntityEffectExecutor extends EntityEffectExecutor {

    public BobbingEntityEffectExecutor(FX fx, Level level, ItemEntity entity, AutoRotate autoRotate) {
        super(fx, level, entity, autoRotate);
    }

    @Override
    public void updateFXObjectFrame(IFXObject fxObject, float partialTicks) {
        if (runtime != null && fxObject == runtime.root) {
            if (!entity.isAlive()) return;

            var position = entity.getEyePosition(partialTicks);

            ItemEntity itemEntity = (ItemEntity) entity;
            float ageInTicks = entity.tickCount + partialTicks;
            float bobOffset = Mth.sin(ageInTicks / 10.0F + itemEntity.bobOffs) * 0.1F + 0.1F;

            runtime.root.updatePos(new Vector3f(
                    (float) (position.x + offset.x),
                    (float) (position.y + offset.y + bobOffset),
                    (float) (position.z + offset.z)
            ));

            if (autoRotate != AutoRotate.NONE) {
                switch (autoRotate) {
                    case FORWARD -> {
                        var forward = entity.getForward();
                        var newRotation = new Quaternionf(rotation).rotateXYZ(
                                0,
                                (float) Math.atan2(-forward.z, forward.x),
                                (float) forward.y
                        );
                        runtime.root.updateRotation(newRotation);
                    }
                    case LOOK -> {
                        var lookAngles = entity.getLookAngle();
                        var newRotation = new Quaternionf(rotation).rotateXYZ(
                                0,
                                (float) Math.atan2(-lookAngles.z, lookAngles.x),
                                (float) lookAngles.y
                        );
                        runtime.root.updateRotation(newRotation);
                    }
                    case XROT -> {
                        var newRotation = new Quaternionf(rotation).rotateXYZ(
                                0,
                                (float) Math.toRadians(-90 - entity.getVisualRotationYInDegrees()),
                                0
                        );
                        runtime.root.updateRotation(newRotation);
                    }
                }
            }
        }
    }
}
