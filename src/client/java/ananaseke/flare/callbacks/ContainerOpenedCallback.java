package ananaseke.flare.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface ContainerOpenedCallback {
    Event<ContainerOpenedCallback> EVENT = EventFactory.createArrayBacked(ContainerOpenedCallback.class,
            (listeners) -> () -> {
                for (ContainerOpenedCallback listener : listeners) {
                    listener.openInventory();
                }
            });

    void openInventory();
}