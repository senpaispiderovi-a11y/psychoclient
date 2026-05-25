package com.psycho.client.gui;

import com.psycho.client.PsychoClient;
import com.psycho.client.module.Module;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.List;

public class PsychoGui extends Screen {

    private Module.Category selectedCategory = Module.Category.PERFORMANCE;
    private int scrollOffset = 0;

    // Layout constants - vanilla feeling
    private static final int SIDEBAR_W = 110;
    private static final int MODULE_H = 22;
    private static final int PADDING = 6;

    public PsychoGui() {
        super(Text.literal("Psycho Client"));
    }

    @Override
    public boolean shouldPause() { return false; } // Game keeps running!

    @Override
    public void render(DrawContext ctx, int mx, int my, float delta) {
        // === Semi-transparent background (vanilla book/chest style) ===
        int panelX = width / 2 - 180;
        int panelY = height / 2 - 120;
        int panelW = 360;
        int panelH = 240;

        // Outer dark border
        ctx.fill(panelX - 2, panelY - 2, panelX + panelW + 2, panelY + panelH + 2, 0xFF000000);
        // Main panel background (dark vanilla-ish gray)
        ctx.fill(panelX, panelY, panelX + panelW, panelY + panelH, 0xDD1A1A1A);
        // Top title bar
        ctx.fill(panelX, panelY, panelX + panelW, panelY + 18, 0xFF2A2A2A);

        // Title text
        ctx.drawCenteredTextWithShadow(textRenderer,
                "§fPsycho §7Client §8| §7v1.0", panelX + panelW / 2, panelY + 5, 0xFFFFFF);

        // === Sidebar - Category tabs ===
        int tabX = panelX + PADDING;
        int tabY = panelY + 22;
        for (Module.Category cat : Module.Category.values()) {
            boolean selected = cat == selectedCategory;
            int tabColor = selected ? 0xFF3A3A3A : 0xFF252525;
            int textColor = selected ? 0xFFFFFFFF : 0xFF888888;

            ctx.fill(tabX, tabY, tabX + SIDEBAR_W, tabY + 16, tabColor);
            if (selected) {
                // Accent line on left
                ctx.fill(tabX, tabY, tabX + 2, tabY + 16, 0xFFAAAAAA);
            }
            ctx.drawTextWithShadow(textRenderer, cat.display, tabX + 6, tabY + 4, textColor);
            tabY += 18;
        }

        // === Module list ===
        int listX = panelX + SIDEBAR_W + PADDING * 2;
        int listY = panelY + 24;
        int listW = panelW - SIDEBAR_W - PADDING * 3;

        List<Module> mods = PsychoClient.moduleManager.getByCategory(selectedCategory);
        int modY = listY - scrollOffset;

        for (Module m : mods) {
            if (modY + MODULE_H < listY) { modY += MODULE_H + 2; continue; }
            if (modY > panelY + panelH - 10) break;

            // Module row background
            int rowColor = m.enabled ? 0xFF2D2D2D : 0xFF222222;
            ctx.fill(listX, modY, listX + listW, modY + MODULE_H, rowColor);

            // Enabled indicator
            int indicatorColor = m.enabled ? 0xFF55FF55 : 0xFF555555;
            ctx.fill(listX, modY, listX + 3, modY + MODULE_H, indicatorColor);

            // Module name
            int nameColor = m.enabled ? 0xFFFFFFFF : 0xFF888888;
            ctx.drawTextWithShadow(textRenderer, m.name, listX + 7, modY + 4, nameColor);

            // Status text
            String status = m.enabled ? "§aON" : "§7OFF";
            ctx.drawTextWithShadow(textRenderer, status,
                    listX + listW - textRenderer.getWidth(status.replaceAll("§.", "")) - 5,
                    modY + 4, 0xFFFFFFFF);

            // Description on second line
            ctx.drawTextWithShadow(textRenderer,
                    "§8" + truncate(m.description, 28),
                    listX + 7, modY + 13, 0xFFFFFF);

            modY += MODULE_H + 2;
        }

        // Footer hint
        ctx.drawCenteredTextWithShadow(textRenderer,
                "§7[Click to toggle] [Scroll to browse] [ESC to close]",
                panelX + panelW / 2, panelY + panelH - 10, 0xFFFFFF);

        super.render(ctx, mx, my, delta);
    }

    @Override
    public boolean mouseClicked(double mx, double my, int button) {
        int panelX = width / 2 - 180;
        int panelY = height / 2 - 120;
        int panelW = 360;

        // --- Sidebar category click ---
        int tabX = panelX + PADDING;
        int tabY = panelY + 22;
        for (Module.Category cat : Module.Category.values()) {
            if (mx >= tabX && mx <= tabX + SIDEBAR_W && my >= tabY && my <= tabY + 16) {
                selectedCategory = cat;
                scrollOffset = 0;
                return true;
            }
            tabY += 18;
        }

        // --- Module toggle click ---
        int listX = panelX + SIDEBAR_W + PADDING * 2;
        int listY = panelY + 24;
        int listW = panelW - SIDEBAR_W - PADDING * 3;
        List<Module> mods = PsychoClient.moduleManager.getByCategory(selectedCategory);

        int modY = listY - scrollOffset;
        for (Module m : mods) {
            if (mx >= listX && mx <= listX + listW && my >= modY && my <= modY + MODULE_H) {
                m.toggle();
                // Save config on change
                PsychoClient.configManager.save();
                return true;
            }
            modY += MODULE_H + 2;
        }

        return super.mouseClicked(mx, my, button);
    }

    @Override
    public boolean mouseScrolled(double mx, double my, double hScroll, double vScroll) {
        scrollOffset = Math.max(0, scrollOffset - (int)(vScroll * 10));
        return true;
    }

    private String truncate(String s, int max) {
        return s.length() > max ? s.substring(0, max - 1) + "…" : s;
    }
}
