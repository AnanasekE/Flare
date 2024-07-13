package ananaseke.flare.mixin.client;

import ananaseke.flare.FlareClient;
import ananaseke.flare.garden.VisitorTracker;
import ananaseke.flare.misc.VisitorItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Mixin(ItemStack.class)

public class VisitorTooltipMixin {
    @Inject(method = "getTooltip", at = @At("RETURN"), cancellable = true)
    private void getTooltip(Item.TooltipContext context, @Nullable PlayerEntity player, TooltipType type, CallbackInfoReturnable<List<Text>> cir) {
        AtomicBoolean hasAcceptOffer = new AtomicBoolean(false);
        AtomicBoolean hasItemsRequired = new AtomicBoolean(false);
        AtomicBoolean hasMissingItems = new AtomicBoolean(false);

        ItemStack stack = (ItemStack) (Object) this; // Cast 'this' to ItemStack

        if (VisitorTracker.hasVisitorName(MinecraftClient.getInstance().currentScreen.getTitle().getString())) return;

        if (stack.getItem() == Items.GREEN_TERRACOTTA) {
//            FlareClient.LOGGER.info("Found Item");
            if (stack.getComponents().get(DataComponentTypes.LORE) != null) {
//                FlareClient.LOGGER.info("Found Lore");
                @Nullable LoreComponent loreComponent = stack.getComponents().get(DataComponentTypes.LORE);
                if (stack.getName().getString().contains("Accept Offer")) hasAcceptOffer.set(true);
                loreComponent.lines().forEach(text -> {
//                    FlareClient.LOGGER.info(text.getString());
                    if (text.getString().contains("Items Required:")) hasItemsRequired.set(true);
//                    if (text.getString().contains("Missing items to accept!")) hasMissingItems.set(true);
                });

//                FlareClient.LOGGER.info("Has Accept Offer: " + hasAcceptOffer.get());
//                FlareClient.LOGGER.info("Has Items Required: " + hasItemsRequired.get());
//                FlareClient.LOGGER.info("Has Missing Items: " + hasMissingItems.get());

                if (hasAcceptOffer.get() && hasItemsRequired.get()) {
                    try {
//                        FlareClient.LOGGER.info("Parsing Item Amount");
                        int endIndex = loreComponent.lines().get(2).getString().split(" ").length - 1;
//                        FlareClient.LOGGER.info("Endindex: " + endIndex);
//                        FlareClient.LOGGER.info("splitLore: " + Arrays.stream(loreComponent.lines().get(1).getString().split(" ")).toList().stream().reduce((s1, s2) -> s1 + " " + s2).get());

                        List<String> line3 = Arrays.stream(loreComponent.lines().get(1).getString().split(" ")).toList();
                        String itemName;
                        if (line3.subList(0, line3.size() - 2).stream().reduce((s1, s2) -> s1 + " " + s2).isPresent()) {
                            int itemAmount;
                            if (line3.get(line3.size() - 1).contains("x")) {
                                itemName = line3.subList(0, line3.size() - 1).stream().reduce((s1, s2) -> s1 + " " + s2).get();
                                itemAmount = Integer.parseInt(line3.get(line3.size() - 1).substring(1).replace(",", ""));
                            } else {
                                itemName = line3.stream().reduce((s1, s2) -> s1 + " " + s2).get();
                                itemAmount = 1;
                            }
                            VisitorTracker.addRequiredItem(new VisitorItem(itemName, itemAmount, MinecraftClient.getInstance().currentScreen.getTitle().getString()));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
