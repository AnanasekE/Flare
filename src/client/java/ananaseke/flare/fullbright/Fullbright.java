package ananaseke.flare.fullbright;

import ananaseke.flare.Config;
import ananaseke.flare.Flare;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;


public class Fullbright {

    public static Logger LOGGER = Flare.LOGGER;
//    public static KeyBinding keyBinding;
//    public static Boolean isFullbrightEnabled = false;
    public static double defaultBrightness;


    public static void initialize() {
        Config config = AutoConfig.getConfigHolder(Config.class).getConfig();

//        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
//                "key.fullbright.toggle",
//                InputUtil.Type.KEYSYM,
//                GLFW.GLFW_KEY_G,
//                "category.flare.main"
//        ));

        ClientLifecycleEvents.CLIENT_STARTED.register(client ->
                defaultBrightness = MinecraftClient.getInstance().options.getGamma().getValue());


        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (config.fullbright) {
                if (MinecraftClient.getInstance().options.getGamma().getValue() != 100.0) {
                    MinecraftClient.getInstance().options.getGamma().setValue(100.0);
                }
            } else {
                if (MinecraftClient.getInstance().options.getGamma().getValue() != defaultBrightness) {
                    MinecraftClient.getInstance().options.getGamma().setValue(defaultBrightness);
                }
            }

//            while (keyBinding.wasPressed()) {
//                LOGGER.info("Key binding pressed, isFullbrightEnabled: " + isFullbrightEnabled);
//                if (isFullbrightEnabled) {
//                    LOGGER.info("Disabling fullbright");
//                    MinecraftClient.getInstance().options.getGamma().setValue(defaultBrightness);
//                    MinecraftClient.getInstance().inGameHud.setOverlayMessage(Text.of("Disabled Fullbright"), false);
//                    isFullbrightEnabled = false;
//                } else {
//                    LOGGER.info("Enabling fullbright");
//                    MinecraftClient.getInstance().options.getGamma().setValue(100.0);
//                    MinecraftClient.getInstance().inGameHud.setOverlayMessage(Text.of("Enabled Fullbright"), false);
//                    isFullbrightEnabled = true;
//                }
//            }
        });
    }
}
