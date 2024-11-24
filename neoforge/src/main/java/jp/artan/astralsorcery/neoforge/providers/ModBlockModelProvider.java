package jp.artan.astralsorcery.neoforge.providers;

import jp.artan.astralsorcery.init.ASBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

public class ModBlockModelProvider extends BlockStateProvider {

    public ModBlockModelProvider(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        // 装飾
        BlockModelBuilder modelBuilder = this.models().cubeAll(this.getBlockId(ASBlocks.MARBLE), this.modLoc("block/marble_raw"));
        this.simpleBlock(ASBlocks.MARBLE.get(), modelBuilder);
        this.simpleBlockItem(ASBlocks.MARBLE.get(), modelBuilder);
    }

    private String getBlockId(Supplier<? extends Block> block) {
        return BuiltInRegistries.BLOCK.getKey(block.get()).getPath();
    }
}
