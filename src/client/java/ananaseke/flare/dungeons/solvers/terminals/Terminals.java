package ananaseke.flare.dungeons.solvers.terminals;

import ananaseke.flare.Flare;
import ananaseke.flare.FlareClient;
import ananaseke.flare.Utils.RenderUtils;
import ananaseke.flare.callbacks.DrawBackgroundScreenCallback;
import ananaseke.flare.callbacks.OnSlotStackPickup;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.impl.client.rendering.v0.RenderingCallbackInvoker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.GlyphRenderer;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.render.*;
import net.minecraft.item.AirBlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.Slot;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Terminals {
    static boolean isInGoldor = false;
    static List<Integer> slotsToHighlight = new ArrayList<>();

    public static void initialize() {
        StartsWithSequenceSolver.initialize();

//        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> {
//            if (slotsToHighlight.isEmpty()) return;
//            MinecraftClient client = MinecraftClient.getInstance();
//            if (!(client.currentScreen instanceof GenericContainerScreen)) return;
//            GenericContainerScreen screen = (GenericContainerScreen) client.currentScreen;
//            GenericContainerScreenHandler handler = screen.getScreenHandler();
//
//            for (Integer id : slotsToHighlight) {
//                for (Slot slot : handler.slots) {
//                    if (slot.id != id) continue;
//                    int x = slot.x;
//                    int y = slot.y;
//                    int offset = client.getWindow().getScaledWidth() / 2;
//                    int yOffset = 300;
//                    drawContext.fill(RenderLayer.getGui(),x - 8 + offset, y - 8 + yOffset, x + 8 + offset, y + 8 + yOffset,10000,0x8000FF00); // 0x8000FF00
////                    Matrix4f matrix4f = drawContext.getMatrices().peek().getPositionMatrix();
////                    BufferBuilder bufferBuilder = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
////                    bufferBuilder.vertex(matrix4f, x, y, 0).color(0x8000FF00);
////                    bufferBuilder.vertex(matrix4f, x + 16, y, 0).color(0x8000FF00);
////                    bufferBuilder.vertex(matrix4f, x, y + 16, 0).color(0x8000FF00);
////                    bufferBuilder.vertex(matrix4f, x + 16, y + 16, 0).color(0x8000FF00);
////                    BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
//                }
//            }
//        });

        DrawBackgroundScreenCallback.EVENT.register(drawContext -> {
            if (slotsToHighlight.isEmpty()) return;
            MinecraftClient client = MinecraftClient.getInstance();
            if (!(client.currentScreen instanceof GenericContainerScreen)) return;
            GenericContainerScreen screen = (GenericContainerScreen) client.currentScreen;
            GenericContainerScreenHandler handler = screen.getScreenHandler();

            for (Integer id : slotsToHighlight) {
                for (Slot slot : handler.slots) {
                    if (slot.id != id) continue;
                    int x = slot.x;
                    int y = slot.y;
                    int offset = client.getWindow().getScaledWidth() / 2;
                    int yOffset = client.getWindow().getScaledHeight() / 2;
                    drawContext.fill(RenderLayer.getGui(), x - 8 + offset, y - 8 + yOffset, x + 8 + offset, y + 8 + yOffset, 10000, 0x8000FF00); // 0x8000FF00

                }
            }
        });


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
//            if (!isInGoldor) return;
//            if (client.player == null) return;
//            if (client.currentScreen == null) return;
////            client.player.sendMessage(client.currentScreen.getTitle());
//            if (!client.currentScreen.getTitle().getString().startsWith("Select all the")) return;
            if (!preChestChecks(client, "Select all the")) return;
            if (client.currentScreen instanceof GenericContainerScreen screen) {
                GenericContainerScreenHandler handler = screen.getScreenHandler();

                Pattern pattern = Pattern.compile("Select all the ([A-Za-z_]+) items!");
                Matcher matcher = pattern.matcher(screen.getTitle().getString());

                String color = null;
                if (matcher.find()) {
                    color = matcher.group(1);
                }
                for (Slot slot : handler.slots) {
                    ItemStack stack = slot.getStack();
                    if (stack.getItem() instanceof AirBlockItem) continue;
                    String modifiedName = stack.getName().getString().toUpperCase().replaceAll(" ", "_");
                    if (color == null) continue;
                    if (modifiedName.contains(color)) {
                        slotsToHighlight.add(slot.getIndex());
                    }
                }
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!preChestChecks(client, "What starts with:")) return;

            GenericContainerScreen screen = (GenericContainerScreen) client.currentScreen;
            GenericContainerScreenHandler handler = screen.getScreenHandler();

            Pattern pattern = Pattern.compile("What starts with: '([A-Z])'\\?");
            Matcher matcher = pattern.matcher(client.currentScreen.getTitle().getString());

            if (matcher.find()) {
                String letter = matcher.group(1);
                for (Slot slot : handler.slots) {
                    ItemStack stack = slot.getStack();
                    if (stack.getName().getString().startsWith(letter)) {
                        slotsToHighlight.add(slot.id);
                    }
                }
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (slotsToHighlight.isEmpty()) return;
            if (client.currentScreen instanceof GenericContainerScreen) return;
            slotsToHighlight.clear();
        });

        OnSlotStackPickup.EVENT.register(slotId -> {
            if (slotsToHighlight.isEmpty()) return;
            slotsToHighlight.removeIf(id -> slotId == id);

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

    private static boolean preChestChecks(MinecraftClient client, String startsWith) {
        if (!isInGoldor) return false;
        if (client.player == null) return false;
        if (client.currentScreen == null) return false;
        if (!client.currentScreen.getTitle().getString().startsWith(startsWith)) return false;
        if (!(client.currentScreen instanceof GenericContainerScreen)) return false;
        return true;
    }
}
