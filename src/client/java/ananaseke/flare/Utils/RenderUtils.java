package ananaseke.flare.Utils;

import ananaseke.flare.overlays.TimedOverlay;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;

import java.awt.Color;
import java.util.OptionalDouble;


public class RenderUtils {
    static TimedOverlay timedOverlay = new TimedOverlay();


    public static void drawEntityBox(WorldRenderContext context, Entity entity, Color color) {
        Box box = entity.getBoundingBox();
        MatrixStack matrixStack = new MatrixStack();

        drawBox(context, matrixStack, box, color);
    }

    public static void drawBox(WorldRenderContext context, MatrixStack matrixStack, Box box, Color color) {
        matrixStack.translate(
                -context.camera().getPos().x,
                -context.camera().getPos().y,
                -context.camera().getPos().z
        );
        WorldRenderer.drawBox(matrixStack, context.consumers().getBuffer(RenderLayer.LINES), box,
                (float) color.getRed() / 255,
                (float) color.getGreen() / 255,
                (float) color.getBlue() / 255,
                (float) color.getAlpha() / 255
        );
        matrixStack.translate(
                context.camera().getPos().x,
                context.camera().getPos().y,
                context.camera().getPos().z
        );
    }

    public static void drawBox(WorldRenderContext context, Box box, Color color) {
        MatrixStack matrixStack = new MatrixStack();
        drawBox(context, matrixStack, box, color);
    }

    public static void drawCenteredText(String text, int offsetY, DrawContext context) {

        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer textRenderer = client.textRenderer;
        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();
        int x = width / 2 - textRenderer.getWidth(text) / 2;
        int y = height / 2 - textRenderer.fontHeight / 2 + offsetY;

        context.drawText(textRenderer, text, x, y, Color.WHITE.getRGB(), true);
    }

    public static void highlightSlot(DrawContext drawContext, Slot slot, int z, int color) {
        drawContext.fill(RenderLayer.getGui(), slot.x, slot.y, slot.x + 16, slot.y + 16, z, color); // 0x8000FF00
    }

    public static void highlightSlot(DrawContext drawContext, Slot slot) {
        highlightSlot(drawContext, slot, 0, 0x8000FF00);
    }

    public static void highlightSlot(DrawContext drawContext, Slot slot, int zOffset) {
        highlightSlot(drawContext, slot, zOffset, 0x8000FF00);
    }

    public static void renderItemTexture(DrawContext drawContext, Slot slot, ItemStack stack, int zOffset) {
        drawContext.drawItem(stack, slot.x, slot.y, 0, zOffset);
    }

}
