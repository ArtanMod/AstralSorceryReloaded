package jp.artan.astralsorcery.neoforge.providers;

import jp.artan.astralsorcery.init.ASBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockModelProvider extends BlockStateProvider {

    public ModBlockModelProvider(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        // 装飾
        ResourceLocation marble = this.getBlockId(ASBlocks.MARBLE.get());
        BlockModelBuilder modelBuilder = this.models().cubeAll(marble.getPath(), this.modLoc("block/marble_raw"));
        this.simpleBlock(ASBlocks.MARBLE.get(), modelBuilder);
        this.simpleBlockItem(ASBlocks.MARBLE.get(), modelBuilder);
    }

    private ResourceLocation getBlockId(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }
}
