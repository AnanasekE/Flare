package ananaseke.flare.overlays;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.client.player.ClientPlayerBlockBreakEvents;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class ItemOverlays {

    public static TimedOverlay JungleAxeOverlay = new TimedOverlay();
    public static int screenHeight;
    public static int screenWidth;

    public static void initialize(){
        jungleAxe();
    }

    private static void jungleAxe() {
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

        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> {
            if (JungleAxeOverlay.shouldRender()) {
                screenHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();
                screenWidth = MinecraftClient.getInstance().getWindow().getScaledWidth();
                int centerX = screenWidth / 2;
                int centerY = screenHeight / 2;
                int maxHeight = 25;
                int width = 5;
                float progress = JungleAxeOverlay.getTimeLeftPercentage();
                int height = (int) (maxHeight * progress);
                int x = centerX + 10;
                int y = centerY - (height / 2);
                drawContext.fill(x, y, x + width, y + height, 0x80FF0000);
            }
        });
    }
}
