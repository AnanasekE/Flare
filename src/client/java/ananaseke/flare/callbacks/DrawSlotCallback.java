package ananaseke.flare.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.DrawContext;

public interface DrawSlotCallback {
    Event<DrawSlotCallback> EVENT = EventFactory.createArrayBacked(DrawSlotCallback.class,
            (listeners) -> (drawContext) -> {
                for (DrawSlotCallback listener : listeners) {
                    listener.drawSlot(drawContext);
                }
            });
    void drawSlot(DrawContext drawContext);
}
