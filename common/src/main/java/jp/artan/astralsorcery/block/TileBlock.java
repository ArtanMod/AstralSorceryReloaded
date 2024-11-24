package jp.artan.astralsorcery.block;

import jp.artan.astralsorcery.utils.BlockStateForPlacementUtils;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class TileBlock extends Block implements SimpleWaterloggedBlock {
    public static final int MAX_HEIGHT = 16;
    public static final IntegerProperty LAYERS = IntegerProperty.create("layers", 1, MAX_HEIGHT);
    public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape[] SHAPE_BY_LAYER_UP = new VoxelShape[]{
            Shapes.empty(),
            Block.box(0.0D, 15.0D, 0.0D, 16.0D, 16.0D, 16.0D),
            Block.box(0.0D, 14.0D, 0.0D, 16.0D, 16.0D, 16.0D),
            Block.box(0.0D, 13.0D, 0.0D, 16.0D, 16.0D, 16.0D),
            Block.box(0.0D, 12.0D, 0.0D, 16.0D, 16.0D, 16.0D),
            Block.box(0.0D, 11.0D, 0.0D, 16.0D, 16.0D, 16.0D),
            Block.box(0.0D, 10.0D, 0.0D, 16.0D, 16.0D, 16.0D),
            Block.box(0.0D, 9.0D,  0.0D, 16.0D, 16.0D, 16.0D),
            Block.box(0.0D, 8.0D,  0.0D, 16.0D, 16.0D, 16.0D),
            Block.box(0.0D, 7.0D,  0.0D, 16.0D, 16.0D, 16.0D),
            Block.box(0.0D, 6.0D,  0.0D, 16.0D, 16.0D, 16.0D),
            Block.box(0.0D, 5.0D,  0.0D, 16.0D, 16.0D, 16.0D),
            Block.box(0.0D, 4.0D,  0.0D, 16.0D, 16.0D, 16.0D),
            Block.box(0.0D, 3.0D,  0.0D, 16.0D, 16.0D, 16.0D),
            Block.box(0.0D, 2.0D,  0.0D, 16.0D, 16.0D, 16.0D),
            Block.box(0.0D, 1.0D,  0.0D, 16.0D, 16.0D, 16.0D),
            Block.box(0.0D, 0.0D,  0.0D, 16.0D, 16.0D, 16.0D)
    };
    protected static final VoxelShape[] SHAPE_BY_LAYER_DOWN = new VoxelShape[]{
            Shapes.empty(),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D,  16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D,  16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 3.0D,  16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D,  16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 5.0D,  16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D,  16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 7.0D,  16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D,  16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 9.0D,  16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 11.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 13.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)
    };
    protected static final VoxelShape[] SHAPE_BY_LAYER_NORTH = new VoxelShape[]{
            Shapes.empty(),
            Block.box(0.0D, 0.0D, 15.0D, 16.0D, 16.0D,  16.0D),
            Block.box(0.0D, 0.0D, 14.0D, 16.0D, 16.0D,  16.0D),
            Block.box(0.0D, 0.0D, 13.0D, 16.0D, 16.0D,  16.0D),
            Block.box(0.0D, 0.0D, 12.0D, 16.0D, 16.0D,  16.0D),
            Block.box(0.0D, 0.0D, 11.0D, 16.0D, 16.0D,  16.0D),
            Block.box(0.0D, 0.0D, 10.0D, 16.0D, 16.0D,  16.0D),
            Block.box(0.0D, 0.0D,  9.0D,  16.0D, 16.0D,  16.0D),
            Block.box(0.0D, 0.0D,  8.0D,  16.0D, 16.0D,  16.0D),
            Block.box(0.0D, 0.0D,  7.0D,  16.0D, 16.0D,  16.0D),
            Block.box(0.0D, 0.0D,  6.0D,  16.0D, 16.0D,  16.0D),
            Block.box(0.0D, 0.0D,  5.0D,  16.0D, 16.0D,  16.0D),
            Block.box(0.0D, 0.0D,  4.0D,  16.0D, 16.0D,  16.0D),
            Block.box(0.0D, 0.0D,  3.0D,  16.0D, 16.0D,  16.0D),
            Block.box(0.0D, 0.0D,  2.0D,  16.0D, 16.0D,  16.0D),
            Block.box(0.0D, 0.0D,  1.0D,  16.0D, 16.0D,  16.0D),
            Block.box(0.0D, 0.0D,  0.0D,  16.0D, 16.0D,  16.0D)
    };
    protected static final VoxelShape[] SHAPE_BY_LAYER_SOUTH = new VoxelShape[]{
            Shapes.empty(),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D,  1.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D,  2.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D,  3.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D,  4.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D,  5.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D,  6.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D,  7.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D,  8.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D,  9.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D,  10.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D,  11.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D,  12.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D,  13.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D,  14.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D,  15.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D,  16.0D)
    };
    protected static final VoxelShape[] SHAPE_BY_LAYER_EAST = new VoxelShape[]{
            Shapes.empty(),
            Block.box(0.0D, 0.0D, 0.0D,  1.0D, 16.0D,  16.0D),
            Block.box(0.0D, 0.0D, 0.0D,  2.0D, 16.0D,  16.0D),
            Block.box(0.0D, 0.0D, 0.0D,  3.0D, 16.0D,  16.0D),
            Block.box(0.0D, 0.0D, 0.0D,  4.0D, 16.0D,  16.0D),
            Block.box(0.0D, 0.0D, 0.0D,  5.0D, 16.0D,  16.0D),
            Block.box(0.0D, 0.0D, 0.0D,  6.0D, 16.0D,  16.0D),
            Block.box(0.0D, 0.0D, 0.0D,  7.0D, 16.0D,  16.0D),
            Block.box(0.0D, 0.0D, 0.0D,  8.0D, 16.0D,  16.0D),
            Block.box(0.0D, 0.0D, 0.0D,  9.0D, 16.0D,  16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 10.0D, 16.0D,  16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 11.0D, 16.0D,  16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 12.0D, 16.0D,  16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 13.0D, 16.0D,  16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 14.0D, 16.0D,  16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 15.0D, 16.0D,  16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D,  16.0D)
    };
    protected static final VoxelShape[] SHAPE_BY_LAYER_WEST = new VoxelShape[]{
            Shapes.empty(),
            Block.box(15.0D, 0.0D, 0.0D, 16.0D, 16.0D,  16.0D),
            Block.box(14.0D, 0.0D, 0.0D, 16.0D, 16.0D,  16.0D),
            Block.box(13.0D, 0.0D, 0.0D, 16.0D, 16.0D,  16.0D),
            Block.box(12.0D, 0.0D, 0.0D, 16.0D, 16.0D,  16.0D),
            Block.box(11.0D, 0.0D, 0.0D, 16.0D, 16.0D,  16.0D),
            Block.box(10.0D, 0.0D, 0.0D, 16.0D, 16.0D,  16.0D),
            Block.box( 9.0D, 0.0D, 0.0D, 16.0D, 16.0D,  16.0D),
            Block.box( 8.0D, 0.0D, 0.0D, 16.0D, 16.0D,  16.0D),
            Block.box( 7.0D, 0.0D, 0.0D, 16.0D, 16.0D,  16.0D),
            Block.box( 6.0D, 0.0D, 0.0D, 16.0D, 16.0D,  16.0D),
            Block.box( 5.0D, 0.0D, 0.0D, 16.0D, 16.0D,  16.0D),
            Block.box( 4.0D, 0.0D, 0.0D, 16.0D, 16.0D,  16.0D),
            Block.box( 3.0D, 0.0D, 0.0D, 16.0D, 16.0D,  16.0D),
            Block.box( 2.0D, 0.0D, 0.0D, 16.0D, 16.0D,  16.0D),
            Block.box( 1.0D, 0.0D, 0.0D, 16.0D, 16.0D,  16.0D),
            Block.box( 0.0D, 0.0D, 0.0D, 16.0D, 16.0D,  16.0D)
    };

    public TileBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(LAYERS, Integer.valueOf(1))
                .setValue(FACING, Direction.UP)
                .setValue(WATERLOGGED, false)
        );
    }

    private static VoxelShape getVoxelShape(BlockState pState) {
        return switch(pState.getValue(FACING)) {
            case UP -> SHAPE_BY_LAYER_UP[pState.getValue(LAYERS)];
            case DOWN -> SHAPE_BY_LAYER_DOWN[pState.getValue(LAYERS)];
            case NORTH -> SHAPE_BY_LAYER_NORTH[pState.getValue(LAYERS)];
            case SOUTH -> SHAPE_BY_LAYER_SOUTH[pState.getValue(LAYERS)];
            case EAST -> SHAPE_BY_LAYER_EAST[pState.getValue(LAYERS)];
            case WEST -> SHAPE_BY_LAYER_WEST[pState.getValue(LAYERS)];
        };
    }

    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return getVoxelShape(pState);
    }

    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return getVoxelShape(pState);
    }

    public VoxelShape getBlockSupportShape(BlockState pState, BlockGetter pReader, BlockPos pPos) {
        return getVoxelShape(pState);
    }

    public VoxelShape getVisualShape(BlockState pState, BlockGetter pReader, BlockPos pPos, CollisionContext pContext) {
        return getVoxelShape(pState);
    }

    public boolean useShapeForLightOcclusion(BlockState pState) {
        return true;
    }

    @Override
    protected BlockState updateShape(BlockState blockState, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos blockPos, Direction direction, BlockPos blockPos2, BlockState blockState2, RandomSource randomSource) {
        if (blockState.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
        }
        return !blockState.canSurvive(levelReader, blockPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(blockState, levelReader, scheduledTickAccess, blockPos, direction, blockPos2, blockState2, randomSource);
    }

    public boolean canBeReplaced(BlockState pState, BlockPlaceContext pUseContext) {
        int i = pState.getValue(LAYERS);
        if (pUseContext.getItemInHand().is(this.asItem()) && i < MAX_HEIGHT) {
            if (pUseContext.replacingClickedOnBlock()) {
                Direction direction = pState.getValue(FACING);

                // プレイヤーがShiftを押しているとき、クリックしている面とは反対側の面のタイルを増やす
                if(pUseContext.getPlayer().isShiftKeyDown()) {
                    return pUseContext.getClickedFace().getOpposite() == direction;
                }

                if(direction.getAxis().isVertical()) {
                    return pUseContext.getClickedFace().getOpposite() == direction;
                }
                // クリックしている面のタイルを増やす
                return pUseContext.getClickedFace() == direction;
            } else {
                return true;
            }
        }
        return false;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState blockstate = pContext.getLevel().getBlockState(pContext.getClickedPos());
        if (blockstate.is(this)) {

            // ブロックがタイルブロックの場合は、タイルの高さを1増やす
            int i = blockstate.getValue(LAYERS);
            Boolean waterLogged = blockstate.getValue(WATERLOGGED);
            return blockstate
                    .setValue(LAYERS, Math.min(MAX_HEIGHT, i + 1))
                    .setValue(WATERLOGGED, waterLogged && i + 1 != MAX_HEIGHT);
        } else {
            BlockStateForPlacementUtils.Point clickPoint = BlockStateForPlacementUtils.getBlockClickPoint(pContext);
            FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
            BlockState result = super.getStateForPlacement(pContext).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
            if(pContext.getPlayer() != null && pContext.getPlayer().isShiftKeyDown()) {
                if(pContext.getClickedFace() == Direction.UP || pContext.getClickedFace() == Direction.DOWN) {
                    if(Screen.hasControlDown()) {

                        // ShiftとCtrlを押しており、垂直方向の面を選択しているとき、プレイヤーが向いている方向とは逆の方向に設置
                        return result.setValue(FACING, pContext.getHorizontalDirection().getOpposite());
                    }

                    // Shiftを押しており、垂直方向の面を選択しているとき、プレイヤーが向いている方向に設置
                    return result.setValue(FACING, pContext.getHorizontalDirection());
                }

                // Shiftを押しており、水平方向の面を選択しているとき、プレイヤーがみている面が垂直方向で半分以下の場合は下向き設置。垂直方向で半分以上の場合は上向き設置。
                return result.setValue(FACING, clickPoint.y() > 0.5D ? Direction.UP : Direction.DOWN);
            }

            if(pContext.getClickedFace() == Direction.UP || pContext.getClickedFace() == Direction.DOWN) {

                // プレイヤーが選択している面に接するように配置。上面下面はクリック面と明太方向に配置する。
                return result.setValue(FACING, pContext.getClickedFace().getOpposite());
            }

            // プレイヤーが選択している面に接するように配置
            return result.setValue(FACING, pContext.getClickedFace());
        }
    }

    @Override
    public boolean placeLiquid(LevelAccessor p_204509_1_, BlockPos p_204509_2_, BlockState p_204509_3_, FluidState p_204509_4_) {
        return p_204509_3_.getValue(LAYERS) < MAX_HEIGHT && SimpleWaterloggedBlock.super.placeLiquid(p_204509_1_, p_204509_2_, p_204509_3_, p_204509_4_);
    }

    @Override
    public boolean canPlaceLiquid(@Nullable Player player, BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, Fluid fluid) {
        return blockState.getValue(LAYERS) < MAX_HEIGHT && SimpleWaterloggedBlock.super.canPlaceLiquid(player, blockGetter, blockPos, blockState, fluid);
    }

    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(LAYERS, FACING, WATERLOGGED);
    }
}
