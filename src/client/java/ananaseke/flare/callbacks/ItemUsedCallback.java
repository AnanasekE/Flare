package ananaseke.flare.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;

public interface ItemUsedCallback {
    Event<ItemUsedCallback> EVENT = EventFactory.createArrayBacked(ItemUsedCallback.class,
            (listeners) -> (stack) -> {
                for (ItemUsedCallback listener : listeners) {
                    listener.onItemUsed(stack);
                }
            });
    void onItemUsed(ItemStack stack);
}
