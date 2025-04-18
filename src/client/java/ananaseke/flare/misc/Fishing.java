package ananaseke.flare.misc;

import ananaseke.flare.FlareClient;
import ananaseke.flare.Utils.RenderUtils;
import ananaseke.flare.Utils.Utils;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.Entity;
import net.minecraft.item.FishingRodItem;


public class Fishing {
    String timer;

    public Fishing() {
        ClientTickEvents.END_CLIENT_TICK.register(this::onEndTick);

        HudRenderCallback.EVENT.register(this::onHudRender);
    }

    private void onEndTick(MinecraftClient client) {
        if (client.world == null) return;
        if (client.player == null) return;
        if (!(client.player.getMainHandStack().getItem() instanceof FishingRodItem)) return;

        for (Entity entity : client.world.getEntities()) {
            Utils.TestRegexResult result = Utils.testRegex(entity.getName().getString(), "^([0-9]\\.[0-9]|!!!)$");
            if (result.isMatching) {
                assert !result.groups.isEmpty();
                timer = result.groups.getFirst();
                return;
            }
            timer = null;
        }
    }

    private void onHudRender(DrawContext drawContext, RenderTickCounter tickCounter) {
        if (timer == null) return;
        RenderUtils.drawCenteredText(timer, 10, drawContext);
    }
}
