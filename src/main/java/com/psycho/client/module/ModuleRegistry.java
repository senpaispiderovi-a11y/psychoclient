package com.psycho.client.module;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ModuleRegistry {
    private static final List<ClientModule> MODULES = new ArrayList<>();
    private static boolean registered;
    private ModuleRegistry() {}

    public static void registerDefaults() {
        if (registered) return;
        registered = true;
        add("FPS Counter", "Displays your current frames per second.", ClientModule.Category.HUD, true);
        add("Coordinates", "Displays your current XYZ coordinates.", ClientModule.Category.HUD, false);
        add("Keystrokes", "Shows movement and jump key states.", ClientModule.Category.HUD, false);
        add("CPS Counter", "Displays left and right click rate.", ClientModule.Category.HUD, false);
        add("Armor HUD", "Shows equipped armor durability.", ClientModule.Category.HUD, false);
        add("Target HUD", "A compact target information panel (planned).", ClientModule.Category.PVP, false);
        add("Crosshair Editor", "Custom crosshair options (planned).", ClientModule.Category.VISUAL, false);
        add("Zoom", "Hold the configured key to zoom (planned).", ClientModule.Category.VISUAL, false);
        add("Hit Color", "Visual hit feedback (planned).", ClientModule.Category.PVP, false);
        add("Toggle Sprint", "Keeps sprint enabled when possible (planned).", ClientModule.Category.PVP, false);
        add("Low Effects", "Reduces selected visual effects (planned).", ClientModule.Category.PERFORMANCE, false);
        add("HUD Editor", "HUD element positioning editor (planned).", ClientModule.Category.HUD, false);
    }

    private static void add(String name, String description, ClientModule.Category category, boolean enabled) {
        MODULES.add(new ClientModule(name, description, category, enabled));
    }

    public static List<ClientModule> all() { return Collections.unmodifiableList(MODULES); }
    public static List<ClientModule> in(ClientModule.Category category) {
        if (category == ClientModule.Category.ALL) return all();
        return MODULES.stream().filter(m -> m.category() == category).toList();
    }
    public static ClientModule find(String name) {
        return MODULES.stream().filter(m -> m.name().equals(name)).findFirst().orElse(null);
    }
}
