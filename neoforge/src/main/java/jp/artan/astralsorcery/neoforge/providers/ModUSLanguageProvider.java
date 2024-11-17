package jp.artan.astralsorcery.neoforge.providers;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModUSLanguageProvider extends LanguageProvider {
    private final String modid;

    public ModUSLanguageProvider(PackOutput output, String modid) {
        super(output, modid, "en_us");
        this.modid = modid;
    }

    @Override
    protected void addTranslations() {

    }
}
