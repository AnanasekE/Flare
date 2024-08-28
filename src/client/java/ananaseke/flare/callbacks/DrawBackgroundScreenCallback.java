package ananaseke.flare.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.DrawContext;

public interface DrawBackgroundScreenCallback {
    Event<DrawBackgroundScreenCallback> EVENT = EventFactory.createArrayBacked(DrawBackgroundScreenCallback.class,
            (listeners) -> (drawContext) -> {
                for (DrawBackgroundScreenCallback listener : listeners) {
                    listener.drawBackground(drawContext);
                }
            });
    void drawBackground(DrawContext drawContext);
}
