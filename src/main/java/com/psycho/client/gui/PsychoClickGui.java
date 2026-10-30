package com.psycho.client.gui;

import com.psycho.client.PsychoClient;
import com.psycho.client.module.ClientModule;
import com.psycho.client.module.ModuleRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.List;

public final class PsychoClickGui extends Screen {
    private static final int BG = 0xF20B0D12;
    private static final int PANEL = 0xFF12151C;
    private static final int PANEL_2 = 0xFF191D26;
    private static final int RED = 0xFFE04444;
    private static final int WHITE = 0xFFF2F3F5;
    private static final int MUTED = 0xFF9299A8;
    private ClientModule.Category selectedCategory = ClientModule.Category.ALL;
    private String selectedModule = "FPS Counter";
    private int scroll;
    private int panelX, panelY, panelW = 570, panelH = 350;

    public PsychoClickGui() { super(Text.literal("PSYCHO CLIENT")); }

    @Override
    protected void init() {
        panelX = (width - panelW) / 2;
        panelY = (height - panelH) / 2;
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        ctx.fill(0, 0, width, height, 0x99000000);
        ctx.fill(panelX, panelY, panelX + panelW, panelY + panelH, BG);
        ctx.fill(panelX, panelY, panelX + panelW, panelY + 3, RED);
        ctx.fill(panelX, panelY + 3, panelX + 145, panelY + panelH, PANEL);
        ctx.fill(panelX + 145, panelY + 3, panelX + 375, panelY + panelH, 0xFF0F1218);
        ctx.fill(panelX + 375, panelY + 3, panelX + panelW, panelY + panelH, PANEL);

        text(ctx, "PSYCHO", panelX + 18, panelY + 18, RED, true);
        text(ctx, "CLIENT", panelX + 77, panelY + 18, WHITE, true);
        text(ctx, "FABRIC 1.21.11", panelX + 18, panelY + 36, MUTED, false);
        ctx.fill(panelX + 14, panelY + 57, panelX + 130, panelY + 58, 0xFF303541);

        int cy = panelY + 72;
        for (ClientModule.Category category : ClientModule.Category.values()) {
            int color = selectedCategory == category ? 0xFF29212A : PANEL;
            ctx.fill(panelX + 10, cy - 5, panelX + 135, cy + 17, color);
            if (selectedCategory == category) ctx.fill(panelX + 10, cy - 5, panelX + 13, cy + 17, RED);
            text(ctx, category.label(), panelX + 22, cy, selectedCategory == category ? WHITE : MUTED, false);
            cy += 30;
        }

        text(ctx, selectedCategory.label().toUpperCase() + " MODULES", panelX + 164, panelY + 18, WHITE, true);
        text(ctx, "Select a module to configure", panelX + 164, panelY + 34, MUTED, false);
        ctx.fill(panelX + 164, panelY + 54, panelX + 357, panelY + 55, 0xFF303541);

        List<ClientModule> modules = ModuleRegistry.in(selectedCategory);
        int listY = panelY + 67;
        for (int i = scroll; i < Math.min(modules.size(), scroll + 8); i++) {
            ClientModule module = modules.get(i);
            int y = listY + (i - scroll) * 32;
            boolean active = module.name().equals(selectedModule);
            ctx.fill(panelX + 157, y, panelX + 365, y + 27, active ? 0xFF252A35 : PANEL_2);
            if (active) ctx.fill(panelX + 157, y, panelX + 159, y + 27, RED);
            text(ctx, module.name(), panelX + 168, y + 5, WHITE, false);
            int toggleColor = module.enabled() ? RED : 0xFF444A56;
            ctx.fill(panelX + 330, y + 8, panelX + 350, y + 19, toggleColor);
            int knobX = module.enabled() ? panelX + 341 : panelX + 331;
            ctx.fill(knobX, y + 9, knobX + 8, y + 18, WHITE);
        }

        ClientModule chosen = ModuleRegistry.find(selectedModule);
        text(ctx, "MODULE SETTINGS", panelX + 393, panelY + 18, WHITE, true);
        ctx.fill(panelX + 393, panelY + 42, panelX + panelW - 17, panelY + 43, 0xFF303541);
        if (chosen != null) {
            text(ctx, chosen.name(), panelX + 393, panelY + 59, RED, true);
            wrapDescription(ctx, chosen.description(), panelX + 393, panelY + 81, 155);
            text(ctx, "STATUS", panelX + 393, panelY + 145, MUTED, true);
            text(ctx, chosen.enabled() ? "ENABLED" : "DISABLED", panelX + 393, panelY + 161, chosen.enabled() ? RED : WHITE, true);
            ctx.fill(panelX + 393, panelY + 190, panelX + 548, panelY + 220, chosen.enabled() ? 0xFF512B36 : 0xFF272D38);
            centered(ctx, chosen.enabled() ? "TURN OFF" : "ENABLE MODULE", panelX + 393, panelY + 190, 155, 30, WHITE);
        }
        text(ctx, "RIGHT SHIFT  •  CLOSE", panelX + 18, panelY + panelH - 23, MUTED, false);
        text(ctx, "PSYCHO CLIENT  /  0.1.0", panelX + 392, panelY + panelH - 23, MUTED, false);
        super.render(ctx, mouseX, mouseY, delta);
    }

    private void wrapDescription(DrawContext ctx, String value, int x, int y, int maxWidth) {
        StringBuilder line = new StringBuilder();
        int lineY = y;
        for (String word : value.split(" ")) {
            String next = line.isEmpty() ? word : line + " " + word;
            if (textWidth(next) > maxWidth) {
                text(ctx, line.toString(), x, lineY, MUTED, false);
                lineY += 12;
                line = new StringBuilder(word);
            } else line = new StringBuilder(next);
        }
        if (!line.isEmpty()) text(ctx, line.toString(), x, lineY, MUTED, false);
    }

    private int textWidth(String s) { return MinecraftClient.getInstance().textRenderer.getWidth(s); }
    private void text(DrawContext ctx, String s, int x, int y, int color, boolean shadow) {
        ctx.drawText(MinecraftClient.getInstance().textRenderer, s, x, y, color, shadow);
    }
    private void centered(DrawContext ctx, String s, int x, int y, int w, int h, int color) {
        text(ctx, s, x + (w - textWidth(s)) / 2, y + (h - 8) / 2, color, false);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0) return super.mouseClicked(mouseX, mouseY, button);
        int x = (int) mouseX, y = (int) mouseY;
        int categoryY = panelY + 67;
        for (ClientModule.Category category : ClientModule.Category.values()) {
            if (inside(x, y, panelX + 10, categoryY - 5, 125, 22)) {
                selectedCategory = category;
                scroll = 0;
                List<ClientModule> list = ModuleRegistry.in(category);
                if (!list.isEmpty()) selectedModule = list.get(0).name();
                return true;
            }
            categoryY += 30;
        }
        List<ClientModule> modules = ModuleRegistry.in(selectedCategory);
        int listY = panelY + 67;
        for (int i = scroll; i < Math.min(modules.size(), scroll + 8); i++) {
            int rowY = listY + (i - scroll) * 32;
            if (inside(x, y, panelX + 157, rowY, 208, 27)) {
                ClientModule module = modules.get(i);
                selectedModule = module.name();
                if (x >= panelX + 322) module.toggle();
                return true;
            }
        }
        ClientModule chosen = ModuleRegistry.find(selectedModule);
        if (chosen != null && inside(x, y, panelX + 393, panelY + 190, 155, 30)) chosen.toggle();
        return true;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        int count = ModuleRegistry.in(selectedCategory).size();
        scroll = Math.max(0, Math.min(Math.max(0, count - 8), scroll - (int) Math.signum(verticalAmount)));
        return true;
    }

    private boolean inside(int x, int y, int rx, int ry, int rw, int rh) {
        return x >= rx && x <= rx + rw && y >= ry && y <= ry + rh;
    }

    @Override public boolean shouldPause() { return false; }
}
