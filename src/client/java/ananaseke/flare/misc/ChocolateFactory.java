package ananaseke.flare.misc;

import ananaseke.flare.Flare;
import ananaseke.flare.FlareClient;
import ananaseke.flare.Utils.ItemUtils;
import ananaseke.flare.Utils.Utils;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChocolateFactory extends SimpleColorSolver implements ClientModInitializer {
    Map<String, Integer> rabbitMultipliers = new HashMap<>();

    public ChocolateFactory() {
        super("^Chocolate Factory$");
        rabbitMultipliers.put("Bro", 1);
        rabbitMultipliers.put("Cousin", 2);
        rabbitMultipliers.put("Sis", 3);
        rabbitMultipliers.put("Daddy", 4);
        rabbitMultipliers.put("Granny", 5);
        rabbitMultipliers.put("Uncle", 6);
        rabbitMultipliers.put("Dog", 7);
    }

    @Override
    public void onInitializeClient() {

    }

    @Override
    protected List<Slot> getHighlights(DefaultedList<Slot> slots) {
//        FlareClient.LOGGER.info("CF works");
        Pattern coinsPattern = Pattern.compile("([0-9,]+) Chocolate");
        Pattern namePattern = Pattern.compile("Rabbit (Bro|Cousin|Sis|Daddy|Granny|Uncle|Dog).*");
        int bestValue = Integer.MAX_VALUE;
        Slot bestSlot = null;

        for (Slot slot : slots) {
            Matcher nameMatcher = namePattern.matcher(Utils.removeColorTags(slot.getStack().getName().getString()));
            if (!nameMatcher.matches()) continue;
            String name = nameMatcher.group(1);
            LoreComponent loreComponent = slot.getStack().getComponents().get(DataComponentTypes.LORE);
            if (loreComponent == null) continue;
            for (Text text : loreComponent.lines()) {
                Matcher coinsMatcher = coinsPattern.matcher(text.getString());
                if (coinsMatcher.matches()) {
                    Integer multiplier = rabbitMultipliers.get(name);
                    int coins = Integer.parseInt(coinsMatcher.group(1).replace(",", ""));
                    if (coins / multiplier < bestValue) {
                        bestValue = coins / multiplier;
                        bestSlot = slot;
                    }
                }
            }
        }
        if (bestSlot == null) {
            return new ArrayList<>();
        }
        ArrayList<Slot> output = new ArrayList<>();
        output.add(bestSlot);
        return output;
    }
}


