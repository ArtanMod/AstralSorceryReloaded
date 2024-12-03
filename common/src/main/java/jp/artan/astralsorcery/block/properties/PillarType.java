package jp.artan.astralsorcery.block.properties;

import net.minecraft.util.StringRepresentable;

public enum PillarType implements StringRepresentable {
    TOP("top"), MIDDLE("middle"), BOTTOM("bottom");

    private final String name;

    private PillarType(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String getSerializedName() {
        return this.name;
    }
}

