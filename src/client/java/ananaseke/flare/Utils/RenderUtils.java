package ananaseke.flare.Utils;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.awt.Color;
import java.awt.image.renderable.RenderContext;


public class RenderUtils {

    public static void drawEntityBox(WorldRenderContext context, Entity entity) {
        Box box = entity.getBoundingBox();
        MatrixStack matrixStack = new MatrixStack();

        drawBox(context, matrixStack, box);
    }

    public static void drawBox(WorldRenderContext context, MatrixStack matrixStack, Box box) {
        matrixStack.translate(
                -context.camera().getPos().x,
                -context.camera().getPos().y,
                -context.camera().getPos().z
        );
        WorldRenderer.drawBox(matrixStack, context.consumers().getBuffer(RenderLayer.LINES), box, 1, 0, 0, 1);
        matrixStack.translate(
                context.camera().getPos().x,
                context.camera().getPos().y,
                context.camera().getPos().z
        );
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

    public static void drawLineCrosshairToBlock(BlockPos blockPos) {

    }

}
