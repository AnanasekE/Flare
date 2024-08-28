package ananaseke.flare.mixin.client;

import ananaseke.flare.callbacks.OnSlotStackPickup;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slot.class)
public class SlotMixin {
    @Inject(method = "takeStack", at = @At("HEAD"))
    private void onTakeStack(int amount, CallbackInfoReturnable<ItemStack> cir) {
        Slot slot = ((Slot) (Object) this);
        OnSlotStackPickup.EVENT.invoker().onSlotStackPickup(slot.id);
    }
}
