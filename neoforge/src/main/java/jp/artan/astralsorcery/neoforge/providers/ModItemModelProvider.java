package jp.artan.astralsorcery.neoforge.providers;

import jp.artan.astralsorcery.init.ASItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        this.getBuilder(getItemId(ASItems.RESONATING_WAND))
                .parent(new ModelFile.UncheckedModelFile("item/handheld"))
                .texture("layer0", this.modLoc("item/wand"));

        this.basicItem(ASItems.AQUAMARINE.get());
    }

    private String getItemId(Supplier<? extends Item> item) {
        return BuiltInRegistries.ITEM.getKey(item.get()).getPath();
    }
}
