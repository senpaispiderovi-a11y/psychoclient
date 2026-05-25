package com.psycho.client.modules.movement;

import com.psycho.client.module.Module;
import net.minecraft.client.MinecraftClient;

public class NoFall extends Module {

    public NoFall() {
        super("No Fall", "Negate fall damage", Category.MOVEMENT);
    }

    @Override
    public void onTick(MinecraftClient mc) {
        if (mc.player == null) return;
        if (mc.player.fallDistance > 2.0f) {
            mc.player.fallDistance = 0;
        }
    }
}
