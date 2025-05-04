package ananaseke.flare.mixin.client;

import ananaseke.flare.callbacks.ItemUsedOnBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Inject(method = "useOnBlock", at = @At(value = "HEAD"))
    private void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        if (context.getWorld().isClient) {
            ItemUsedOnBlock.EVENT.invoker().onItemUsed(context);
        }
    }

//    @Inject(method = "use", at = @At(value = "HEAD"))
//    private void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
//
//    }
}
