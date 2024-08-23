package ananaseke.flare.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface DungeonStartCallback {
    Event<DungeonStartCallback> EVENT = EventFactory.createArrayBacked(DungeonStartCallback.class,
            (listeners) -> () -> {
                for (DungeonStartCallback listener : listeners) {
                    listener.onDungeonStarted();
                }
            });
    void onDungeonStarted();
}