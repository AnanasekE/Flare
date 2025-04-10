package ananaseke.flare.dungeons;

import ananaseke.flare.misc.SimpleColorSolver;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;

import java.util.ArrayList;
import java.util.List;

public class Croesus extends SimpleColorSolver {

    public Croesus() {
        super("Croesus");
    }

    @Override
    protected List<Slot> getHighlightedSlots(DefaultedList<Slot> slots) {
        return new ArrayList<>(slots.stream()
                .filter(slot -> {
                    if (slot.getStack().getComponents().get(DataComponentTypes.LORE) == null) {
                        return false;
                    }
                    return slot.getStack().getComponents().get(DataComponentTypes.LORE)
                            .lines()
                            .stream()
                            .anyMatch(text -> text.getString().matches("No Chests Opened!"));
                }).toList());
    }
}
