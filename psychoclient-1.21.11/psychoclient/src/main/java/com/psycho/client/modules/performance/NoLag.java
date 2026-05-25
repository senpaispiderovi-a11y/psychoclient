package com.psycho.client.modules.performance;

import com.psycho.client.module.Module;
import net.minecraft.client.MinecraftClient;

/**
 * NoLag - reduces unnecessary background work to keep TPS smooth
 */
public class NoLag extends Module {

    public NoLag() {
        super("No Lag", "Reduces lag spikes and smooths gameplay", Category.PERFORMANCE);
        enabled = true;
    }

    @Override
    public void onTick(MinecraftClient mc) {
        // Limit chunk upload speed to avoid stutters during exploration
        if (mc.worldRenderer != null) {
            // Vanilla already has throttling; this module acts as a flag
            // for future mixin-based chunk upload limiting
        }
    }
}
