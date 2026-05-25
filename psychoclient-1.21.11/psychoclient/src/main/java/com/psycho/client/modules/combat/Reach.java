package com.psycho.client.modules.combat;

import com.psycho.client.module.Module;

/**
 * Reach - shows reach distance indicator (visual only, legit range)
 */
public class Reach extends Module {

    public static float reachDisplay = 3.0f;

    public Reach() {
        super("Reach Display", "Shows your current reach distance on HUD", Category.COMBAT);
    }
}
