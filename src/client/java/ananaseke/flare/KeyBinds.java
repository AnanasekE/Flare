package ananaseke.flare;

import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;

public class KeyBinds {


    // KeyBindings
    public static KeyBinding showMoreItemInfoKeybind;
    public static KeyBinding devKeybind;
    public static KeyBinding openEChestKeybind;
    public static KeyBinding openPetMenu;
    public static KeyBinding highlightEntitiesGlow;
    public static KeyBinding highlightEntitiesBox;
    public static KeyBinding highlightEntitiesColor;
    public static KeyBinding fragRunModeKeybind;
    public static KeyBinding openConfigScreen;


    private static final Map<KeyBinding, ToggleOption> toggles = new HashMap<>();

    private record ToggleOption(String name, boolean state) {
        ToggleOption toggle() {
            return new ToggleOption(this.name, !this.state);
        }
    }

    public static boolean isToggleActive(KeyBinding keyBinding) {
        return toggles.getOrDefault(keyBinding, new ToggleOption(keyBinding.toString() + "Toggle", false)).state();
    }

    public static void initialize() {
        registerKeyBindings();
        registerToggles();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            // Process toggle keys
            toggles.keySet().forEach(keyBinding -> {
                if (keyBinding.wasPressed()) {
                    ToggleOption option = toggles.get(keyBinding).toggle();
                    toggles.put(keyBinding, option);
                    if (client.player == null) return;
                    client.player.sendMessage(Text.of("Changed " + option.name + " to " + option.state()));
                }
            });

            processKey(openConfigScreen, () -> {
                Screen configScreen = AutoConfig.getConfigScreen(Config.class, client.currentScreen).get();
                client.setScreen(configScreen);
            });

            if (client.player == null) return;
            processKey(openEChestKeybind, () -> client.player.networkHandler.sendCommand("ec"));
            processKey(openPetMenu, () -> client.player.networkHandler.sendCommand("pets"));

        });
    }

    private static void registerKeyBindings() {
        showMoreItemInfoKeybind = register("show_more_item_info", GLFW.GLFW_KEY_LEFT_SHIFT);
        devKeybind = register("dev", GLFW.GLFW_KEY_GRAVE_ACCENT);
        openEChestKeybind = register("open_ec", GLFW.GLFW_KEY_B);
        openPetMenu = register("open_pet_menu", GLFW.GLFW_KEY_N);
        highlightEntitiesGlow = register("highlight_entities_glow", GLFW.GLFW_KEY_HOME);
        highlightEntitiesBox = register("highlight_entities", GLFW.GLFW_KEY_HOME);
        highlightEntitiesColor = register("highlight_entities_color", GLFW.GLFW_KEY_MINUS);
        openConfigScreen = register("open_config_menu", GLFW.GLFW_KEY_H);
        fragRunModeKeybind = register("fragRunMode", GLFW.GLFW_KEY_END);
    }

    private static void registerToggles() {
//        toggles.put(devKeybind, new ToggleOption("devKeybindToggle", false));
        toggles.put(highlightEntitiesBox, new ToggleOption("highlightEntitiesBoxToggle", false));
        toggles.put(highlightEntitiesGlow, new ToggleOption("highlightEntitiesGlowToggle", false));
        toggles.put(highlightEntitiesColor, new ToggleOption("highlightEntitiesColorToggle", false));
    }

    private static KeyBinding register(String name, int keyCode) {
        return KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.flare." + name,
                InputUtil.Type.KEYSYM,
                keyCode,
                "category.flare.main"
        ));
    }

    public static void processKey(KeyBinding keyBinding, Runnable action) {
        while (keyBinding.wasPressed()) {
            FlareClient.LOGGER.info("Clicked: {}", keyBinding);
            action.run();
        }
    }
}
