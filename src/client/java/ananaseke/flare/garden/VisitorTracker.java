package ananaseke.flare.garden;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Map;

public class VisitorTracker {
    List<Map<String, Integer>> requiredItems;
    public static void initialize() {
        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> {
            // Draw the visitor tracker
//            MinecraftClient client = MinecraftClient.getInstance();
        });
        // callback on inventory open





    }
    ;
}
