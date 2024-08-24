package ananaseke.flare.mixin.client;

import ananaseke.flare.FlareClient;
import ananaseke.flare.KeyBinds;
import ananaseke.flare.Utils.ItemPriceUtils;
import ananaseke.flare.Utils.ItemUtils;
import net.minecraft.component.DataComponentTypes;
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
        List<Text> outList = cir.getReturnValue();

//        minecraft:custom_data=>{enchantments:{infinite_quiver:6},id:"ENCHANTED_BOOK",timestamp:1720599363052L,uuid:"289a835d-1ddc-4930-a822-e7870411da23"}
//        "product_id":"ENCHANTMENT_INFINITE_QUIVER_10"

        if (optionalInternalName.isEmpty()) return;
        String internalName = optionalInternalName.get();

        if (internalName.equals("ENCHANTED_BOOK")) {
            if (ItemUtils.getEnchantments(stack).isPresent()) {
//                FlareClient.LOGGER.info("Enchantments: " + ItemUtils.getEnchantments(stack).get());
            }
        }

        ItemPriceUtils.getBazaarItemBuyPrice(internalName).ifPresent(price -> {
            float newPrice = price;
            if (price >= 100) {
                newPrice = ((float) price.intValue());
            }
            String formattedPrice = NumberFormat.getNumberInstance(Locale.ENGLISH).format(newPrice);
            Text completed = Text.of("§5Buy Price: §6" + formattedPrice + " coins");
            outList.add(completed);
        });

        ItemPriceUtils.getBazaarItemSellPrice(internalName).ifPresent(price -> {
            float newPrice = price;
            if (price >= 100) {
                newPrice = ((float) price.intValue());
            }
            String formattedPrice = NumberFormat.getNumberInstance(Locale.ENGLISH).format(newPrice);
            Text completed = Text.of("§5Buy Price: §6" + formattedPrice + " coins");
            outList.add(completed);
        });


//
//        optionalInternalName.ifPresent(internalName -> {
//            ItemPriceUtils.getBazaarItemBuyPrice(internalName).ifPresent(price -> {
//                String formattedPrice = NumberFormat.getNumberInstance(Locale.GERMAN).format(price);
//                Text completed = Text.of("§6Buy Price: " + formattedPrice);
//                outList.add(completed);
//            });
//        });
//        optionalInternalName.ifPresent(internalName -> {
//            ItemPriceUtils.getBazaarItemSellPrice(internalName).ifPresent(price -> {
//                String formattedPrice = NumberFormat.getNumberInstance(Locale.GERMAN).format(price);
//                Text completed = Text.of("§6Sell Price: " + formattedPrice);
//                outList.add(completed);
//            });
//        });
//
//        if (KeyBinds.showMoreItemInfoKeybind.isPressed()) {
//            optionalInternalName.ifPresent(internalName -> {
//                ItemPriceUtils.getBazaarItemQuickBuyPrice(internalName).ifPresent(price -> {
//                    String formattedPrice = NumberFormat.getNumberInstance(Locale.GERMAN).format(price);
//                    Text completed = Text.of("§6Quick Buy Price: " + formattedPrice);
//                    outList.add(completed);
//                });
//            });
//            optionalInternalName.ifPresent(internalName -> {
//                ItemPriceUtils.getBazaarItemQuickSellPrice(internalName).ifPresent(price -> {
//                    String formattedPrice = NumberFormat.getNumberInstance(Locale.GERMAN).format(price);
//                    Text completed = Text.of("§6Quick Sell Price: " + formattedPrice);
//                    outList.add(completed);
//                });
//            });
//        }

        cir.setReturnValue(outList);
    }
}
