package jp.artan.astralsorcery.neoforge.providers;

import jp.artan.astralsorcery.init.ASBlocks;
import jp.artan.astralsorcery.init.ASTags;
import jp.artan.astralsorcery.sets.StoneDecoration;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                ASBlocks.MARBLE.get(),
                ASBlocks.MARBLE_ARCH.get(),
                ASBlocks.MARBLE_BRICKS.get(),
                ASBlocks.MARBLE_CHISELED.get(),
                ASBlocks.MARBLE_ENGRAVED.get(),
                ASBlocks.MARBLE_RUNED.get(),
                ASBlocks.MARBLE_PILLAR.get()
        );
        this.addStoneDecoration(ASBlocks.MARBLE_DECORATION);
        this.addStoneDecoration(ASBlocks.MARBLE_ARCH_DECORATION);
        this.addStoneDecoration(ASBlocks.MARBLE_BRICKS_DECORATION);
        this.addStoneDecoration(ASBlocks.MARBLE_CHISELED_DECORATION);
        this.addStoneDecoration(ASBlocks.MARBLE_ENGRAVED_DECORATION);
        this.addStoneDecoration(ASBlocks.MARBLE_RUNED_DECORATION);
    }

    protected void addStoneDecoration(StoneDecoration decoration) {

        // 通常タグ
        this.tag(BlockTags.SLABS).add(decoration.slab.get());
        this.tag(ASTags.BlockTag.VERTICAL_SLAB).add(decoration.verticalSlab.get());
        this.tag(BlockTags.STAIRS).add(decoration.stairs.get());
        this.tag(ASTags.BlockTag.TILE).add(decoration.tile.get());
        this.tag(BlockTags.WALLS).add(decoration.wall.get());

        // 採掘タグ
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(decoration.slab.get());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(decoration.verticalSlab.get());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(decoration.stairs.get());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(decoration.tile.get());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(decoration.wall.get());
    }
}
