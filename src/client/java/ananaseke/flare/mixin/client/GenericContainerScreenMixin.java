package ananaseke.flare.mixin.client;

import ananaseke.flare.callbacks.DrawBackgroundScreenCallback;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GenericContainerScreen.class)
public class GenericContainerScreenMixin {
    @Inject(method = "drawBackground", at = @At("HEAD"))
    private void drawBackground(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo ci) {
        DrawBackgroundScreenCallback.EVENT.invoker().drawBackground(context);
    }
}
