package jp.artan.astralsorcery;

import jp.artan.astralsorcery.init.ASBlocks;
import jp.artan.astralsorcery.init.ASCreativeTab;
import jp.artan.astralsorcery.init.ASItems;
import net.minecraft.resources.ResourceLocation;

public final class AstralSorceryReloaded {
    public static final String MOD_ID = "astralsorcery";

    public static void init() {
        ASCreativeTab.register();
        ASBlocks.register();
        ASItems.register();
    }

    public static void initClient() {
    }

    public static void commonSetup() {
    }

    public static ResourceLocation getResource(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }
}
