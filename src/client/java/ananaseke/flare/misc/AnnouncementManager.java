package ananaseke.flare.misc;

import ananaseke.flare.overlays.TimedOverlay;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

import static ananaseke.flare.Utils.RenderUtils.drawCenteredText;

public class AnnouncementManager {
    private static TimedOverlay timedOverlay = new TimedOverlay();

    public void initialize() {
        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> {
            if (timedOverlay.shouldRender()) {
                render(drawContext);
            }
        });
    }

    public static void announceToPlayer(String message, long timeMs) {
        timedOverlay.setMessage(Text.of(message), timeMs);
    }

    public static void render(DrawContext context) {
        if (timedOverlay.shouldRender()) {
            String message = timedOverlay.getMessage().getString();
            drawCenteredText(message, -50, context);
        }
    }

}
