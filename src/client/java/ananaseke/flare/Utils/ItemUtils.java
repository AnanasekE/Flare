package ananaseke.flare.Utils;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class ItemUtils {
    public static Optional<String> getInternalName(ItemStack stack) { // get internal name of item
        AtomicReference<String> returnVal = new AtomicReference<>();
        if (stack.getComponents().get(DataComponentTypes.CUSTOM_DATA) != null) {
            @Nullable NbtComponent customData = stack.getComponents().get(DataComponentTypes.CUSTOM_DATA);
            if (customData == null) return Optional.empty();
            customData.apply((nbtComponent) -> returnVal.set(nbtComponent.getString("id")));
            return Optional.of(returnVal.get());

        }
        return Optional.empty();
    }

    public static Optional<String> getEnchantments(ItemStack stack) {
        AtomicReference<String> returnVal = new AtomicReference<>();
        if (stack.getComponents().get(DataComponentTypes.CUSTOM_DATA) != null) {
            @Nullable NbtComponent customData = stack.getComponents().get(DataComponentTypes.CUSTOM_DATA);
            if (customData == null) return Optional.empty();
            customData.apply((nbtComponent) -> returnVal.set(nbtComponent.getString("enchantments")));
            return Optional.of(returnVal.get());

        }
        return Optional.empty();
    }

    public static Optional<NbtComponent> getCustomData(ItemStack stack) {
        if (stack.getComponents().get(DataComponentTypes.CUSTOM_DATA) != null) {
            @Nullable NbtComponent customData = stack.getComponents().get(DataComponentTypes.CUSTOM_DATA);
            if (customData == null) return Optional.empty();
            return Optional.of(customData);
        }
        return Optional.empty();
    }
}
