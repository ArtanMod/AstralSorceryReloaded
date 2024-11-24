package jp.artan.astralsorcery.init;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.DeferredSupplier;
import dev.architectury.registry.registries.RegistrySupplier;
import jp.artan.astralsorcery.AstralSorceryReloaded;
import jp.artan.astralsorcery.block.TileBlock;
import jp.artan.astralsorcery.block.VerticalSlabBlock;
import jp.artan.astralsorcery.sets.StoneDecoration;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Supplier;

public class ASBlocks {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(AstralSorceryReloaded.MOD_ID, Registries.BLOCK);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(AstralSorceryReloaded.MOD_ID, Registries.ITEM);

    public static void register() {
        BLOCKS.register();
        ITEMS.register();
    }

    // public static final RegistrySupplier<Block> ROCK_COLLECTOR_CRYSTAL = register("collector_crystal", () -> new Block(Block.Properties.ofFullCopy(Blocks.DRIED_KELP_BLOCK)), ASCreativeTab.ASTRAL_SORCERY);

    // Luminous Crafting Table
    //public static final RegistrySupplier<Block> ALTAR_DISCOVERY = register("altar_discovery", () -> new Block(Block.Properties.ofFullCopy(Blocks.DRIED_KELP_BLOCK)), ASCreativeTab.ASTRAL_SORCERY);

    // 装飾ブロック
    public static final RegistrySupplier<Block> MARBLE = register("marble", id -> new Block(Block.Properties.ofFullCopy(Blocks.STONE).setId(id)), ASCreativeTab.ASTRAL_SORCERY);
    public static final StoneDecoration MARBLE_DECORATION = registerDecoration("marble", MARBLE, ASCreativeTab.ASTRAL_SORCERY);

    private static StoneDecoration registerDecoration(String name, Supplier<? extends Block> material, RegistrySupplier<CreativeModeTab> tab) {
        RegistrySupplier<SlabBlock> slabBlock = register(name + "_slab", id -> new SlabBlock(Block.Properties.ofFullCopy(Blocks.STONE_SLAB).setId(id)), tab);
        RegistrySupplier<VerticalSlabBlock> verticalSlabBlock = register(name + "_vertical_slab", id -> new VerticalSlabBlock(Block.Properties.of().strength(2.0F, 6.0F).sound(SoundType.STONE).requiresCorrectToolForDrops().setId(id)), tab);
        RegistrySupplier<StairBlock> stairBlock = register(name + "_stairs", id -> new StairBlock(material.get().defaultBlockState(), Block.Properties.ofFullCopy(Blocks.STONE_STAIRS).setId(id)), tab);
        RegistrySupplier<TileBlock> tileBlock = register(name + "_tile", id -> new TileBlock(Block.Properties.of().strength(2.0F, 6.0F).sound(SoundType.STONE).noOcclusion().requiresCorrectToolForDrops().setId(id)), tab);
        RegistrySupplier<WallBlock> wallBlock = register(name + "_wall", id -> new WallBlock(Block.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL).setId(id)), tab);
        return new StoneDecoration(
                slabBlock,
                verticalSlabBlock,
                stairBlock,
                tileBlock,
                wallBlock
        );
    }

    private static <T extends Block> RegistrySupplier<T> register(String name, Function<ResourceKey<Block>, T> block, DeferredSupplier<CreativeModeTab> tab) {
        RegistrySupplier<T> registeredBlock = BLOCKS.register(name, () -> block.apply(createKey(name)));
        ITEMS.register(name, () -> {
            BlockItem itemInstance = new BlockItem(registeredBlock.get(), new Item.Properties().setId(createItemKey(name)));
            CreativeTabRegistry.append(tab, itemInstance);
            return itemInstance;
        });
        return registeredBlock;
    }
    private static ResourceKey<Block> createKey(String name) {
        return ResourceKey.create(Registries.BLOCK, AstralSorceryReloaded.getResource(name));
    }
    private static ResourceKey<Item> createItemKey(String name) {
        return ResourceKey.create(Registries.ITEM, AstralSorceryReloaded.getResource(name));
    }
}
