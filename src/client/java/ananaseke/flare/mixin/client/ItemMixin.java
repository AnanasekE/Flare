package ananaseke.flare.mixin.client;

import ananaseke.flare.callbacks.ItemUsedCallback;
import ananaseke.flare.callbacks.ItemUsedOnBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {
    @Inject(method = "use", at = @At("HEAD"))
    private void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable cir) {
        ItemUsedCallback.EVENT.invoker().onItemUsed(user.getStackInHand(hand));
    }

    @Inject(method = "useOnBlock", at = @At("HEAD"))
    private void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        ItemUsedOnBlock.EVENT.invoker().onItemUsed(context);
    }
}
