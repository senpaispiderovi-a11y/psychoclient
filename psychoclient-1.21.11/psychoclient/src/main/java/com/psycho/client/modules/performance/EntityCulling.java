package com.psycho.client.modules.performance;

import com.psycho.client.module.Module;
import net.minecraft.client.MinecraftClient;

/**
 * Entity Culling - skips rendering entities not visible to the player.
 * Works alongside the mixin for actual culling logic.
 */
public class EntityCulling extends Module {

    public static boolean active = false;

    public EntityCulling() {
        super("Entity Culling", "Skip rendering entities behind walls", Category.PERFORMANCE);
        enabled = true;
    }

    @Override
    public void onEnable() { active = true; }

    @Override
    public void onDisable() { active = false; }
}
