package ananaseke.flare.Utils;

import net.minecraft.client.MinecraftClient;
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
}
