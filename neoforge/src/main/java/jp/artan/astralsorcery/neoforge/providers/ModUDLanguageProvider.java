package jp.artan.astralsorcery.neoforge.providers;

import dev.architectury.registry.registries.RegistrySupplier;
import jp.artan.astralsorcery.init.ASBlocks;
import jp.artan.astralsorcery.init.ASCreativeTab;
import jp.artan.astralsorcery.neoforge.providers.util.LangUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModUDLanguageProvider extends LanguageProvider {
    private final String modid;

    public ModUDLanguageProvider(PackOutput output, String modid) {
        super(output, modid, "en_ud");
        this.modid = modid;
    }

    @Override
    protected void addTranslations() {
        this.addBlock();
        this.addItem();

        // ItemGroup
        this.addCreativeModeTab(ASCreativeTab.ASTRAL_SORCERY);
        this.addCreativeModeTab(ASCreativeTab.ATTUNED_PAPERS, LangUtils.toUpsideDownEnglish("]AS[ Attuned Papers"));
        this.addCreativeModeTab(ASCreativeTab.ATTUNED_CRYSTALS, LangUtils.toUpsideDownEnglish("]AS[ Attuned Crystals"));

        // Block
        this.add(ASBlocks.MARBLE_CHISELED.get(), LangUtils.toUpsideDownEnglish("Chiseled Marble"));
        this.add(ASBlocks.MARBLE_ENGRAVED.get(), LangUtils.toUpsideDownEnglish("Engraved Marble"));
        this.add(ASBlocks.MARBLE_RUNED.get(), LangUtils.toUpsideDownEnglish("Runed Marble"));
        this.add(ASBlocks.ALTAR_DISCOVERY.get(), LangUtils.toUpsideDownEnglish("Luminous Crafting Table"));
    }

    @Override
    public void add(Block key, String name) {
        add(key.asItem(), name);
    }

    private void addBlock() {
        BuiltInRegistries.BLOCK.stream().filter(block -> {
            return this.modid.equals(BuiltInRegistries.BLOCK.getKey(block).getNamespace()) && !addToExclude(block);
        }).forEach(block -> {
            String blockId = BuiltInRegistries.BLOCK.getKey(block).getPath();
            this.add(block, LangUtils.toUpsideDownEnglish(blockId));
        });
    }

    private void addItem() {
        BuiltInRegistries.ITEM.stream().filter(item -> {
            return this.modid.equals(BuiltInRegistries.ITEM.getKey(item).getNamespace()) && !addToExclude(item);
        }).forEach(item -> {
            String itemId = BuiltInRegistries.ITEM.getKey(item).getPath();
            this.add(item, LangUtils.toUpsideDownEnglish(itemId));
        });
    }

    private void addCreativeModeTab(RegistrySupplier<CreativeModeTab> creativeModeTab) {
        String var10000 = creativeModeTab.getId().getNamespace();
        String id = "itemGroup." + var10000 + "." + creativeModeTab.getId().getPath();
        this.addCreativeModeTab(creativeModeTab, LangUtils.toUpsideDownEnglish(id.replace("itemGroup." + this.modid + ".", "")));
    }

    private void addCreativeModeTab(RegistrySupplier<CreativeModeTab> creativeModeTab, String name) {
        String var10000 = creativeModeTab.getId().getNamespace();
        String id = "itemGroup." + var10000 + "." + creativeModeTab.getId().getPath();
        this.add(id, name);
    }

    /**
     * ブロックの自動翻訳から除外するかどうか
     * <p>
     * 追加定義したい場合はオーバーライドしたうえで、super.addToExclude(...)を呼び出すこと
     *
     * @param block ブロック
     * @return true:除外する false:除外しない
     */
    protected boolean addToExclude(Block block) {
        return block instanceof WallSignBlock || block instanceof WallHangingSignBlock || block == ASBlocks.MARBLE_CHISELED.get() || block == ASBlocks.MARBLE_ENGRAVED.get() || block == ASBlocks.MARBLE_RUNED.get() || block == ASBlocks.ALTAR_DISCOVERY.get();
    }

    /**
     * アイテムの自動翻訳から除外するかどうか
     * <p>
     * 追加定義したい場合はオーバーライドしたうえで、super.addToExclude(...)を呼び出すこと
     *
     * @param item アイテム
     * @return true:除外する false:除外しない
     */
    protected boolean addToExclude(Item item) {
        return item instanceof BlockItem;
    }
}
