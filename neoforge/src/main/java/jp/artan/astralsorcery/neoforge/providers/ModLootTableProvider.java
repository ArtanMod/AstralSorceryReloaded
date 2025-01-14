package jp.artan.astralsorcery.neoforge.providers;

import com.google.common.collect.ImmutableList;
import jp.artan.astralsorcery.block.TileBlock;
import jp.artan.astralsorcery.block.VerticalSlabBlock;
import jp.artan.astralsorcery.block.properties.VerticalSlabType;
import jp.artan.astralsorcery.init.ASBlocks;
import jp.artan.astralsorcery.sets.StoneDecoration;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.util.ProblemReporter;
import net.minecraft.util.context.ContextKeySet;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import static jp.artan.astralsorcery.AstralSorceryReloaded.MOD_ID;

public class ModLootTableProvider extends LootTableProvider {
    public ModLootTableProvider(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(arg, Set.of(), blockLootTable(ModBlockLoot::new), completableFuture);
    }

    public static List<LootTableProvider.SubProviderEntry> blockLootTable(Function<HolderLookup.Provider, LootTableSubProvider> provider) {
        ImmutableList.Builder<LootTableProvider.SubProviderEntry> builder = ImmutableList.builder();
        ContextKeySet lootContextParamSet = LootContextParamSets.BLOCK;
        builder.add(new LootTableProvider.SubProviderEntry(provider, lootContextParamSet));
        return builder.build();
    }

    @Override
    protected void validate(WritableRegistry<LootTable> writableregistry, ValidationContext validationcontext, ProblemReporter.Collector problemreporter$collector) {
    }

    public static class ModBlockLoot extends VanillaBlockLoot {

        public ModBlockLoot(HolderLookup.Provider arg) {
            super(arg);
        }

        @Override
        protected void generate() {
            this.dropSelf(ASBlocks.MARBLE.get());
            this.dropSelf(ASBlocks.MARBLE_ARCH.get());
            this.dropSelf(ASBlocks.MARBLE_BRICKS.get());
            this.dropSelf(ASBlocks.MARBLE_CHISELED.get());
            this.dropSelf(ASBlocks.MARBLE_ENGRAVED.get());
            this.dropSelf(ASBlocks.MARBLE_RUNED.get());
            this.dropSelf(ASBlocks.MARBLE_PILLAR.get());
            this.dropStoneDecoration(ASBlocks.MARBLE_DECORATION);
            this.dropStoneDecoration(ASBlocks.MARBLE_ARCH_DECORATION);
            this.dropStoneDecoration(ASBlocks.MARBLE_BRICKS_DECORATION);
            this.dropStoneDecoration(ASBlocks.MARBLE_CHISELED_DECORATION);
            this.dropStoneDecoration(ASBlocks.MARBLE_ENGRAVED_DECORATION);
            this.dropStoneDecoration(ASBlocks.MARBLE_RUNED_DECORATION);

            this.dropSelf(ASBlocks.LUMINOUS_CRAFTING_TABLE.get());
        }

        protected Iterable<Block> getKnownBlocks() {
            return BuiltInRegistries.BLOCK.stream().filter((block) -> MOD_ID.equals(BuiltInRegistries.BLOCK.getKey(block).getNamespace())).toList();
        }

        protected void dropSlab(SlabBlock block) {
            this.add(block, createSlabItemTable(block));
        }

        protected void dropVerticalSlab(VerticalSlabBlock block) {
            this.add(block, createVerticalSlabItemTable(block));
        }

        protected void dropTile(TileBlock block) {
            this.add(block, createTileEntityLootTable(block));
        }

        protected void dropStoneDecoration(StoneDecoration decoration) {
            this.dropSlab(decoration.slab.get());
            this.dropVerticalSlab(decoration.verticalSlab.get());
            this.dropSelf(decoration.stairs.get());
            this.dropTile(decoration.tile.get());
            this.dropSelf(decoration.wall.get());
        }

        protected LootTable.Builder createVerticalSlabItemTable(Block arg) {
            return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(applyExplosionDecay(arg, LootItem.lootTableItem(arg).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(arg).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(VerticalSlabBlock.TYPE, VerticalSlabType.DOUBLE)))))));
        }

        protected LootTable.Builder createTileEntityLootTable(Block block) {
            LootPoolSingletonContainer.Builder<?> lootTable = LootItem.lootTableItem(block);
            for(int i = 2; i <= TileBlock.MAX_HEIGHT; i++) {
                lootTable.apply(SetItemCountFunction.setCount(ConstantValue.exactly(i)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TileBlock.LAYERS, i))));
            }
            return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(applyExplosionDecay(block,lootTable)));
        }
    }
}
