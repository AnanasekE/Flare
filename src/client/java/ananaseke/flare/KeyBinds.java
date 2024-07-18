package ananaseke.flare;

import ananaseke.flare.Utils.RenderUtils;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

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


    public static void initialize() {
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
        });

    }
}
