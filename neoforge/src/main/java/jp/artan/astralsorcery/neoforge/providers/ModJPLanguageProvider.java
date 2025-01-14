package jp.artan.astralsorcery.neoforge.providers;

import jp.artan.astralsorcery.init.ASBlocks;
import jp.artan.astralsorcery.init.ASItems;
import jp.artan.astralsorcery.sets.StoneDecoration;
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
        this.add(ASBlocks.MARBLE_ARCH.get(), "大理石のアーチ");
        this.add(ASBlocks.MARBLE_BRICKS.get(), "大理石のレンガ");
        this.add(ASBlocks.MARBLE_CHISELED.get(), "模様入り大理石");
        this.add(ASBlocks.MARBLE_ENGRAVED.get(), "彫刻された大理石");
        this.add(ASBlocks.MARBLE_RUNED.get(), "ルーンの彫られた大理石");
        this.add(ASBlocks.MARBLE_PILLAR.get(), "大理石の柱");
        this.addStoneDecoration(ASBlocks.MARBLE_DECORATION, "大理石");
        this.addStoneDecoration(ASBlocks.MARBLE_ARCH_DECORATION, "大理石のアーチ");
        this.addStoneDecoration(ASBlocks.MARBLE_BRICKS_DECORATION, "大理石のレンガ");
        this.addStoneDecoration(ASBlocks.MARBLE_CHISELED_DECORATION, "模様入り大理石");
        this.addStoneDecoration(ASBlocks.MARBLE_ENGRAVED_DECORATION, "彫刻された大理石");
        this.addStoneDecoration(ASBlocks.MARBLE_RUNED_DECORATION, "ルーンの彫られた大理石");

        // 杖
        this.add(ASItems.RESONATING_WAND.get(), "共鳴の杖");

        // アクアマリン
        this.add(ASItems.AQUAMARINE.get(), "アクアマリン");

        // 作業台
        this.add(ASBlocks.LUMINOUS_CRAFTING_TABLE.get(), "光明の作業台");
    }

    protected void addStoneDecoration(StoneDecoration decoration, String name) {
        this.add(decoration.slab.get(), name + "のハーフブロック");
        this.add(decoration.verticalSlab.get(), name + "の縦ハーフブロック");
        this.add(decoration.stairs.get(), name + "の階段");
        this.add(decoration.tile.get(), name + "のタイル");
        this.add(decoration.wall.get(), name + "の塀");
    }

    @Override
    public void add(Block key, String name) {
        add(key.asItem(), name);
    }
}
