package jp.artan.astralsorcery.block.entity;

import jp.artan.astralsorcery.init.ASBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class LuminousCraftingTableEntity extends BlockEntity {
    public LuminousCraftingTableEntity(BlockPos blockPos, BlockState blockState) {
        super(ASBlockEntity.LUMINOUS_CRAFTING_TABLE_ENTITY.get(), blockPos, blockState);
    }
}
