package jp.artan.astralsorcery.neoforge;

import jp.artan.astralsorcery.AstralSorceryReloaded;
import jp.artan.astralsorcery.neoforge.providers.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@Mod(AstralSorceryReloaded.MOD_ID)
public final class AstralSorceryReloadedNeoForge {
    public AstralSorceryReloadedNeoForge(IEventBus eventBus) {
        AstralSorceryReloaded.init();
        eventBus.addListener(this::commonSetup);
        eventBus.addListener(this::onClientSetup);
        eventBus.addListener(this::registerProviders);
    }

    public void commonSetup(final FMLCommonSetupEvent event) {
        AstralSorceryReloaded.commonSetup();
    }

    public void onClientSetup(FMLClientSetupEvent event) {
        AstralSorceryReloaded.initClient();
    }

    private void registerProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        // Model
        generator.addProvider(event.includeClient(), new ModBlockModelProvider(output, AstralSorceryReloaded.MOD_ID, existingFileHelper));
        generator.addProvider(event.includeClient(), new ModItemModelProvider(output, AstralSorceryReloaded.MOD_ID, existingFileHelper));

        // LootTable
        generator.addProvider(event.includeServer(), new ModLootTableProvider(output, lookupProvider));

        // Lang
        generator.addProvider(event.includeClient(), new ModUDLanguageProvider(output, AstralSorceryReloaded.MOD_ID));
        generator.addProvider(event.includeClient(), new ModUSLanguageProvider(output, AstralSorceryReloaded.MOD_ID));
        generator.addProvider(event.includeClient(), new ModJPLanguageProvider(output, AstralSorceryReloaded.MOD_ID));

        // Recipe
        generator.addProvider(event.includeClient(), new ModRecipeProvider.Runner(output, lookupProvider));

        // Tag
        ModBlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(output, lookupProvider, AstralSorceryReloaded.MOD_ID, existingFileHelper);
        generator.addProvider(event.includeClient(), blockTagsProvider);
        //generator.addProvider(event.includeClient(), new ModItemTagsProvider(output, lookupProvider, blockTagsProvider.contentsGetter(), AstralSorceryReloaded.MOD_ID, existingFileHelper));
    }
}
