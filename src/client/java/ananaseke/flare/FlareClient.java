package ananaseke.flare;

import ananaseke.flare.Plaques.Plaques;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.packet.c2s.common.ClientOptionsC2SPacket;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlareClient implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("flare");
    public static final String MOD_ID = "flare";

    public static KeyBinding keyBinding;
    public static Boolean isFullbrightEnabled = false;
    public static double defaultBrightness;

    @Override
    public void onInitializeClient() {
        Plaques.initialize();


        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.fullbright.toggle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                "category.fullbright.main"
        ));

        ClientLifecycleEvents.CLIENT_STARTED.register(client ->
                defaultBrightness = MinecraftClient.getInstance().options.getGamma().getValue());


        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.wasPressed()) {
                LOGGER.info("Key binding pressed, isFullbrightEnabled: " + isFullbrightEnabled);
                if (isFullbrightEnabled) {
                    LOGGER.info("Disabling fullbright");
                    MinecraftClient.getInstance().options.getGamma().setValue(defaultBrightness);
                    MinecraftClient.getInstance().inGameHud.setOverlayMessage(Text.of("Disabled Fullbright"), false);
                    isFullbrightEnabled = false;
                } else {
                    LOGGER.info("Enabling fullbright");
                    MinecraftClient.getInstance().options.getGamma().setValue(100.0);
                    MinecraftClient.getInstance().inGameHud.setOverlayMessage(Text.of("Enabled Fullbright"), false);
                    isFullbrightEnabled = true;
                }
            }
        });

    }
}
