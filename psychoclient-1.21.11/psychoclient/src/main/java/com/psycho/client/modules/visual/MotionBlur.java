package com.psycho.client.modules.visual;

import com.psycho.client.module.Module;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.SimpleFramebuffer;
import org.lwjgl.opengl.GL30;

public class MotionBlur extends Module {

    public static float strength = 0.5f;
    private static SimpleFramebuffer accumBuffer = null;
    private static int lastW = -1, lastH = -1;

    public MotionBlur() {
        super("Motion Blur", "Smooth Lunar-style temporal motion blur", Category.VISUAL);
        enabled = true;
    }

    public static void renderBlur() {
        if (!isMotionBlurEnabled()) return;
        MinecraftClient mc = MinecraftClient.getInstance();
        Framebuffer main = mc.getFramebuffer();
        int w = main.textureWidth, h = main.textureHeight;

        if (accumBuffer == null || lastW != w || lastH != h) {
            if (accumBuffer != null) accumBuffer.delete();
            accumBuffer = new SimpleFramebuffer(w, h, false, MinecraftClient.IS_SYSTEM_MAC);
            accumBuffer.setClearColor(0,0,0,1);
            accumBuffer.clear(MinecraftClient.IS_SYSTEM_MAC);
            lastW = w; lastH = h;
            blit(main.fbo, accumBuffer.fbo, w, h);
            return;
        }

        float alpha = Math.max(0.01f, Math.min(0.99f, 1.0f - strength));
        accumBuffer.beginWrite(false);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1f, 1f, 1f, alpha);
        RenderSystem.setShaderTexture(0, main.getColorAttachment());
        drawQuad(w, h);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.disableBlend();
        main.beginWrite(false);
        RenderSystem.setShaderTexture(0, accumBuffer.getColorAttachment());
        drawQuad(w, h);
    }

    private static void blit(int src, int dst, int w, int h) {
        GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, src);
        GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, dst);
        GL30.glBlitFramebuffer(0,0,w,h,0,0,w,h,GL30.GL_COLOR_BUFFER_BIT,GL30.GL_LINEAR);
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
    }

    private static void drawQuad(int w, int h) {
        net.minecraft.client.render.BufferRenderer.drawWithGlobalProgram(
            net.minecraft.client.render.Tessellator.getInstance().begin(
                net.minecraft.client.render.VertexFormat.DrawMode.QUADS,
                net.minecraft.client.render.VertexFormats.POSITION_TEXTURE)
                .vertex(0,h,0).texture(0,0)
                .vertex(w,h,0).texture(1,0)
                .vertex(w,0,0).texture(1,1)
                .vertex(0,0,0).texture(0,1)
                .end());
    }

    private static boolean isMotionBlurEnabled() {
        var m = com.psycho.client.PsychoClient.moduleManager.getByName("Motion Blur");
        return m != null && m.enabled;
    }
}
