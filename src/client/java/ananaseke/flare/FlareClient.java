package ananaseke.flare;

import ananaseke.flare.Plaques.Plaques;
import ananaseke.flare.fullbright.Fullbright;
import ananaseke.flare.overlays.TimedOverlay;
import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.client.player.ClientPlayerBlockBreakEvents;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class FlareClient implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("flare");
    public static final String MOD_ID = "flare";

    public static TimedOverlay JungleAxeOverlay = new TimedOverlay();
    public static int screenHeight;
    public static int screenWidth;


    @Override
    public void onInitializeClient() {
        Plaques.initialize();
        Fullbright.initialize();


        ClientPlayerBlockBreakEvents.AFTER.register((world, player, pos, state) -> {
            assert MinecraftClient.getInstance().world != null;
            BlockState oldState = MinecraftClient.getInstance().world.getBlockState(pos);
            if (oldState.getBlock() != state.getBlock()) {
                if (player.getMainHandStack().getName().getString().contains("Jungle Axe") && !JungleAxeOverlay.shouldRender()) {
                    LOGGER.info("Jungle axe mine detected");
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
