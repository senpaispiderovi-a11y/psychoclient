package com.psycho.client;

import com.psycho.client.gui.PsychoClickGui;
import com.psycho.client.hud.PsychoHud;
import com.psycho.client.module.ModuleRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public final class PsychoClient implements ClientModInitializer {
    public static final String BRAND = "PSYCHO CLIENT";
    private static KeyBinding openGuiKey;

    @Override
    public void onInitializeClient() {
        ModuleRegistry.registerDefaults();
        openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.psycho_client.open_gui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.psycho_client.general"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openGuiKey.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(new PsychoClickGui());
                }
            }
        });

        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> PsychoHud.render(drawContext));
    }
}
