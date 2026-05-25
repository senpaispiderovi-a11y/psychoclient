package com.psycho.client.mixin;

import com.psycho.client.modules.visual.MotionBlur;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(
        method = "render",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/hud/InGameHud;render(Lnet/minecraft/client/gui/DrawContext;F)V",
            shift = At.Shift.BEFORE
        )
    )
    private void onBeforeHud(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
        MotionBlur.renderBlur();
    }
}
