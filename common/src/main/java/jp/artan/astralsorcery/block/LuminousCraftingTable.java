package jp.artan.astralsorcery.block;

import com.mojang.serialization.MapCodec;
import jp.artan.astralsorcery.init.ASBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class LuminousCraftingTable extends BaseEntityBlock {
    public static final MapCodec<LuminousCraftingTable> CODEC = simpleCodec(LuminousCraftingTable::new);

    protected LuminousCraftingTable(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<LuminousCraftingTable> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return ASBlockEntity.LUMINOUS_CRAFTING_TABLE_ENTITY.get().create(blockPos, blockState);
    }
}
