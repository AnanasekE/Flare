package ananaseke.flare.mixin.client;

import ananaseke.flare.Utils.ItemPriceUtils;
import ananaseke.flare.Utils.ItemUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Mixin(ItemStack.class)
public class TooltipMixin {
    @Inject(method = "getTooltip", at = @At("RETURN"), cancellable = true)
    private void getTooltip(Item.TooltipContext context, @Nullable PlayerEntity player, TooltipType type, CallbackInfoReturnable<List<Text>> cir) {
        ItemStack stack = (ItemStack) (Object) this; // Cast 'this' to ItemStack
        Optional<String> optionalInternalName = ItemUtils.getInternalName(stack); // Assuming getInternalName returns Optional<String>

        optionalInternalName.ifPresent(internalName -> {
            ItemPriceUtils.getBazaarItemPrice(internalName).ifPresent(price -> {
                String formattedPrice = NumberFormat.getNumberInstance(Locale.GERMAN).format(price);
                Text completed = Text.of("Lowest BIN price: " + formattedPrice);
                List<Text> outList = cir.getReturnValue();
                outList.add(completed);
                cir.setReturnValue(outList);
            });
        });
    }
}
