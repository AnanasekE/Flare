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
        drawBox(context, entity, box);
    }

    public static void drawBox(WorldRenderContext context, Entity entity, Box box) {
        MatrixStack matrixStack = new MatrixStack();
        matrixStack.translate(
                -entity.getX() - context.camera().getPos().x + entity.getX(),
                -entity.getY() - context.camera().getPos().y + entity.getY(),
                -entity.getZ() - context.camera().getPos().z + entity.getZ()
        );
        WorldRenderer.drawBox(matrixStack, context.consumers().getBuffer(RenderLayer.LINES), box, 1, 0, 0, 1);
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

}
