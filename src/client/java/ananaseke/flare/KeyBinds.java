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

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (devKeybind.wasPressed()) {
                devKeybindToggle = !devKeybindToggle;
                MinecraftClient.getInstance().player.sendMessage(Text.of("Changed devKeybindToggle to " + devKeybindToggle), false);
            }
        });

    }
}
