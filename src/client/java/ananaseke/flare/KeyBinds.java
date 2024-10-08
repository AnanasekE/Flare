package ananaseke.flare;

import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class KeyBinds {
    public static KeyBinding showMoreItemInfoKeybind;
    public static KeyBinding devKeybind;
    public static boolean devKeybindToggle;
    public static KeyBinding openEChestKeybind;
    public static KeyBinding openPetMenu;
    public static KeyBinding highlightEntitesGlow;
    public static boolean highlightEntitesGlowToggle;
    public static KeyBinding highlightEntitiesBox;
    public static boolean highlightEntitiesBoxToggle;
    public static KeyBinding highlightEntitiesColor;
    public static boolean highlightEntitiesColorToggle;
    public static KeyBinding fragRunModeKeybind;

    public static KeyBinding openConfigScreen;


    public static void initialize() {
        registerBinds();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (devKeybind.wasPressed()) {
                devKeybindToggle = !devKeybindToggle;
                client.player.sendMessage(Text.of("Changed devKeybindToggle to " + devKeybindToggle), false);
            }

            if (highlightEntitiesBox.wasPressed()) {
                highlightEntitiesBoxToggle = !highlightEntitiesBoxToggle;
                client.player.sendMessage(Text.of("Changed highlightEntitiesBoxToggle to " + highlightEntitiesBoxToggle), false);
            }

            if (highlightEntitesGlow.wasPressed()) {
                highlightEntitesGlowToggle = !highlightEntitesGlowToggle;
                client.player.sendMessage(Text.of("Changed highlightEntitesGlowToggle to " + highlightEntitesGlowToggle), false);
            }

            if (highlightEntitiesColor.wasPressed()) {
                highlightEntitiesColorToggle = !highlightEntitiesColorToggle;
                client.player.sendMessage(Text.of("Changed highlightEntitesColorToggle to " + highlightEntitiesColorToggle), false);
            }


            while (openConfigScreen.wasPressed()) {
                Screen configScreen = AutoConfig.getConfigScreen(Config.class, MinecraftClient.getInstance().currentScreen).get();
                MinecraftClient.getInstance().setScreen(configScreen);
            }

            if (openEChestKeybind.wasPressed()) {
                if (MinecraftClient.getInstance().player == null) return;
                MinecraftClient.getInstance().player.networkHandler.sendCommand("ec");
            }
            if (openPetMenu.wasPressed()) {
                if (MinecraftClient.getInstance().player == null) return;
                MinecraftClient.getInstance().player.networkHandler.sendCommand("pets");
            }
        });

    }

    private static void registerBinds() {
        showMoreItemInfoKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.flare.show_more_item_info",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_SHIFT,
                "category.flare.main"
        ));

        devKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.flare.dev",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_GRAVE_ACCENT,
                "category.flare.main"
        ));

        openEChestKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.flare.open_ec",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_B,
                "category.flare.main"
        ));

        openPetMenu = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.flare.open_pet_menu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_N,
                "category.flare.main"
        ));

        highlightEntitesGlow = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.flare.highlight_entities_glow",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_HOME,
                "category.flare.main"
        ));

        highlightEntitiesBox = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.flare.highlight_entities",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_HOME,
                "category.flare.main"
        ));

        highlightEntitiesColor = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.flare.highlight_entities_color",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_MINUS,
                "category.flare.main"
        ));

        openConfigScreen = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.flare.open_config_menu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_H,
                "category.flare.main"
        ));

        fragRunModeKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.flare.fragRunMode",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_END,
                "category.flare.main"
        ));
    }
}
