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
        MinecraftClient client = MinecraftClient.getInstance();
        AtomicReference<String> returnVal = null;
        if (!client.player.getMainHandStack().getComponents().get(DataComponentTypes.CUSTOM_DATA).isEmpty()) {
            @Nullable NbtComponent customData = client.player.getMainHandStack().getComponents().get(DataComponentTypes.CUSTOM_DATA);
            assert customData != null;
            customData.apply((nbtComponent) -> {
//                nbtComponent.getString("id")
                returnVal.set(nbtComponent.getString("id"));
            });
        }
        return Optional.of(returnVal.get());
    }
}
