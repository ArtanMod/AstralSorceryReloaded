package jp.artan.astralsorcery.init;

import dev.architectury.registry.registries.DeferredRegister;
import jp.artan.astralsorcery.AstralSorceryReloaded;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;

public class ASItems {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(AstralSorceryReloaded.MOD_ID, Registries.ITEM);

    public static void register() {
        ITEMS.register();
    }

}
