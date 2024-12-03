package jp.artan.astralsorcery.neoforge.providers;

import jp.artan.astralsorcery.block.TileBlock;
import jp.artan.astralsorcery.block.VerticalSlabBlock;
import jp.artan.astralsorcery.init.ASBlocks;
import jp.artan.astralsorcery.sets.StoneDecoration;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class ModRecipeProvider extends RecipeProvider {
    protected ModRecipeProvider(HolderLookup.Provider arg, RecipeOutput arg2) {
        super(arg, arg2);
    }

    @Override
    protected void buildRecipes() {

        // 大理石
        shaped(RecipeCategory.BUILDING_BLOCKS, ASBlocks.MARBLE_ARCH.get(), 2)
                .define('#', ASBlocks.MARBLE.get())
                .pattern("##")
                .unlockedBy("has_item", has(ASBlocks.MARBLE.get()))
                .save(this.output);
        shaped(RecipeCategory.BUILDING_BLOCKS, ASBlocks.MARBLE_BRICKS.get(), 4)
                .define('#', ASBlocks.MARBLE.get())
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_item", has(ASBlocks.MARBLE.get()))
                .save(this.output);
        shaped(RecipeCategory.BUILDING_BLOCKS, ASBlocks.MARBLE_CHISELED.get(), 4)
                .define('#', ASBlocks.MARBLE.get())
                .pattern(" # ")
                .pattern("# #")
                .pattern(" # ")
                .unlockedBy("has_item", has(ASBlocks.MARBLE.get()))
                .save(this.output);
        shaped(RecipeCategory.BUILDING_BLOCKS, ASBlocks.MARBLE_ENGRAVED.get(), 5)
                .define('#', ASBlocks.MARBLE.get())
                .pattern(" # ")
                .pattern("###")
                .pattern(" # ")
                .unlockedBy("has_item", has(ASBlocks.MARBLE.get()))
                .save(this.output);
        shaped(RecipeCategory.BUILDING_BLOCKS, ASBlocks.MARBLE_PILLAR.get(), 2)
                .define('#', ASBlocks.MARBLE.get())
                .pattern("#")
                .pattern("#")
                .unlockedBy("has_item", has(ASBlocks.MARBLE.get()))
                .save(this.output);
        shaped(RecipeCategory.BUILDING_BLOCKS, ASBlocks.MARBLE_RUNED.get(), 3)
                .define('A', ASBlocks.MARBLE.get())
                .define('B', ASBlocks.MARBLE_CHISELED.get())
                .pattern("ABA")
                .unlockedBy("has_item", has(ASBlocks.MARBLE.get()))
                .save(this.output);

        // 大理石デコレーション
        stoneDecoration(ASBlocks.MARBLE_DECORATION, ASBlocks.MARBLE);
        stoneDecoration(ASBlocks.MARBLE_ARCH_DECORATION, ASBlocks.MARBLE_ARCH);
        stoneDecoration(ASBlocks.MARBLE_BRICKS_DECORATION, ASBlocks.MARBLE_BRICKS);
        stoneDecoration(ASBlocks.MARBLE_CHISELED_DECORATION, ASBlocks.MARBLE_CHISELED);
        stoneDecoration(ASBlocks.MARBLE_ENGRAVED_DECORATION, ASBlocks.MARBLE_ENGRAVED);
        stoneDecoration(ASBlocks.MARBLE_RUNED_DECORATION, ASBlocks.MARBLE_RUNED);
    }

    public void tile(Supplier<? extends TileBlock> resultItem, Supplier<SlabBlock> slabBlock, @Nullable String groupName) {
        String name = getItemName(resultItem.get());
        shaped(RecipeCategory.BUILDING_BLOCKS, resultItem.get(), 8)
                .define('#', slabBlock.get())
                .pattern("##")
                .unlockedBy("has_item", has(slabBlock.get()))
                .group(groupName)
                .save(this.output);
        shaped(RecipeCategory.BUILDING_BLOCKS, slabBlock.get())
                .define('#', resultItem.get())
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_item", has(resultItem.get()))
                .group(groupName)
                .save(this.output, name + "_reverse");
    }

    public void slab(Supplier<? extends SlabBlock> resultItem, Supplier<Block> material, @Nullable String groupName) {
        String name = getItemName(resultItem.get());
        shaped(RecipeCategory.BUILDING_BLOCKS, resultItem.get(), 6)
                .define('#', material.get())
                .pattern("###")
                .unlockedBy("has_item", has(material.get()))
                .group(groupName)
                .save(this.output);
        shaped(RecipeCategory.BUILDING_BLOCKS, material.get())
                .define('#', resultItem.get())
                .pattern("#")
                .pattern("#")
                .unlockedBy("has_item", has(resultItem.get()))
                .group(groupName == null ? null : groupName + "_reverse")
                .save(this.output, name + "_reverse");
    }

    public void verticalSlab(Supplier<? extends VerticalSlabBlock> resultItem, Supplier<Block> material, @Nullable String groupName) {
        String name = getItemName(resultItem.get());
        shaped(RecipeCategory.BUILDING_BLOCKS, resultItem.get(), 6)
                .define('#', material.get())
                .pattern("#")
                .pattern("#")
                .pattern("#")
                .unlockedBy("has_item", has(material.get()))
                .group(groupName)
                .save(this.output);
        shaped(RecipeCategory.BUILDING_BLOCKS, material.get())
                .define('#', resultItem.get())
                .pattern("##")
                .unlockedBy("has_item", has(resultItem.get()))
                .group(groupName == null ? null : groupName + "_reverse")
                .save(this.output, name + "_reverse");
    }

    public void stairs(Supplier<? extends StairBlock> resultItem, Supplier<Block> material, @Nullable String groupName) {
        shaped(RecipeCategory.BUILDING_BLOCKS, resultItem.get(), 4)
                .define('#', material.get())
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .unlockedBy("has_item", has(material.get()))
                .group(groupName)
                .save(this.output);
    }

    public void wall(Supplier<? extends WallBlock> resultItem, Supplier<Block> material, @Nullable String groupName) {
        shaped(RecipeCategory.BUILDING_BLOCKS, resultItem.get(), 6)
                .define('#', material.get())
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_item", has(material.get()))
                .group(groupName)
                .save(this.output);
    }
    protected void stoneDecoration(StoneDecoration decoration, Supplier<? extends Block> material) {
        stoneDecoration(decoration, material, null);
    }

    protected void stoneDecoration(StoneDecoration decoration, Supplier<? extends Block> material, @Nullable String groupName) {
        slab(decoration.slab, material::get, groupName == null ? null : groupName + "_slab");
        verticalSlab(decoration.verticalSlab, material::get, groupName == null ? null : groupName + "_vertical_slab");
        stairs(decoration.stairs, material::get, groupName == null ? null : groupName + "_stairs");
        tile(decoration.tile, decoration.slab, groupName == null ? null : groupName + "_tile");
        wall(decoration.wall, material::get, groupName == null ? null : groupName + "_wall");

        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, decoration.slab.get(), material.get(), 2);
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, decoration.verticalSlab.get(), material.get(), 2);
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, decoration.stairs.get(), material.get());
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, decoration.tile.get(), decoration.slab.get(), 8);
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, decoration.tile.get(), material.get(), 16);
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, decoration.wall.get(), material.get());
    }

    public static class Runner extends RecipeProvider.Runner {

        public Runner(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(arg, completableFuture);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider arg, RecipeOutput arg2) {
            return new ModRecipeProvider(arg, arg2);
        }

        @Override
        public String getName() {
            return "Recipes";
        }
    }
}
