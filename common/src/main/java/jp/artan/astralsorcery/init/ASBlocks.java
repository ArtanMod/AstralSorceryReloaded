package jp.artan.astralsorcery.init;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.DeferredSupplier;
import dev.architectury.registry.registries.RegistrySupplier;
import jp.artan.astralsorcery.AstralSorceryReloaded;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class ASBlocks {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(AstralSorceryReloaded.MOD_ID, Registries.BLOCK);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(AstralSorceryReloaded.MOD_ID, Registries.ITEM);

    public static void register() {
        BLOCKS.register();
        ITEMS.register();
    }

    //public static final RegistrySupplier<Block> ROCK_COLLECTOR_CRYSTAL = register("collector_crystal", () -> new Block(Block.Properties.ofFullCopy(Blocks.DRIED_KELP_BLOCK)), ASCreativeTab.ASTRAL_SORCERY);

    // Luminous Crafting Table
    //public static final RegistrySupplier<Block> ALTAR_DISCOVERY = register("altar_discovery", () -> new Block(Block.Properties.ofFullCopy(Blocks.DRIED_KELP_BLOCK)), ASCreativeTab.ASTRAL_SORCERY);

    private static <T extends Block> RegistrySupplier<T> register(String name, Supplier<T> block, @Nullable DeferredSupplier<CreativeModeTab> tab) {
        RegistrySupplier<T> registeredBlock = BLOCKS.register(name, block);
        ITEMS.register(name, () -> {
            BlockItem itemInstance = new BlockItem(registeredBlock.get(), new Item.Properties());
            CreativeTabRegistry.append(tab, itemInstance);
            return itemInstance;
        });
        return registeredBlock;
    }
}
