package ananaseke.flare.dungeons;

import ananaseke.flare.Utils.RenderUtils;
import ananaseke.flare.callbacks.DrawSlotCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.Slot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Croesus {
    private static List<Slot> slotsToHighlight = new ArrayList<>();

    public static void initialize() { // FIXME later, dosent render background or slotstohighlight problem
        DrawSlotCallback.EVENT.register((drawContext, slot) -> {
            if (slotsToHighlight.isEmpty()) return;
            MinecraftClient client = MinecraftClient.getInstance();
            if (!(client.currentScreen instanceof GenericContainerScreen)) return;
            if (slotsToHighlight.stream().noneMatch(slot1 -> slot1.id == slot.id)) return;
            RenderUtils.highlightSlot(drawContext, slot);
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.currentScreen == null) return;
            if (!client.currentScreen.getTitle().getString().contains("Croesus")) return;
            GenericContainerScreen screen = (GenericContainerScreen) client.currentScreen;
            GenericContainerScreenHandler screenHandler = screen.getScreenHandler();
//            screenHandler.slots.stream().filter(slot -> slot.getStack().getItem().getComponents().get(DataComponentTypes.LORE).lines().stream().anyMatch(text -> text.getString().contains("No Chests Opened!"))).toList();
//            slotsToHighlight.addAll(screenHandler.slots.stream()
//                    .filter(slot -> slot.getStack().getItem().getComponents().get(DataComponentTypes.LORE) != null)
//                    .filter(slot -> slot.getStack().getItem().getComponents().get(DataComponentTypes.LORE).lines().stream()
//                            .anyMatch(text -> text.getString().contains("No Chests Opened!"))).toList());

            for (Slot slot : screenHandler.slots) {
                if (slotsToHighlight.stream().anyMatch(slot1 -> slot1.id == slot.id)) continue;
                LoreComponent loreComponent = slot.getStack().getItem().getComponents().get(DataComponentTypes.LORE);
                if (loreComponent == null) continue;
                if (loreComponent.lines().stream().noneMatch(text -> text.getString().contains("No Chests Opened!"))) {
                    continue;
                }

                slotsToHighlight.add(slot);
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (slotsToHighlight.isEmpty()) return;
            if (client.currentScreen instanceof GenericContainerScreen) return;
            slotsToHighlight.clear();
        });
    }
}
