package ananaseke.flare;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyBinds {
    public static KeyBinding showMoreItemInfoKeybind;

    public static void initialize() {
        showMoreItemInfoKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.flare.show_more_item_info",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_SHIFT,
                "category.flare.main"
        ));
    }
}
