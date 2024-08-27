package ananaseke.flare.dungeons.solvers.terminals;

import ananaseke.flare.FlareClient;
import ananaseke.flare.KeyBinds;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.block.StainedGlassPaneBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.component.Component;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.AirBlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.Slot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class Terminals {
    static boolean isInGoldor = false;
    static Optional<Integer> slotToHighlight = Optional.empty();

    public static void initialize() {
        StartsWithSequenceSolver.initialize();

        // Click in order

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.getServer() == null) return;
            client.getServer().getBossBarManager().getAll().forEach(commandBossBar -> {
                if (commandBossBar.getName().getString().contains("Goldor")) {
                    isInGoldor = true;
                } else {
                    isInGoldor = false;
                }
            });
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!isInGoldor) return;
            if (client.player == null) return;
            if (client.currentScreen == null) return;
//            client.player.sendMessage(client.currentScreen.getTitle());
            if (!client.currentScreen.getTitle().getString().startsWith("Select all the")) return;
            if (client.currentScreen instanceof GenericContainerScreen screen) {
                GenericContainerScreenHandler handler = screen.getScreenHandler();

                for (Slot slot : handler.slots) {
                    ItemStack stack = slot.getStack();
                    if (stack.getItem() instanceof AirBlockItem) continue;

                    FlareClient.LOGGER.info("Stack: " + stack.getName());
//                    FlareClient.LOGGER.info("Stack: " + stack.getComponents().get(DataComponentTypes.ATTRIBUTE_MODIFIERS));


                    if (stack.getComponents().get(DataComponentTypes.BASE_COLOR) == null) continue;
                    FlareClient.LOGGER.info("ItemHasColor " + stack.getComponents().get(DataComponentTypes.BASE_COLOR).getName());
                }
            }
        });

        // move to clienttickevents
//        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> {
//            if (!isInGoldor) return;
//            if (client.player == null) return;
//            if (client.currentScreen == null) return;
//            client.player.sendMessage(client.currentScreen.getTitle());
//            if (!client.currentScreen.getTitle().getString().startsWith("What starts with:")) return;
//            String name = client.currentScreen.getTitle().getString();
////            char letter = name.split(":")[0]
//
//        });
      /*  HudRenderCallback.EVENT.register((drawContext, tickCounter) -> {
            if (!isInGoldor) return;
            if (client.player == null) return;
            if (client.currentScreen == null) return;
            client.player.sendMessage(client.currentScreen.getTitle());
            if (!client.currentScreen.getTitle().getString().startsWith("Select all the:")) return;
            if (client.currentScreen instanceof GenericContainerScreen screen) {
                GenericContainerScreenHandler handler = screen.getScreenHandler();

                for (Slot slot : handler.slots) {
                    ItemStack stack = slot.getStack();
                    if (stack.getComponents().get(DataComponentTypes.BASE_COLOR) == null) continue;
                    FlareClient.LOGGER.info("ItemHasColor" + stack.getComponents().get(DataComponentTypes.BASE_COLOR).getName());
                }
            }

        });*/

    }
}
