package jp.artan.astralsorcery.neoforge.providers;

import dev.architectury.registry.registries.RegistrySupplier;
import jp.artan.astralsorcery.init.ASCreativeTab;
import jp.artan.astralsorcery.neoforge.providers.util.LangUtils;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModUSLanguageProvider extends LanguageProvider {
    private final String modid;

    public ModUSLanguageProvider(PackOutput output, String modid) {
        super(output, modid, "en_us");
        this.modid = modid;
    }

    @Override
    protected void addTranslations() {
        // ItemGroup
        this.addCreativeModeTab(ASCreativeTab.ASTRAL_SORCERY);
        this.addCreativeModeTab(ASCreativeTab.ATTUNED_PAPERS, "[AS] Attuned Papers");
        this.addCreativeModeTab(ASCreativeTab.ATTUNED_CRYSTALS, "[AS] Attuned Crystals");
    }

    protected void addCreativeModeTab(RegistrySupplier<CreativeModeTab> creativeModeTab) {
        String var10000 = creativeModeTab.getId().getNamespace();
        String id = "itemGroup." + var10000 + "." + creativeModeTab.getId().getPath();
        this.addCreativeModeTab(creativeModeTab, LangUtils.toEnglishName(id.replace("itemGroup." + this.modid + ".", "")));
    }

    protected void addCreativeModeTab(RegistrySupplier<CreativeModeTab> creativeModeTab, String name) {
        String var10000 = creativeModeTab.getId().getNamespace();
        String id = "itemGroup." + var10000 + "." + creativeModeTab.getId().getPath();
        this.add(id, name);
    }
}
