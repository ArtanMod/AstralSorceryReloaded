package jp.artan.astralsorcery.block;


import jp.artan.astralsorcery.block.properties.ModBlockStateProperties;
import jp.artan.astralsorcery.block.properties.PillarType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class PillarBlock extends Block implements SimpleWaterloggedBlock {
    public static final EnumProperty<PillarType> TYPE = ModBlockStateProperties.PILLAR_TYPE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape TOP_SHAPE = Shapes.join(
            Block.box(2, 0, 2, 14, 12, 14),
            Block.box(0, 12, 0, 16, 16, 16),
            BooleanOp.OR
    );
    private static final VoxelShape MIDDLE_SHAPE = Block.box(2, 0, 2, 14, 16, 14);
    private static final VoxelShape BOTTOM_SHAPE = Shapes.join(
            Block.box(2, 4, 2, 14, 16, 14),
            Block.box(0, 0, 0, 16, 4, 16),
            BooleanOp.OR
    );

    public PillarBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(TYPE, PillarType.MIDDLE)
                .setValue(WATERLOGGED, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(TYPE, WATERLOGGED);
    }

    @Override
    protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return switch(blockState.getValue(TYPE)) {
            case TOP -> TOP_SHAPE;
            case MIDDLE -> MIDDLE_SHAPE;
            case BOTTOM -> BOTTOM_SHAPE;
        };
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        BlockPos blockpos = blockPlaceContext.getClickedPos();
        Level level = blockPlaceContext.getLevel();
        FluidState fluidstate = blockPlaceContext.getLevel().getFluidState(blockpos);
        return this.getThisState(level, blockpos).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    private BlockState getThisState(LevelReader level, BlockPos pos) {
        boolean hasUp = level.getBlockState(pos.above()).getBlock() instanceof PillarBlock;
        boolean hasDown = level.getBlockState(pos.below()).getBlock() instanceof PillarBlock;
        if (hasUp) {
            if (hasDown) {
                return this.defaultBlockState().setValue(TYPE, PillarType.MIDDLE);
            }
            return this.defaultBlockState().setValue(TYPE, PillarType.BOTTOM);
        } else if (hasDown) {
            return this.defaultBlockState().setValue(TYPE, PillarType.TOP);
        }
        return this.defaultBlockState().setValue(TYPE, PillarType.MIDDLE);
    }

    @Override
    public FluidState getFluidState(BlockState p_204507_1_) {
        return p_204507_1_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_204507_1_);
    }

    @Override
    public BlockState updateShape(BlockState blockState, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos blockPos, Direction direction, BlockPos blockPos2, BlockState blockState2, RandomSource randomSource) {
        if (blockState.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
        }
        FluidState fluidstate = levelReader.getFluidState(blockPos);
        return this.getThisState(levelReader, blockPos).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    @Override
    protected boolean isPathfindable(BlockState blockState, PathComputationType pathComputationType) {
        if(Objects.requireNonNull(pathComputationType) == PathComputationType.WATER) {
            return blockState.getFluidState().is(FluidTags.WATER);
        }
        return false;
    }
}
