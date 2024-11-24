package jp.artan.astralsorcery.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ASTags {
    public static class BlockTag {
        public static final TagKey<Block> VERTICAL_SLAB = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "vertical_slab"));
        public static final TagKey<Block> TILE = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "tile"));

        public static void register() {
        }
    }
}
