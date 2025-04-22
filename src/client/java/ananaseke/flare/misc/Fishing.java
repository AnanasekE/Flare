package ananaseke.flare.misc;

import ananaseke.flare.Config;
import ananaseke.flare.Utils.Utils;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.Entity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

import java.awt.*;
import java.util.Objects;


public class Fishing {
    String timer;
    Config config;

    public Fishing() {
        config = AutoConfig.getConfigHolder(Config.class).getConfig();

        ClientTickEvents.END_CLIENT_TICK.register(this::onEndTick);

        HudRenderCallback.EVENT.register(this::onHudRender);
    }

    private void onEndTick(MinecraftClient client) {
        if (client.world == null) return;
        if (client.player == null) return;
        if (!(client.player.getMainHandStack().getItem() instanceof FishingRodItem)) {
            if (timer != null) timer = null;
            return;
        }

        for (Entity entity : client.world.getEntities()) {
            Utils.TestRegexResult result = Utils.testRegex(entity.getName().getString(), "^([0-9]\\.[0-9]|!!!)$");
            if (result.isMatching) {
                assert !result.groups.isEmpty();
                timer = result.groups.getFirst();
                if (timer.equals("!!!")) {
                    playCatchNotificationSound(client, config.catchFishNotificationSoundVolume);
                }
                return;
            }
            timer = null;
        }
    }

    private void onHudRender(DrawContext drawContext, RenderTickCounter tickCounter) {
        if (timer == null) return;

        float scaleModifier = 2F;

        drawContext.getMatrices().push();
        drawContext.getMatrices().scale(scaleModifier, scaleModifier, 1F);

        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer textRenderer = client.textRenderer;
        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();
        int x = (int) (width / (2 * scaleModifier) - (float) textRenderer.getWidth(timer) / 2);
        int y = (int) (height / (2 * scaleModifier) - (float) textRenderer.fontHeight / 2 + 10);

        Color color = Color.WHITE;
        if (Objects.equals(timer, "!!!")) {
            color = new Color(234, 85, 85, 255);
        }

        drawContext.drawText(textRenderer, timer, x, y, color.getRGB(), true);
        drawContext.getMatrices().pop();
    }

    private void playCatchNotificationSound(MinecraftClient client, float volume) {
        if (client.world == null) return;
        if (client.player == null) return;
        client.world.playSound(client.player, client.player.getBlockPos(), SoundEvents.BLOCK_NOTE_BLOCK_BELL.value(), SoundCategory.BLOCKS, volume, 1F);
    }
}
