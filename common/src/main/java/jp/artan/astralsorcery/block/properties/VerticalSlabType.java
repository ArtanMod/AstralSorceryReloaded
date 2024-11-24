package jp.artan.astralsorcery.block.properties;

import net.minecraft.util.StringRepresentable;

public enum VerticalSlabType implements StringRepresentable {
    HALF("half"), DOUBLE("double");

    private final String name;

    private VerticalSlabType(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String getSerializedName() {
        return this.name;
    }
}

