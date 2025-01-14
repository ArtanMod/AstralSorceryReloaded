package jp.artan.astralsorcery.init;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import jp.artan.astralsorcery.AstralSorceryReloaded;
import jp.artan.astralsorcery.block.entity.LuminousCraftingTableEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Set;

public class ASBlockEntity {
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(AstralSorceryReloaded.MOD_ID, Registries.BLOCK_ENTITY_TYPE);

    public static void register() {
        BLOCK_ENTITIES.register();
    }

    public static final RegistrySupplier<BlockEntityType<LuminousCraftingTableEntity>> LUMINOUS_CRAFTING_TABLE_ENTITY = BLOCK_ENTITIES.register("luminous_crafting_table_entity",
            () -> new BlockEntityType<>(LuminousCraftingTableEntity::new, Set.of(ASBlocks.LUMINOUS_CRAFTING_TABLE.get())));
}
