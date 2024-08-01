package ananaseke.flare.garden;

import ananaseke.flare.FlareClient;
import ananaseke.flare.misc.VisitorItem;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class VisitorTracker {
    private static final List<VisitorItem> requiredItems = new ArrayList<>();

    public static List<VisitorItem> getRequiredItems() {
        return requiredItems;
    }

    public static void addRequiredItem(VisitorItem requiredItem) {
        requiredItems.add(requiredItem);
    }

    public static boolean hasVisitorName(String visitorName) {
        for (VisitorItem item : requiredItems) {
            if (item.getVisitorName().equals(visitorName)) {
                return true;
            }
        }
        return false;
    }

    public static void removeByVisitorName(String visitorName) {
        requiredItems.removeIf(item -> item.getVisitorName().equals(visitorName));
    }

    public static void clear() {
        requiredItems.clear();
    }

    public static void initialize() {
        KeyBinding keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.flare.clear_visitor_tracker",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_DELETE,
                "category.flare.main"
        ));
        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> {
            // Draw the visitor tracker
            while (keyBinding.wasPressed()) {
                clear();
            }
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null) return;
            if (requiredItems.isEmpty()) return;
            int y = 5;
            // draw transparent background
            drawContext.fill(5, y, 150, 5 + 10 * (requiredItems.size() + 1) + 10, new Color(0, 0, 0, 100).getRGB());
            y += 5;
            drawContext.drawText(client.textRenderer, "Required Items", 10, y, 0xFFFFFF, true);
            for (VisitorItem item : requiredItems) {
                y += 10;
                drawContext.drawText(client.textRenderer, item.getItemName() + " x" + item.getItemAmount(), 20, y, 0xFFFFFF, true);
            }
        });


        // not working, doesn't even trigger
        ClientReceiveMessageEvents.CHAT.register((message, signedMessage, sender, params, receptionTimestamp) -> {
            if (message.getLiteralString() == null) return;
            if (message.getLiteralString().contains("OFFER ACCEPTED with")) {
                String[] split = message.getString().split(" ");
                FlareClient.LOGGER.info(Arrays.stream(split).reduce((s1, s2) -> s1 + " " + s2).get());
                String visitorName = split[3];
                FlareClient.LOGGER.info("Offer accepted with " + visitorName);
                removeByVisitorName(visitorName);
            }
        });

//        OFFER ACCEPTED with Duke (UNCOMMON)
//        OFFER ACCEPTED with Gold Forger (RARE)
    }
}
