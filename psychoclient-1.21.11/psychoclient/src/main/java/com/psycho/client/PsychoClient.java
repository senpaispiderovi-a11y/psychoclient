package com.psycho.client;

import com.psycho.client.config.ConfigManager;
import com.psycho.client.gui.PsychoGui;
import com.psycho.client.module.ModuleManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PsychoClient implements ClientModInitializer {

    public static final String NAME = "Psycho Client";
    public static final String VERSION = "1.0.0";
    public static final Logger LOGGER = LoggerFactory.getLogger("psychoclient");

    public static PsychoClient INSTANCE;
    public static ModuleManager moduleManager;
    public static ConfigManager configManager;

    private static KeyBinding menuKey;

    @Override
    public void onInitializeClient() {
        INSTANCE = this;
        LOGGER.info("{} v{} loading...", NAME, VERSION);

        // Register Right Shift key to open menu
        menuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.psychoclient.menu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.psychoclient"
        ));

        // Init managers
        moduleManager = new ModuleManager();
        configManager = new ConfigManager();
        configManager.load();

        // Tick handler
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            // Open GUI on Right Shift
            while (menuKey.wasPressed()) {
                client.setScreen(new PsychoGui());
            }

            // Tick all modules
            moduleManager.tick(client);
        });

        LOGGER.info("{} loaded! Press RIGHT SHIFT to open menu.", NAME);
    }
}
