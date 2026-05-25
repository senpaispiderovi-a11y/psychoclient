package com.psycho.client.module;

public abstract class Module {

    public final String name;
    public final String description;
    public final Category category;
    public boolean enabled = false;

    public Module(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public void onEnable() {}
    public void onDisable() {}
    public void onTick(net.minecraft.client.MinecraftClient mc) {}

    public void toggle() {
        enabled = !enabled;
        if (enabled) onEnable(); else onDisable();
    }

    public enum Category {
        PERFORMANCE("Performance"),
        VISUAL("Visual"),
        MOVEMENT("Movement"),
        COMBAT("Combat"),
        MISC("Misc");

        public final String display;
        Category(String display) { this.display = display; }
    }
}
