package ananaseke.flare.dungeons;

import ananaseke.flare.misc.SimpleColorSolver;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;

import java.util.ArrayList;
import java.util.List;

public class PartyFinder extends SimpleColorSolver {
    public PartyFinder() {
        super("^Party Finder$");
    }

    @Override
    protected List<Slot> getHighlightedSlots(DefaultedList<Slot> slots) {
        return new ArrayList<>(slots.stream().filter(slot -> {
            if (slot.getStack().getComponents().get(DataComponentTypes.LORE) == null) return false;
            return slot.getStack().getComponents().get(DataComponentTypes.LORE).lines().stream().anyMatch(text -> text.getString().matches("Click to join!"));
        }).toList());
    }
}
