package ananaseke.flare.overlays;

import ananaseke.flare.KeyBinds;
import ananaseke.flare.callbacks.ItemUsedCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.client.player.ClientPlayerBlockBreakEvents;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class ItemOverlays {

    public static TimedOverlay JungleAxeOverlay = new TimedOverlay();
    public static TimedOverlay AgaricusCapOverlay = new TimedOverlay();
    public static TimedOverlay WandOfAtonement = new TimedOverlay();

    public static int screenHeight;
    public static int screenWidth;

    public static void initialize() {
        overlays();
    }

    private static void overlays() {
        ClientPlayerBlockBreakEvents.AFTER.register((world, player, pos, state) -> {
            assert MinecraftClient.getInstance().world != null;
            BlockState oldState = MinecraftClient.getInstance().world.getBlockState(pos);
            if (oldState.getBlock() != state.getBlock()) {
                if (player.getMainHandStack().getName().getString().contains("Jungle Axe") && !JungleAxeOverlay.shouldRender()) {
//                    LOGGER.info("Jungle axe mine detected");
                    JungleAxeOverlay.setMessage(Text.of(""), 2000);
                }
            }
        });

        ItemUsedCallback.EVENT.register(stack -> {
            if (stack.getName().getString().contains("Wand of Atonement")) {
                WandOfAtonement.setMessage(Text.of(""), 7000);
            }
        });

        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> {
            if (JungleAxeOverlay.shouldRender()) {
                renderTimedOverlay(drawContext, JungleAxeOverlay);
            }
            if (AgaricusCapOverlay.shouldRender()) {
                renderTimedOverlay(drawContext, AgaricusCapOverlay);
            }
            if (WandOfAtonement.shouldRender()) {
                renderTimedOverlay(drawContext, WandOfAtonement);
            }
        });
    }

    private static void renderTimedOverlay(DrawContext drawContext, TimedOverlay timedOverlay) {
        screenHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();
        screenWidth = MinecraftClient.getInstance().getWindow().getScaledWidth();
        int centerX = screenWidth / 2;
        int centerY = screenHeight / 2;
        int maxHeight = 25;
        int width = 5;
        float progress = timedOverlay.getTimeLeftPercentage();
        int height = (int) (maxHeight * progress);
        int x = centerX + 10;
        int y = centerY - (height / 2);
        drawContext.fill(x, y, x + width, y + height, 0x80FF0000);
    }
}
