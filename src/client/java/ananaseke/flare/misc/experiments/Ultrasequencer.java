package ananaseke.flare.misc.experiments;

import ananaseke.flare.FlareClient;
import ananaseke.flare.callbacks.OnSlotStackPickup;
import ananaseke.flare.misc.SimpleColorSolver;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Ultrasequencer extends SimpleColorSolver {
    private List<Integer> patternSlotIds = new ArrayList<>();

    // Regular expression pattern to extract the first number from the item name
    private static final Pattern NUMBER_PATTERN = Pattern.compile("\\d+");

    public Ultrasequencer() {
        super("Ultrasequencer \\(.+\\)");

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.currentScreen == null) return;
            if (!(client.currentScreen instanceof GenericContainerScreen)) return;
            if (client.player == null) return;
            DefaultedList<Slot> allSlots = ((GenericContainerScreen) (client.currentScreen)).getScreenHandler().slots;
            Optional<Slot> maybeSlot = allSlots.stream().filter(slot -> slot.getStack().getName().getString().matches("Remember the pattern!")).findFirst();
            if (maybeSlot.isPresent()) {
                patternSlotIds = allSlots.stream().filter(slot -> slot.getStack().getItem() instanceof DyeItem || slot.getStack().getItem() instanceof BoneMealItem || slot.getStack().getItem() == Items.LAPIS_LAZULI).sorted((slot1, slot2) -> {
                    int num1 = extractNumberFromName(slot1.getStack().getName().getString());
                    int num2 = extractNumberFromName(slot2.getStack().getName().getString());
                    return Integer.compare(num1, num2);
                }).map(slot -> slot.id).collect(Collectors.toCollection(ArrayList::new));
            }
        });

        OnSlotStackPickup.EVENT.register(slotId -> {
            patternSlotIds.removeIf(slotId1 -> slotId1 == slotId);
        });
    }

    private int extractNumberFromName(String name) {
        Matcher matcher = NUMBER_PATTERN.matcher(name);
        if (matcher.find()) {
            try {
                return Integer.parseInt(matcher.group());
            } catch (NumberFormatException e) {
                return Integer.MAX_VALUE;
            }
        }
        return Integer.MAX_VALUE;
    }

    @Override
    protected List<Slot> getHighlightedSlots(DefaultedList<Slot> slots) {
        List<Slot> correctSlots = slots.stream()
                .filter(slot -> patternSlotIds.contains(slot.id))
                .sorted((slot1, slot2) -> {
                    int index1 = patternSlotIds.indexOf(slot1.id);
                    int index2 = patternSlotIds.indexOf(slot2.id);
                    return Integer.compare(index1, index2);
                })
                .collect(Collectors.toList());

        if (correctSlots.isEmpty()) return new ArrayList<>();
        List<Slot> newList = new ArrayList<>();
        newList.add(correctSlots.getFirst());

        return newList;
    }
}
