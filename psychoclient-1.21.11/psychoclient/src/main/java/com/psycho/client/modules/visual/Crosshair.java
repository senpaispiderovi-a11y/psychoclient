package com.psycho.client.modules.visual;

import com.psycho.client.module.Module;

/**
 * Custom crosshair styles like Lunar Client
 */
public class Crosshair extends Module {

    public static CrosshairStyle style = CrosshairStyle.CROSS;
    public static int color = 0xFFFFFFFF;

    public Crosshair() {
        super("Custom Crosshair", "Lunar-style crosshair customization", Category.VISUAL);
    }

    public enum CrosshairStyle {
        CROSS, DOT, CIRCLE, ARROW
    }
}
