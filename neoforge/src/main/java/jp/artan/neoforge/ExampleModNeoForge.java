package jp.artan.neoforge;

import net.neoforged.fml.common.Mod;

import jp.artan.ExampleMod;

@Mod(ExampleMod.MOD_ID)
public final class ExampleModNeoForge {
    public ExampleModNeoForge() {
        // Run our common setup.
        ExampleMod.init();
    }
}
