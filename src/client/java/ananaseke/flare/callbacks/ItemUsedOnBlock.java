package ananaseke.flare.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemUsageContext;

public interface ItemUsedOnBlock {
    Event<ItemUsedOnBlock> EVENT = EventFactory.createArrayBacked(ItemUsedOnBlock.class,
            (listeners) -> (context) -> {
                for (ItemUsedOnBlock listener : listeners) {
                    listener.onItemUsed(context);
                }
            });

    void onItemUsed(ItemUsageContext context);
}
