package jp.artan.astralsorcery.neoforge.providers;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModJPLanguageProvider extends LanguageProvider {
    private final String modid;

    public ModJPLanguageProvider(PackOutput output, String modid) {
        super(output, modid, "ja_jp");
        this.modid = modid;
    }

    @Override
    protected void addTranslations() {

    }
}
