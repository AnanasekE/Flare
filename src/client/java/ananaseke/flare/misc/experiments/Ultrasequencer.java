package ananaseke.flare.misc.experiments;

import ananaseke.flare.callbacks.OnSlotStackPickup;
import ananaseke.flare.misc.SimpleColorSolver;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.item.DyeItem;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Ultrasequencer extends SimpleColorSolver {
    private List<Integer> pickedUpSlotIds = new ArrayList<>();
    private List<Integer> patternSlotIds = new ArrayList<>();

    public Ultrasequencer() {
        super("Ultrasequencer \\(.+\\)");
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.currentScreen == null) return;
            if (!(client.currentScreen instanceof GenericContainerScreen)) return;
            if (client.player == null) return;
            DefaultedList<Slot> allSlots = ((GenericContainerScreen) (client.currentScreen)).getScreenHandler().slots;
            Optional<Slot> maybeSlot = allSlots.stream().filter(slotId -> slotId.getStack().getName().getString().matches("Remember the pattern!")).findFirst();
            if (maybeSlot.isPresent()) {
//                client.player.sendMessage(Text.of("TEST1"));
                patternSlotIds = allSlots.stream()
                        .filter(slotId -> slotId.getStack().getItem() instanceof DyeItem)
                        .map(slotId -> slotId.id)
                        .collect(Collectors.toCollection(ArrayList::new));
                client.player.sendMessage(Text.of(String.valueOf(patternSlotIds.size())));
            } else {
//                client.player.sendMessage(Text.of("TEST2"));
                patternSlotIds.removeIf(slotId -> pickedUpSlotIds.contains(slotId));
            }
        });
// TODO: reset the pickedup id's when the maybeslot changes to exists from not exists or something idk
        OnSlotStackPickup.EVENT.register(slotId -> {
            if (!pickedUpSlotIds.contains(slotId)) {
                pickedUpSlotIds.add(slotId);
            }
//            if (MinecraftClient.getInstance().player == null) return;
//            MinecraftClient.getInstance().player.sendMessage(Text.of("TEST2"));
        });
    }

    @Override
    protected List<Slot> getHighlightedSlots(DefaultedList<Slot> slots) {
        return slots.stream().filter(slot -> pickedUpSlotIds.contains(slot.id)).collect(Collectors.toList());
    }
}
