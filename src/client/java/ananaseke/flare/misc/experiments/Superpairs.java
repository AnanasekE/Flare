package ananaseke.flare.misc.experiments;

import ananaseke.flare.FlareClient;
import ananaseke.flare.KeyBinds;
import ananaseke.flare.misc.SimpleColorSolver;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Superpairs extends SimpleColorSolver {

    private final List<Slot> registeredSlots = new ArrayList<>();

    private final List<Item> ignoredItems = List.of(
            Items.GRAY_STAINED_GLASS_PANE,
            Items.LIGHT_BLUE_STAINED_GLASS,
            Items.LIGHT_GRAY_STAINED_GLASS_PANE,
            Items.BOOKSHELF,
            Items.CLOCK,
            Items.AIR
    );

    public Superpairs() {
        super("^Superpairs \\(.+\\)$", 0, true);


        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (KeyBinds.isToggleActive(KeyBinds.devKeybind) && !registeredSlots.isEmpty()) {
                FlareClient.LOGGER.info(registeredSlots.getFirst().getStack().getItem().toString());
            }

            if (client.currentScreen == null && !registeredSlots.isEmpty()) {
                registeredSlots.clear();
                FlareClient.LOGGER.info("Clearing registered slots");
            }
            if (client.currentScreen == null) return;
            if (!(client.currentScreen instanceof GenericContainerScreen)) return;
            DefaultedList<Slot> allSlots = ((GenericContainerScreen) (client.currentScreen)).getScreenHandler().slots;
            Optional<Slot> maybeSlot = allSlots.stream().filter(slot -> slot.getStack().getName().getString().matches("Remaining clicks: .+")).findFirst();
            if (maybeSlot.isPresent()) {
                List<Slot> unregisteredSlots = allSlots.stream()
                        .filter(slot -> !ignoredItems.contains(slot.getStack().getItem()))
                        .filter(slot -> !registeredSlots.contains(slot))
                        .map(slot -> new Slot(slot.inventory, slot.id, slot.x, slot.y))
                        .toList();
                registeredSlots.addAll(unregisteredSlots);
            }
        });
    }

    @Override
    protected List<Slot> getHighlightedSlots(DefaultedList<Slot> slots) {
        return registeredSlots;
    }
}
