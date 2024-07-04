package ananaseke.flare;

import ananaseke.flare.Plaques.Plaques;
import ananaseke.flare.fullbright.Fullbright;
import ananaseke.flare.overlays.ItemOverlays;
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




    @Override
    public void onInitializeClient() {
        Plaques.initialize();
        Fullbright.initialize();
        ItemOverlays.initialize();


    }
}
