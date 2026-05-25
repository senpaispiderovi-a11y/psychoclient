package com.psycho.client.modules.movement;

import com.psycho.client.module.Module;
import net.minecraft.client.MinecraftClient;

public class Sprint extends Module {

    public Sprint() {
        super("Auto Sprint", "Always sprint like Lunar Client", Category.MOVEMENT);
        enabled = true;
    }

    @Override
    public void onTick(MinecraftClient mc) {
        if (mc.player == null) return;
        if (mc.player.forwardSpeed > 0 && !mc.player.isSprinting()) {
            mc.player.setSprinting(true);
        }
    }
}
