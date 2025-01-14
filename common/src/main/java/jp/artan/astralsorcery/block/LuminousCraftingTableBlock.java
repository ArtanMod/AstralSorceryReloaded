package jp.artan.astralsorcery.block;

import com.mojang.serialization.MapCodec;
import jp.artan.astralsorcery.init.ASBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class LuminousCraftingTableBlock extends BaseEntityBlock {
    public static final MapCodec<LuminousCraftingTableBlock> CODEC = simpleCodec(LuminousCraftingTableBlock::new);
    private static final VoxelShape SHAPE = Shapes.or(
            Block.box(4, 2, 4, 12, 9.5f, 12),    // Pillar
            Block.box(2, 0, 2, 14, 2, 14),       // Base
            Block.box(0, 9.5f, 0, 16, 15.5f, 16) // Top
    );

    public LuminousCraftingTableBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean isPathfindable(BlockState blockState, PathComputationType pathComputationType) {
        return false;
    }

    @Override
    protected VoxelShape getEntityInsideCollisionShape(BlockState blockState, Level level, BlockPos blockPos) {
        return SHAPE;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    @Override
    protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    @Override
    protected MapCodec<LuminousCraftingTableBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return ASBlockEntity.LUMINOUS_CRAFTING_TABLE_ENTITY.get().create(blockPos, blockState);
    }
}
