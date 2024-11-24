package jp.artan.astralsorcery.neoforge.providers;

import jp.artan.astralsorcery.init.ASBlocks;
import jp.artan.astralsorcery.init.ASItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModJPLanguageProvider extends LanguageProvider {
    private final String modid;

    public ModJPLanguageProvider(PackOutput output, String modid) {
        super(output, modid, "ja_jp");
        this.modid = modid;
    }

    @Override
    protected void addTranslations() {

        // 大理石
        this.add(ASBlocks.MARBLE.get(), "大理石");

        // 杖
        this.add(ASItems.RESONATING_WAND.get(), "共鳴の杖");

        // アクアマリン
        this.add(ASItems.AQUAMARINE.get(), "アクアマリン");
    }

    @Override
    public void add(Block key, String name) {
        add(key.asItem(), name);
    }
}
