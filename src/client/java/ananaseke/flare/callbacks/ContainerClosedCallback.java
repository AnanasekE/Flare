package ananaseke.flare.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface ContainerClosedCallback {
    Event<ContainerClosedCallback> EVENT = EventFactory.createArrayBacked(ContainerClosedCallback.class,
            (listeners) -> () -> {
                for (ContainerClosedCallback listener : listeners) {
                    listener.closeInventory();
                }
            });

    void closeInventory();
}
