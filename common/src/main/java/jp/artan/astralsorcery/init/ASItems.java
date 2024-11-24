package jp.artan.astralsorcery.init;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.DeferredSupplier;
import dev.architectury.registry.registries.RegistrySupplier;
import jp.artan.astralsorcery.AstralSorceryReloaded;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Supplier;

public class ASItems {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(AstralSorceryReloaded.MOD_ID, Registries.ITEM);

    // 杖
    public static final RegistrySupplier<Item> RESONATING_WAND = register("resonating_wand", ASCreativeTab.ASTRAL_SORCERY);

    public static final RegistrySupplier<Item> AQUAMARINE = register("aquamarine", ASCreativeTab.ASTRAL_SORCERY);

    public static void register() {
        ITEMS.register();
    }

    private static RegistrySupplier<Item> register(String name, DeferredSupplier<CreativeModeTab> tab) {
        return register(name, Item::new, tab);
    }
    private static <T extends Item> RegistrySupplier<T> register(String name, Function<Item.Properties, T> item, DeferredSupplier<CreativeModeTab> tab) {
        return ITEMS.register(name, () -> {
            T itemInstance = item.apply(new Item.Properties().setId(createKey(name)));
            CreativeTabRegistry.append(tab, itemInstance);
            return itemInstance;
        });
    }
    private static ResourceKey<Item> createKey(String name) {
        return ResourceKey.create(Registries.ITEM, AstralSorceryReloaded.getResource(name));
    }
}
