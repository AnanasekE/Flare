package ananaseke.flare.misc.experiments;

import ananaseke.flare.FlareClient;
import ananaseke.flare.KeyBinds;
import ananaseke.flare.misc.highlight.InventoryTextureDrawer;
import ananaseke.flare.misc.highlight.TextureHighlightEntry;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Superpairs extends InventoryTextureDrawer {

    private final List<TextureHighlightEntry> records = new ArrayList<>();

    private final List<Item> ignoredItems = List.of(
            Items.GRAY_STAINED_GLASS_PANE,
            Items.CYAN_STAINED_GLASS,
            Items.LIGHT_GRAY_STAINED_GLASS_PANE,
            Items.BOOKSHELF,
            Items.CLOCK,
            Items.AIR
    );

    public Superpairs() {
        super("^Superpairs \\(.+\\)$", 0);


        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (KeyBinds.isToggleActive(KeyBinds.devKeybind) && !records.isEmpty()) {
                FlareClient.LOGGER.info(records.getFirst().stack().toString());
            }

            if (client.currentScreen == null && !records.isEmpty()) {
                records.clear();
                FlareClient.LOGGER.info("Clearing registered slots");
            }
            if (client.currentScreen == null) return;
            if (!(client.currentScreen instanceof GenericContainerScreen)) return;
            DefaultedList<Slot> allSlots = ((GenericContainerScreen) (client.currentScreen)).getScreenHandler().slots;
            Optional<Slot> maybeSlot = allSlots.stream().filter(slot -> slot.getStack().getName().getString().matches("Remaining Clicks: .+")).findFirst();
            if (maybeSlot.isPresent()) {
                List<TextureHighlightEntry> unregisteredSlots = allSlots.stream()
                        .map(slot -> new TextureHighlightEntry(slot, slot.getStack().copy()))
                        .filter(entry -> !ignoredItems.contains(entry.stack().getItem()))
                        .filter(entry -> !isInRecords(entry))
                        .toList();

                if (!unregisteredSlots.isEmpty() && KeyBinds.isToggleActive(KeyBinds.devKeybind))
                    FlareClient.LOGGER.info("Adding slots: {}", unregisteredSlots.getFirst());
                records.addAll(unregisteredSlots);
            }
        });
    }


    @Override
    protected List<TextureHighlightEntry> getHighlightedEntries(DefaultedList<Slot> slots) {
        return records;
    }

    private boolean isInRecords(TextureHighlightEntry entry) {
        for (TextureHighlightEntry record : records) {
            if (record.slot().id == entry.slot().id && ItemStack.areEqual(record.stack(), entry.stack())) {
                return true;
            }
        }
        return false;
    }
}
