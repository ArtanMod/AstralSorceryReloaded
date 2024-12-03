package jp.artan.astralsorcery.neoforge.providers;

import jp.artan.astralsorcery.block.TileBlock;
import jp.artan.astralsorcery.block.VerticalSlabBlock;
import jp.artan.astralsorcery.block.properties.VerticalSlabType;
import jp.artan.astralsorcery.init.ASBlocks;
import jp.artan.astralsorcery.sets.StoneDecoration;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlockModelProvider extends BlockStateProvider {

    public ModBlockModelProvider(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        // 大理石
        ResourceLocation marbleRL = this.modLoc("block/marble");
        this.cubeAll(ASBlocks.MARBLE.get(), null);
        this.cubeAll(ASBlocks.MARBLE_BRICKS.get(), null);
        this.cubeAll(ASBlocks.MARBLE_CHISELED.get(), null);
        this.cubeAll(ASBlocks.MARBLE_ENGRAVED.get(), null);
        this.stoneDecoration(ASBlocks.MARBLE_DECORATION, ASBlocks.MARBLE);
        this.stoneDecoration(ASBlocks.MARBLE_BRICKS_DECORATION, ASBlocks.MARBLE_BRICKS);
        this.stoneDecoration(ASBlocks.MARBLE_CHISELED_DECORATION, ASBlocks.MARBLE_CHISELED);
        this.stoneDecoration(ASBlocks.MARBLE_ENGRAVED_DECORATION, ASBlocks.MARBLE_ENGRAVED);

        // 大理石のアーチ
        ResourceLocation marbleArchRL = this.modLoc("block/marble_arch");
        BlockModelBuilder marbleArchModel = this.models().cube(this.getBlockId(ASBlocks.MARBLE_ARCH), marbleRL, marbleRL, marbleArchRL, marbleArchRL, marbleArchRL, marbleArchRL)
                .texture("particle", marbleArchRL);
        this.simpleBlock(ASBlocks.MARBLE_ARCH.get(), marbleArchModel);
        this.simpleBlockItem(ASBlocks.MARBLE_ARCH.get(), marbleArchModel);
        this.stoneDecoration(ASBlocks.MARBLE_ARCH_DECORATION, ASBlocks.MARBLE_ARCH, marbleArchRL, marbleRL);

        // 大理石の柱
        ResourceLocation marblePillarUpDownRL = this.modLoc("block/marble_pillar_updown");

        // ルーンの彫られた大理石
        ResourceLocation marbleRunedRL = this.modLoc("block/marble_runed");
        BlockModelBuilder marbleRunedModel = this.models().cube(this.getBlockId(ASBlocks.MARBLE_RUNED), marblePillarUpDownRL, marblePillarUpDownRL, marbleRunedRL, marbleRunedRL, marbleRunedRL, marbleRunedRL)
                .texture("particle", marbleRunedRL);
        this.simpleBlock(ASBlocks.MARBLE_RUNED.get(), marbleRunedModel);
        this.simpleBlockItem(ASBlocks.MARBLE_RUNED.get(), marbleRunedModel);
        this.stoneDecoration(ASBlocks.MARBLE_RUNED_DECORATION, ASBlocks.MARBLE_RUNED, marbleRunedRL, marblePillarUpDownRL);

        // 作業台
        BlockModelBuilder alterDiscovery = this.createAltarDiscoveryModel();
        this.simpleBlock(ASBlocks.ALTAR_DISCOVERY.get(), alterDiscovery);
        this.simpleBlockItem(ASBlocks.ALTAR_DISCOVERY.get(), alterDiscovery);
    }

    protected void cubeAll(Block block, @Nullable String renderType) {
        String blockId = this.getBlockId(block);
        ResourceLocation blockTexture = this.blockTexture(block);
        BlockModelBuilder modelFile = this.models().cubeAll(blockId, blockTexture);
        if (renderType != null) {
            modelFile.renderType(renderType);
        }

        this.simpleBlock(block, modelFile);
        this.simpleBlockItem(block, modelFile);
    }

    protected void slab(SlabBlock block, ResourceLocation baseCubeBlockModel, ResourceLocation side, ResourceLocation top, ResourceLocation bottom, @Nullable String renderType) {
        String blockId = getBlockId(block);

        ModelFile doubleSlab = new ModelFile.UncheckedModelFile(baseCubeBlockModel);
        BlockModelBuilder bottomSlab = this.models().slab(blockId + "_bottom", side, bottom, top);
        BlockModelBuilder topSlab = this.models().slabTop(blockId + "_top", side, bottom, top);
        if(renderType != null) {
            bottomSlab.renderType(renderType);
            topSlab.renderType(renderType);
        }

        this.slabBlock(block, bottomSlab, topSlab, doubleSlab);
        this.simpleBlockItem(block, bottomSlab);
    }

    protected void verticalSlab(VerticalSlabBlock block, @Nullable ResourceLocation baseCubeBlockModel, @Nullable ResourceLocation horizontalBlockModel, ResourceLocation side, ResourceLocation top, ResourceLocation bottom, @Nullable String renderType) {
        String blockId = getBlockId(block);

        ModelFile doubleSlab = new ModelFile.UncheckedModelFile(baseCubeBlockModel);
        boolean isDoubleRotateNull = horizontalBlockModel == null;
        ModelFile doubleSlabRotate = isDoubleRotateNull ? null : new ModelFile.UncheckedModelFile(horizontalBlockModel);

        BlockModelBuilder halfSlab = this.models().getBuilder(blockId + "_half").parent(new ModelFile.UncheckedModelFile(this.modLoc("block/vertical_slab")))
                .texture("side", side)
                .texture("bottom", bottom)
                .texture("top", top);
        BlockModelBuilder halfSlabRotate = this.models().getBuilder(blockId + "_half_rotate").parent(new ModelFile.UncheckedModelFile(this.modLoc("block/vertical_slab_rotate")))
                .texture("side", side)
                .texture("bottom", bottom)
                .texture("top", top);
        if(renderType != null) {
            halfSlab.renderType(renderType);
            halfSlabRotate.renderType(renderType);
        }
        this.getVariantBuilder(block)
                .forAllStatesExcept(state -> {
                    boolean horizontalClicked = state.getValue(VerticalSlabBlock.HORIZONTAL_CLICKED);
                    return ConfiguredModel.builder()
                            .modelFile(state.getValue(VerticalSlabBlock.TYPE) == VerticalSlabType.HALF ? (horizontalClicked && !isDoubleRotateNull ? halfSlabRotate : halfSlab) : (horizontalClicked && !isDoubleRotateNull ? doubleSlabRotate : doubleSlab))
                            .rotationX(state.getValue(VerticalSlabBlock.TYPE) == VerticalSlabType.DOUBLE && horizontalClicked && !isDoubleRotateNull ? 90 : 0)
                            .rotationY((((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180)) % 360)
                            .uvLock(horizontalBlockModel == null)
                            .build();
                }, isDoubleRotateNull ? new Property[]{ VerticalSlabBlock.WATERLOGGED, VerticalSlabBlock.HORIZONTAL_CLICKED } : new Property[]{ VerticalSlabBlock.WATERLOGGED });
        this.simpleBlockItem(block, halfSlab);
    }

    protected void stairs(StairBlock block, ResourceLocation side, ResourceLocation top, ResourceLocation bottom, @Nullable String renderType) {
        String blockId = getBlockId(block);

        BlockModelBuilder stairs = this.models().getBuilder(blockId).parent(new ModelFile.UncheckedModelFile(this.modLoc("block/stairs")))
                .texture("side", side)
                .texture("top", top)
                .texture("bottom", bottom);
        BlockModelBuilder stairsInner = this.models().getBuilder(blockId + "_inner").parent(new ModelFile.UncheckedModelFile(this.modLoc("block/inner_stairs")))
                .texture("side", side)
                .texture("top", top)
                .texture("bottom", bottom);
        BlockModelBuilder stairsOuter = this.models().getBuilder(blockId + "_outer").parent(new ModelFile.UncheckedModelFile(this.modLoc("block/outer_stairs")))
                .texture("side", side)
                .texture("top", top)
                .texture("bottom", bottom);
        if(renderType != null) {
            stairs.renderType(renderType);
            stairsInner.renderType(renderType);
            stairsOuter.renderType(renderType);
        }

        this.stairsBlock(block, stairs, stairsInner, stairsOuter);
        this.simpleBlockItem(block, stairs);
    }

    protected void tile(TileBlock block, ResourceLocation side, ResourceLocation top, ResourceLocation bottom, @Nullable String renderType) {
        String blockId = getBlockId(block);

        this.getVariantBuilder(block).forAllStatesExcept(blockState -> {
            Direction facing = blockState.getValue(TileBlock.FACING);
            Integer value = blockState.getValue(TileBlock.LAYERS);
            int yRot = (int) facing.toYRot(); // Stairs model is rotated 90 degrees clockwise for some reason
            if (facing == Direction.DOWN) {
                yRot += 90; // Top stairs are rotated 90 degrees clockwise
            }
            yRot %= 360;
            boolean uvlock = yRot != 0 || facing == Direction.DOWN; // Don't set uvlock for states that have no rotation
            Function<Direction, Integer> getRotationX = direction -> switch(direction) {
                case UP -> 180;
                case DOWN -> 0;
                default -> 270;
            };
            BlockModelBuilder modelFile = this.models().getBuilder(blockId + "_height" + value).parent(new ModelFile.UncheckedModelFile(this.modLoc("block/tile_height" + value)))
                    .texture("top", top)
                    .texture("side", side)
                    .texture("bottom", bottom);
            if(renderType != null)    {
                modelFile.renderType(renderType);
            }
            return ConfiguredModel.builder()
                    .modelFile(modelFile)
                    .rotationX(getRotationX.apply(facing))
                    .rotationY(yRot)
                    .uvLock(uvlock)
                    .build();
        }, TileBlock.WATERLOGGED);

        ModelFile itemModelFile = this.models().withExistingParent(blockId, this.modLoc(getBlockId(() -> block) + "_height1"));
        this.simpleBlockItem(block, itemModelFile);
    }

    protected void wall(WallBlock block, ResourceLocation side, ResourceLocation top, ResourceLocation bottom, @Nullable String renderType) {
        String blockId = getBlockId(block);

        BlockModelBuilder wallPost = this.models().getBuilder(blockId + "_post").parent(new ModelFile.UncheckedModelFile(this.modLoc("block/template_wall_post")))
                .texture("side", side)
                .texture("top", top)
                .texture("bottom", bottom);
        BlockModelBuilder wallSide = this.models().getBuilder(blockId + "_side").parent(new ModelFile.UncheckedModelFile(this.modLoc("block/template_wall_side")))
                .texture("side", side)
                .texture("top", top)
                .texture("bottom", bottom);
        BlockModelBuilder wallSideTall = this.models().getBuilder(blockId + "_side_tall").parent(new ModelFile.UncheckedModelFile(this.modLoc("block/template_wall_side_tall")))
                .texture("side", side)
                .texture("top", top)
                .texture("bottom", bottom);
        BlockModelBuilder inventory = this.models().getBuilder(blockId + "_inventory").parent(new ModelFile.UncheckedModelFile(this.modLoc("block/wall_inventory")))
                .texture("side", side)
                .texture("top", top)
                .texture("bottom", bottom);
        if(renderType != null) {
            wallPost.renderType(renderType);
            wallSide.renderType(renderType);
            wallSideTall.renderType(renderType);
            inventory.renderType(renderType);
        }

        this.wallBlock((WallBlock) block, wallPost, wallSide, wallSideTall);
        this.simpleBlockItem(block, inventory);
    }

    protected void stoneDecoration(StoneDecoration decoration, Supplier<? extends Block> resource) {
        stoneDecoration(decoration, resource, null);
    }

    protected void stoneDecoration(StoneDecoration decoration, Supplier<? extends Block> resource, @Nullable String renderType) {
        String base = this.getBlockId(resource);
        ResourceLocation all = this.modLoc("block/" + base);
        stoneDecoration(decoration, resource, all, all, renderType);
    }

    protected void stoneDecoration(StoneDecoration decoration, Supplier<? extends Block> resource, ResourceLocation sideRL, ResourceLocation upBottomRL) {
        stoneDecoration(decoration, resource, sideRL, upBottomRL, null);
    }

    protected void stoneDecoration(StoneDecoration decoration, Supplier<? extends Block> resource, ResourceLocation sideRL, ResourceLocation upBottomRL, @Nullable String renderType) {
        ResourceLocation baseBlockModelFile = this.blockTexture(resource.get());

        this.slab(decoration.slab.get(), baseBlockModelFile, sideRL, upBottomRL, upBottomRL, renderType);
        this.verticalSlab(decoration.verticalSlab.get(), baseBlockModelFile, null, sideRL, upBottomRL, upBottomRL, renderType);
        this.stairs(decoration.stairs.get(), sideRL, upBottomRL, upBottomRL, renderType);
        this.tile(decoration.tile.get(), sideRL, upBottomRL, upBottomRL, renderType);
        this.wall(decoration.wall.get(), sideRL, upBottomRL, upBottomRL, renderType);
    }

    private BlockModelBuilder createAltarDiscoveryModel() {
        return this.models().withExistingParent("altar_discovery", "block/block")
                .texture("particle", this.modLoc("block/wood_raw"))
                .texture("table_tex_top", this.modLoc("block/altar_1_top"))
                .texture("table_tex_side", this.modLoc("block/altar_1_side"))
                .texture("table_tex_bottom", this.modLoc("block/altar_1_bottom"))
                // pillar
                .element()
                .from(4, 2, 4).to(12, 9.5f, 12)
                .face(Direction.NORTH).uvs(4, 6, 12, 14).texture("#table_tex_side").end()
                .face(Direction.SOUTH).uvs(4, 6, 12, 14).texture("#table_tex_side").end()
                .face(Direction.WEST).uvs(4, 6, 12, 14).texture("#table_tex_side").end()
                .face(Direction.EAST).uvs(4, 6, 12, 14).texture("#table_tex_side").end()
                .end()
                // base
                .element()
                .from(2, 0, 2).to(14, 2, 14)
                .face(Direction.DOWN).uvs(2, 2, 14, 14).texture("#table_tex_bottom").end()
                .face(Direction.UP).uvs(2, 2, 14, 14).texture("#table_tex_bottom").end()
                .face(Direction.NORTH).uvs(2, 14, 14, 16).texture("#table_tex_side").end()
                .face(Direction.SOUTH).uvs(2, 14, 14, 16).texture("#table_tex_side").end()
                .face(Direction.WEST).uvs(2, 14, 14, 16).texture("#table_tex_side").end()
                .face(Direction.EAST).uvs(2, 14, 14, 16).texture("#table_tex_side").end()
                .end()
                // top
                .element()
                .from(0, 9.5f, 0).to(16, 15.5f, 16)
                .face(Direction.DOWN).uvs(0, 0, 16, 15.5f).texture("#table_tex_bottom").end()
                .face(Direction.UP).uvs(0, 0, 16, 16).texture("#table_tex_top").end()
                .face(Direction.NORTH).uvs(0, 0, 16, 6).texture("#table_tex_side").end()
                .face(Direction.SOUTH).uvs(0, 0, 16, 6).texture("#table_tex_side").end()
                .face(Direction.WEST).uvs(0, 0, 16, 6).texture("#table_tex_side").end()
                .face(Direction.EAST).uvs(0, 0, 16, 6).texture("#table_tex_side").end()
                .end();
    }

    private String getBlockId(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }

    private String getBlockId(Supplier<? extends Block> block) {
        return getBlockId(block.get());
    }
}
