package ananaseke.flare.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface OnSlotStackPickup {
    Event<OnSlotStackPickup> EVENT = EventFactory.createArrayBacked(OnSlotStackPickup.class,
            (listeners) -> (slotId) -> {
                for (OnSlotStackPickup listener : listeners) {
                    listener.onSlotStackPickup(slotId);
                }
            });
    void onSlotStackPickup(int slotId);
}