package jp.artan.astralsorcery.init;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import jp.artan.astralsorcery.AstralSorceryReloaded;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.function.Supplier;

public class ASCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(AstralSorceryReloaded.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> ASTRAL_SORCERY = createTab("astral_sorcery", () -> new ItemStack(Items.IRON_PICKAXE));
    public static final RegistrySupplier<CreativeModeTab> ATTUNED_PAPERS = createTab("as_attuned_papers", () -> new ItemStack(Items.PAPER));
    public static final RegistrySupplier<CreativeModeTab> ATTUNED_CRYSTALS = createTab("s_attuned_crystals", () -> new ItemStack(Items.END_CRYSTAL));

    public static void register() {
        CREATIVE_MODE_TABS.register();
    }

    private static RegistrySupplier<CreativeModeTab> createTab(String name, Supplier<ItemStack> icon) {
        return CREATIVE_MODE_TABS.register(name, () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0).icon(icon).title(Component.translatable("itemGroup." + AstralSorceryReloaded.MOD_ID + "." + name)).build());
    }
}
