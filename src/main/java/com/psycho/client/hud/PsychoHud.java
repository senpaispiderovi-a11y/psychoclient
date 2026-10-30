package com.psycho.client.hud;

import com.psycho.client.module.ModuleRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;

public final class PsychoHud {
    private static final int RED = 0xFFE04444;
    private static final int WHITE = 0xFFF2F3F5;
    private static final int MUTED = 0xFFB7BBC4;
    private PsychoHud() {}

    public static void render(DrawContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.options.hudHidden) return;
        int y = 8;
        if (enabled("FPS Counter")) {
            context.fill(8, y - 2, 102, y + 12, 0x990B0D12);
            context.fill(8, y - 2, 10, y + 12, RED);
            context.drawText(client.textRenderer, "FPS  " + client.getCurrentFps(), 15, y, WHITE, true);
            y += 17;
        }
        if (enabled("Coordinates")) {
            ClientPlayerEntity player = client.player;
            String coords = String.format("XYZ  %d  %d  %d", player.getBlockX(), player.getBlockY(), player.getBlockZ());
            int w = client.textRenderer.getWidth(coords) + 12;
            context.fill(8, y - 2, 8 + w, y + 12, 0x990B0D12);
            context.fill(8, y - 2, 10, y + 12, RED);
            context.drawText(client.textRenderer, coords, 15, y, MUTED, true);
        }
    }

    private static boolean enabled(String name) {
        var module = ModuleRegistry.find(name);
        return module != null && module.enabled();
    }
}
