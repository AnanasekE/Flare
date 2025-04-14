package ananaseke.flare.misc.experiments;

import ananaseke.flare.misc.SimpleColorSolver;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Chronomatron extends SimpleColorSolver {
    private List<Integer> sequence = new ArrayList<>();

    public Chronomatron() {
        super("Chronomatron \\(.+\\)");

//        ClientTickEvents.END_CLIENT_TICK.register(client -> {
//            if (client.currentScreen == null) return;
//            if (!(client.currentScreen instanceof GenericContainerScreen)) return;
//            if (client.player == null) return;
//            DefaultedList<Slot> allSlots = ((GenericContainerScreen) (client.currentScreen)).getScreenHandler().slots;
//            Optional<Slot> maybeSlot = allSlots.stream().filter(slot -> slot.getStack().getName().getString().matches("Remember the pattern!")).findFirst();
//            if (maybeSlot.isPresent()) {
//
//            }
//        });
    }

    @Override
    protected List<Slot> getHighlightedSlots(DefaultedList<Slot> slots) {
        return List.of();
    }
}
