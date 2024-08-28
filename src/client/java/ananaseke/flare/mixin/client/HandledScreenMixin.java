package ananaseke.flare.mixin.client;

import ananaseke.flare.callbacks.DrawSlotCallback;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreen.class)
public class HandledScreenMixin {
    @Inject(method = "drawSlot", at = @At("HEAD"))
    private void drawSlot(DrawContext context, Slot slot, CallbackInfo ci) {
        DrawSlotCallback.EVENT.invoker().drawSlot(context, slot);
    }
}
