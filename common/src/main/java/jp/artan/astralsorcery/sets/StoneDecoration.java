package jp.artan.astralsorcery.sets;

import dev.architectury.registry.registries.RegistrySupplier;
import jp.artan.astralsorcery.block.TileBlock;
import jp.artan.astralsorcery.block.VerticalSlabBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;

public class StoneDecoration {

    /**
     * ハーフブロック
     */
    public final RegistrySupplier<SlabBlock> slab;

    /**
     * 縦ハーフブロック
     */
    public final RegistrySupplier<VerticalSlabBlock> verticalSlab;

    /**
     * 階段
     */
    public final RegistrySupplier<StairBlock> stairs;

    /**
     * タイル
     */
    public final RegistrySupplier<TileBlock> tile;

    /**
     * 塀
     */
    public final RegistrySupplier<WallBlock> wall;

    public StoneDecoration(
            RegistrySupplier<SlabBlock> slab,
            RegistrySupplier<VerticalSlabBlock> verticalSlab,
            RegistrySupplier<StairBlock> stairs,
            RegistrySupplier<TileBlock> tile,
            RegistrySupplier<WallBlock> wall
    ) {
        this.slab = slab;
        this.verticalSlab = verticalSlab;
        this.stairs = stairs;
        this.tile = tile;
        this.wall = wall;
    }
}
