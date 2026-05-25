package com.psycho.client.modules.visual;

import com.psycho.client.module.Module;
import net.minecraft.client.MinecraftClient;

public class FullBright extends Module {

    private double prevGamma = 1.0;

    public FullBright() {
        super("Full Bright", "Always see in the dark like Lunar Client", Category.VISUAL);
    }

    @Override
    public void onEnable() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.options != null) {
            prevGamma = mc.options.getGamma().getValue();
            mc.options.getGamma().setValue(10.0);
        }
    }

    @Override
    public void onDisable() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.options != null) {
            mc.options.getGamma().setValue(prevGamma);
        }
    }
}
