package net.kykokos.amadditions.block.custom;

import net.kykokos.amadditions.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;

import javax.annotation.Nullable;

public class CustomDripstoneBlock extends PointedDripstoneBlock {
    public CustomDripstoneBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(TIP_DIRECTION, Direction.DOWN)
                .setValue(THICKNESS, DripstoneThickness.TIP)
                .setValue(WATERLOGGED, false));
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(TIP_DIRECTION) == Direction.DOWN && state.getValue(THICKNESS) == DripstoneThickness.TIP) {

            if (isAttachedToSulfurBlock(level, pos)) {
                if (random.nextFloat() < 0.05f) {
                    BlockPos targetPos = pos.below();
                    if (level.isEmptyBlock(targetPos)) {
                        level.setBlockAndUpdate(targetPos, this.defaultBlockState()
                                .setValue(TIP_DIRECTION, Direction.DOWN)
                                .setValue(THICKNESS, DripstoneThickness.TIP));
                    }
                }
            }
        }
    }

    private boolean isAttachedToSulfurBlock(ServerLevel level, BlockPos pos) {
        BlockPos.MutableBlockPos currentPos = pos.mutable().move(Direction.UP);

        while (level.getBlockState(currentPos).is(this)) {
            currentPos.move(Direction.UP);
        }

        return level.getBlockState(currentPos).is(ModBlocks.SULFUR_BLOCK.get());
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        LevelReader level = context.getLevel();
        BlockPos pos = context.getClickedPos();

        return this.defaultBlockState()
                .setValue(TIP_DIRECTION, Direction.DOWN)
                .setValue(THICKNESS, calculateThicknessFor(level, pos));
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        BlockPos blockAbove = pos.above();
        BlockState stateAbove = level.getBlockState(blockAbove);

        if (!stateAbove.is(this) && !stateAbove.isFaceSturdy(level, blockAbove, Direction.DOWN)) {
            return Blocks.AIR.defaultBlockState();
        }

        return state.setValue(THICKNESS, calculateThicknessFor(level, pos));
    }

    private DripstoneThickness calculateThicknessFor(LevelReader level, BlockPos pos) {
        boolean hasSpikeAbove = level.getBlockState(pos.above()).is(this);
        boolean hasSpikeBelow = level.getBlockState(pos.below()).is(this);

        if (hasSpikeAbove && hasSpikeBelow) {
            return DripstoneThickness.MIDDLE;
        }
        if (hasSpikeAbove) {
            return DripstoneThickness.TIP;
        }
        if (hasSpikeBelow) {
            BlockState stateBelow = level.getBlockState(pos.below());
            if (stateBelow.is(this) && stateBelow.getValue(THICKNESS) == DripstoneThickness.TIP) {
                return DripstoneThickness.FRUSTUM;
            }
            return DripstoneThickness.BASE;
        }

        return DripstoneThickness.TIP;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos blockAbove = pos.above();
        BlockState stateAbove = level.getBlockState(blockAbove);
        return stateAbove.is(this) || stateAbove.isFaceSturdy(level, blockAbove, Direction.DOWN);
    }
}

