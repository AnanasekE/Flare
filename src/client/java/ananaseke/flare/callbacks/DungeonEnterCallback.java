package ananaseke.flare.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface DungeonEnterCallback {
    Event<DungeonEnterCallback> EVENT = EventFactory.createArrayBacked(DungeonEnterCallback.class,
            (listeners) -> (floor) -> {
                for (DungeonEnterCallback listener : listeners) {
                    listener.onDungeonEntered(floor);
                }
            });
    void onDungeonEntered(int floor);
}