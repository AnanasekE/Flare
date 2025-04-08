package ananaseke.flare.dungeons.solvers.terminals;

import ananaseke.flare.Utils.RenderUtils;
import ananaseke.flare.callbacks.DrawSlotCallback;
import ananaseke.flare.callbacks.OnSlotStackPickup;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorTerm {
    private static MinecraftClient client = MinecraftClient.getInstance();
    private static List<Slot> slotsToHighlight = new ArrayList<>();
    private static Map<String, DyeColor> colors;
    private static Map<Item, DyeColor> itemColors;


    public static void initialize() {
        loadColors();

        DrawSlotCallback.EVENT.register((drawContext, slot) -> {
            if (slotsToHighlight.isEmpty()) return;
            if (!(client.currentScreen instanceof GenericContainerScreen)) return;
            if (slotsToHighlight.stream().anyMatch(slot1 -> slot1.id == slot.id)) {
                RenderUtils.highlightSlot(drawContext, slot);
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register((client) -> {
            if (client.currentScreen == null) {
                if (!slotsToHighlight.isEmpty()) {
                    slotsToHighlight.clear();
                }
                return;
            }
            if (!(client.currentScreen instanceof GenericContainerScreen screen)) return;


            Pattern pattern = Pattern.compile("^Select all the ([A-Z ]+) items!$");
            Matcher matcher = pattern.matcher(client.currentScreen.getTitle().getString());

            if (matcher.matches()) {
                String color = matcher.group(1);
                DyeColor target = colors.get(color);
                if (target != null) {

                    GenericContainerScreenHandler handler = screen.getScreenHandler();
                    for (Slot slot : handler.slots) {
                        if (target.equals(itemColors.get(slot.getStack().getItem())) &&
                                !slot.getStack().hasGlint() &&
                                slotsToHighlight.stream().noneMatch(slot1 -> slot1.id == slot.id)) {
                            slotsToHighlight.add(slot);
                        }
                    }
                }
            }
        });

        OnSlotStackPickup.EVENT.register(slotId -> {
            if (slotsToHighlight.isEmpty()) return;
            slotsToHighlight.removeIf(slot -> slot.id == slotId);
        });
    }

    private static void loadColors() {
        colors = new HashMap<>();
        itemColors = new HashMap<>();

        for (DyeColor color : DyeColor.values()) {
            colors.put(color.getName().toUpperCase(Locale.ENGLISH), color);
        }
        colors.put("SILVER", DyeColor.LIGHT_GRAY);
        colors.put("LIGHT BLUE", DyeColor.LIGHT_BLUE);


        for (DyeColor color : DyeColor.values()) {
            for (String item : new String[]{"dye", "wool", "stained_glass", "terracotta"}) {
                itemColors.put(Registries.ITEM.get(Identifier.ofVanilla(color.getName() + "_" + item)), color);
                itemColors.put(Items.BONE_MEAL, DyeColor.WHITE);
                itemColors.put(Items.LAPIS_LAZULI, DyeColor.BLUE);
                itemColors.put(Items.COCOA_BEANS, DyeColor.BROWN);
                itemColors.put(Items.INK_SAC, DyeColor.BLACK);
            }
        }
    }
}
