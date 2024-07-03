package ananaseke.flare;

import ananaseke.flare.Plaques.Plaques;
import ananaseke.flare.fullbright.Fullbright;
import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
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
    public static Integer jungleAxeTimer = 0;

    @Override
    public void onInitializeClient() {
        Plaques.initialize();
        Fullbright.initialize();

        ClientPlayerBlockBreakEvents.AFTER.register((world, player, pos, state) -> {
            assert MinecraftClient.getInstance().world != null;
            BlockState oldState = MinecraftClient.getInstance().world.getBlockState(pos);
            if (oldState.getBlock() != state.getBlock()) {
                if (player.getMainHandStack().getName().getString().contains("Jungle Axe")) {
                    LOGGER.info("Jungle axe mine detected");
                    jungleAxeTimer = 40; // 2s - ignores pet for now
                }
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (jungleAxeTimer > 0) {
                jungleAxeTimer--;
            }
        });


    }
}
