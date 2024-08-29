package ananaseke.flare.dungeons.solvers.terminals;

import ananaseke.flare.FlareClient;
import ananaseke.flare.KeyBinds;
import ananaseke.flare.Utils.RenderUtils;
import ananaseke.flare.Utils.Utils;
import ananaseke.flare.callbacks.DrawSlotCallback;
import ananaseke.flare.callbacks.OnSlotStackPickup;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.render.*;
import net.minecraft.item.AirBlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.Slot;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Terminals {
    static boolean isInGoldor = false;
    static List<Integer> slotsToHighlight = new ArrayList<>();

    public static void initialize() {
        StartsWithSequenceSolver.initialize();

        DrawSlotCallback.EVENT.register((drawContext, slot) -> {
            if (slotsToHighlight.isEmpty()) return;
            MinecraftClient client = MinecraftClient.getInstance();
            if (!(client.currentScreen instanceof GenericContainerScreen)) return;

            if (slotsToHighlight.stream().noneMatch(integer -> slot.id == integer)) return;
//            drawContext.fill(RenderLayer.getGui(), slot.x, slot.y, slot.x + 16, slot.y + 16, 0, 0x8000FF00); // 0x8000FF00
            RenderUtils.highlightSlot(drawContext, slot);
        });


        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.getServer() == null) return;
            client.getServer().getBossBarManager().getAll().forEach(commandBossBar -> isInGoldor = commandBossBar.getName().getString().contains("Goldor"));
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!preChestChecks(client, "Select all the")) return;
            if (!(client.currentScreen instanceof GenericContainerScreen screen)) return;
            GenericContainerScreenHandler handler = screen.getScreenHandler();

            Pattern pattern = Pattern.compile("Select all the ([A-Za-z_]+) items!");
            Matcher matcher = pattern.matcher(screen.getTitle().getString());

            if (matcher.find()) {
                String color = matcher.group(1);
                FlareClient.LOGGER.info("Color: " + color);

                for (Slot slot : handler.slots) {
                    ItemStack stack = slot.getStack();
                    if (stack.getItem() instanceof AirBlockItem) continue;
//                    String modifiedName = stack.getName().getString().toUpperCase().replaceAll(" ", "_");
                    String modifiedName = stack.getName().getString().toUpperCase();
                    FlareClient.LOGGER.info("Item name: " + modifiedName);
                    modifiedName = Utils.removeColorTags(modifiedName);
                    if (modifiedName.contains(color)) {
                        slotsToHighlight.add(slot.getIndex());
                    }
                }
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!preChestChecks(client, "What starts with:")) return;

            GenericContainerScreen screen = (GenericContainerScreen) client.currentScreen;
            if (screen == null) return;
            GenericContainerScreenHandler handler = screen.getScreenHandler();

            Pattern pattern = Pattern.compile("What starts with: '([A-Z])'\\?");
            Matcher matcher = pattern.matcher(client.currentScreen.getTitle().getString());

            if (matcher.find()) {
                String letter = matcher.group(1);
                FlareClient.LOGGER.info(letter);
                for (Slot slot : handler.slots) {
                    ItemStack stack = slot.getStack();
                    String modifiedName = Utils.removeColorTags(stack.getName().getString());
                    if (modifiedName.startsWith(letter)) {
                        slotsToHighlight.add(slot.id);
                    }
                }
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (slotsToHighlight.isEmpty()) return;
            if (client.currentScreen instanceof GenericContainerScreen) return;
            slotsToHighlight.clear();
        });

        OnSlotStackPickup.EVENT.register(slotId -> {
            if (slotsToHighlight.isEmpty()) return;
            slotsToHighlight.removeIf(id -> slotId == id);

        });

    }

    private static boolean preChestChecks(MinecraftClient client, String startsWith) {
        if (!isInGoldor) return false;
        if (client.player == null) return false;
        if (client.currentScreen == null) return false;
        if (!client.currentScreen.getTitle().getString().startsWith(startsWith)) return false;
        if (!(client.currentScreen instanceof GenericContainerScreen)) return false;
        return true;
    }
}
