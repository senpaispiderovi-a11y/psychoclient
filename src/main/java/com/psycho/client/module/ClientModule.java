package com.psycho.client.module;

public final class ClientModule {
    private final String name;
    private final String description;
    private final Category category;
    private boolean enabled;

    public ClientModule(String name, String description, Category category, boolean enabled) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.enabled = enabled;
    }

    public String name() { return name; }
    public String description() { return description; }
    public Category category() { return category; }
    public boolean enabled() { return enabled; }
    public void toggle() { enabled = !enabled; }
    public void setEnabled(boolean value) { enabled = value; }

    public enum Category {
        ALL("All"), HUD("HUD"), PVP("PvP"), VISUAL("Visual"), PERFORMANCE("Performance");
        private final String label;
        Category(String label) { this.label = label; }
        public String label() { return label; }
    }
}
