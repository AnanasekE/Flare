package ananaseke.flare.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.screen.slot.Slot;

public interface DrawSlotCallback {
    Event<DrawSlotCallback> EVENT = EventFactory.createArrayBacked(DrawSlotCallback.class,
            (listeners) -> (drawContext, slot) -> {
                for (DrawSlotCallback listener : listeners) {
                    listener.drawSlot(drawContext, slot);
                }
            });
    void drawSlot(DrawContext drawContext, Slot slot);
}
