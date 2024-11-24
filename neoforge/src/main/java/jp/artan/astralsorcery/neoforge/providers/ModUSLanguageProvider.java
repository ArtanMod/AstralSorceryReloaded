package jp.artan.astralsorcery.neoforge.providers;

import dev.architectury.registry.registries.RegistrySupplier;
import jp.artan.astralsorcery.init.ASCreativeTab;
import jp.artan.astralsorcery.neoforge.providers.util.LangUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModUSLanguageProvider extends LanguageProvider {
    private final String modid;

    public ModUSLanguageProvider(PackOutput output, String modid) {
        super(output, modid, "en_us");
        this.modid = modid;
    }

    @Override
    protected void addTranslations() {
        this.addBlock();
        this.addItem();

        // ItemGroup
        this.addCreativeModeTab(ASCreativeTab.ASTRAL_SORCERY);
        this.addCreativeModeTab(ASCreativeTab.ATTUNED_PAPERS, "[AS] Attuned Papers");
        this.addCreativeModeTab(ASCreativeTab.ATTUNED_CRYSTALS, "[AS] Attuned Crystals");
    }

    private void addBlock() {
        BuiltInRegistries.BLOCK.stream().filter(block -> {
            return this.modid.equals(BuiltInRegistries.BLOCK.getKey(block).getNamespace()) && !(block instanceof WallSignBlock || block instanceof WallHangingSignBlock);
        }).forEach(block -> {
            String blockId = BuiltInRegistries.BLOCK.getKey(block).getPath();
            this.add(block.asItem(), LangUtils.toEnglishName(blockId));
        });
    }

    private void addItem() {
        BuiltInRegistries.ITEM.stream().filter(item -> {
            return this.modid.equals(BuiltInRegistries.ITEM.getKey(item).getNamespace()) && !(item instanceof BlockItem);
        }).forEach(item -> {
            String itemId = BuiltInRegistries.ITEM.getKey(item).getPath();
            this.add(item, LangUtils.toEnglishName(itemId));
        });
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
