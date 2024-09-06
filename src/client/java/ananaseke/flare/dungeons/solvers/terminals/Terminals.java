package ananaseke.flare.dungeons.solvers.terminals;

import ananaseke.flare.FlareClient;
import ananaseke.flare.Utils.RenderUtils;
import ananaseke.flare.Utils.Utils;
import ananaseke.flare.callbacks.DrawSlotCallback;
import ananaseke.flare.callbacks.OnSlotStackPickup;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
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
        ColorTerm.initialize();

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
            if (client.currentScreen == null) {
                if (!slotsToHighlight.isEmpty()) {
                    slotsToHighlight.clear();
                }
                return;
            }
            if (!(client.currentScreen instanceof GenericContainerScreen screen)) return;

            GenericContainerScreenHandler handler = screen.getScreenHandler();

            Pattern pattern = Pattern.compile("^What starts with: '([A-Z])'\\?$");
            Matcher matcher = pattern.matcher(client.currentScreen.getTitle().getString());

            if (matcher.matches()) {
                String letter = matcher.group(1);
                FlareClient.LOGGER.info(letter);
                for (Slot slot : handler.slots) {
                    ItemStack stack = slot.getStack();
                    String modifiedName = Utils.removeColorTags(stack.getName().getString());
                    if (modifiedName.startsWith(letter) && !slot.getStack().hasGlint()) {
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
}
