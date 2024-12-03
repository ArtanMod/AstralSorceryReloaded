package jp.artan.astralsorcery.block;

import jp.artan.astralsorcery.block.properties.ModBlockStateProperties;
import jp.artan.astralsorcery.block.properties.VerticalSlabType;
import jp.artan.astralsorcery.utils.BlockStateForPlacementUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class VerticalSlabBlock extends Block implements SimpleWaterloggedBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty HORIZONTAL_CLICKED = BooleanProperty.create("horizontal_clicked");
    public static final EnumProperty<VerticalSlabType> TYPE = ModBlockStateProperties.VERTICAL_SLAB_TYPE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape Vertical_NORTH = Block.box(0.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape Vertical_EAST = Block.box(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D);
    protected static final VoxelShape Vertical_SOUTH = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D);
    protected static final VoxelShape Vertical_WEST = Block.box(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape[] SHAPES = new VoxelShape[]{
            Vertical_NORTH, Vertical_EAST, Vertical_SOUTH, Vertical_WEST
    };

    public VerticalSlabBlock(Properties p_i48331_1_) {
        super(p_i48331_1_);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(FACING, Direction.NORTH)
                .setValue(TYPE, VerticalSlabType.HALF)
                .setValue(WATERLOGGED, false)
                .setValue(HORIZONTAL_CLICKED, false)
        );
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState p_220074_1_) {
        return p_220074_1_.getValue(TYPE) != VerticalSlabType.DOUBLE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(TYPE, WATERLOGGED, FACING, HORIZONTAL_CLICKED);
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, BlockGetter p_220053_2_, BlockPos p_220053_3_, CollisionContext p_220053_4_) {
        VerticalSlabType slabtype = p_220053_1_.getValue(TYPE);
        switch (slabtype) {
            case DOUBLE:
                return Shapes.block();
            default:
                return SHAPES[this.getShapeIndex(p_220053_1_)];
        }
    }

    private int getShapeIndex(BlockState p_196511_1_) {
        return p_196511_1_.getValue(FACING).get2DDataValue();
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState result;
        BlockPos blockpos = pContext.getClickedPos();
        BlockState blockstate = pContext.getLevel().getBlockState(blockpos);
        if (blockstate.is(this)) {
            return blockstate
                    .setValue(TYPE, VerticalSlabType.DOUBLE)
                    .setValue(WATERLOGGED, false)
                    .setValue(FACING, pContext.getHorizontalDirection().getOpposite());
        }
        BlockStateForPlacementUtils.Point point = BlockStateForPlacementUtils.getBlockClickPoint(pContext);
        FluidState fluidstate = pContext.getLevel().getFluidState(blockpos);
        result = this.defaultBlockState()
                .setValue(TYPE, VerticalSlabType.HALF)
                .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
        if(pContext.getPlayer().isShiftKeyDown()) {
            System.out.println(point.x() + " : " + point.y());
            return result.setValue(HORIZONTAL_CLICKED, true)
                    .setValue(FACING, point.x() > 0.5D ? pContext.getHorizontalDirection().getCounterClockWise() : pContext.getHorizontalDirection().getClockWise());
        }
        return result.setValue(FACING, point.y() > 0.5D ? pContext.getHorizontalDirection() : pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public boolean canBeReplaced(BlockState pState, BlockPlaceContext p_196253_2_) {
        ItemStack itemstack = p_196253_2_.getItemInHand();
        VerticalSlabType slabtype = pState.getValue(TYPE);
        if (slabtype != VerticalSlabType.DOUBLE && itemstack.getItem() == this.asItem()) {
            if (p_196253_2_.replacingClickedOnBlock()) {
                Direction direction = pState.getValue(FACING);
                return direction.getOpposite() == p_196253_2_.getClickedFace();
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public FluidState getFluidState(BlockState p_204507_1_) {
        return p_204507_1_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_204507_1_);
    }

    @Override
    public boolean placeLiquid(LevelAccessor p_204509_1_, BlockPos p_204509_2_, BlockState p_204509_3_, FluidState p_204509_4_) {
        return p_204509_3_.getValue(TYPE) != VerticalSlabType.DOUBLE ? SimpleWaterloggedBlock.super.placeLiquid(p_204509_1_, p_204509_2_, p_204509_3_, p_204509_4_) : false;
    }

    @Override
    public boolean canPlaceLiquid(@Nullable Player player, BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, Fluid fluid) {
        return blockState.getValue(TYPE) != VerticalSlabType.DOUBLE ? SimpleWaterloggedBlock.super.canPlaceLiquid(player, blockGetter, blockPos, blockState, fluid) : false;
    }

    @Override
    public BlockState updateShape(BlockState blockState, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos blockPos, Direction direction, BlockPos blockPos2, BlockState blockState2, RandomSource randomSource) {
        if (blockState.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
        }
        return super.updateShape(blockState, levelReader, scheduledTickAccess, blockPos, direction, blockPos2, blockState2, randomSource);
    }

    @Override
    protected boolean isPathfindable(BlockState blockState, PathComputationType pathComputationType) {
        if(Objects.requireNonNull(pathComputationType) == PathComputationType.WATER) {
            return blockState.getFluidState().is(FluidTags.WATER);
        }
        return false;
    }
}

