package jp.artan.astralsorcery.neoforge.providers;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModUDLanguageProvider extends LanguageProvider {
    private final String modid;

    public ModUDLanguageProvider(PackOutput output, String modid) {
        super(output, modid, "en_ud");
        this.modid = modid;
    }

    @Override
    protected void addTranslations() {

    }
}
