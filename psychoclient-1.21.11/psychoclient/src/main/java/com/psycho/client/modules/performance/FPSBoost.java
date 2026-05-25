package com.psycho.client.modules.performance;

import com.psycho.client.module.Module;
import net.minecraft.client.MinecraftClient;

/**
 * FPS Boost - reduces unnecessary render load
 * Disables cloud rendering, lowers particle count, limits entity render distance
 */
public class FPSBoost extends Module {

    public FPSBoost() {
        super("FPS Boost", "Maximizes FPS by reducing visual overhead", Category.PERFORMANCE);
        enabled = true; // ON by default
    }

    @Override
    public void onEnable() {
        apply(MinecraftClient.getInstance());
    }

    @Override
    public void onDisable() {
        restore(MinecraftClient.getInstance());
    }

    private void apply(MinecraftClient mc) {
        if (mc.options == null) return;
        mc.options.getCloudRenderMode().setValue(net.minecraft.client.option.CloudRenderMode.OFF);
        mc.options.getParticles().setValue(net.minecraft.particle.ParticleTypes.AMBIENT_ENTITY_EFFECT != null
                ? net.minecraft.client.option.ParticlesMode.MINIMAL
                : net.minecraft.client.option.ParticlesMode.MINIMAL);
        mc.options.getEntityDistanceScaling().setValue(0.75);
        mc.options.write();
    }

    private void restore(MinecraftClient mc) {
        if (mc.options == null) return;
        mc.options.getCloudRenderMode().setValue(net.minecraft.client.option.CloudRenderMode.FAST);
        mc.options.getParticles().setValue(net.minecraft.client.option.ParticlesMode.ALL);
        mc.options.getEntityDistanceScaling().setValue(1.0);
        mc.options.write();
    }
}
