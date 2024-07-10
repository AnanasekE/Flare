package ananaseke.flare.Utils;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import com.mojang.blaze3d.systems.RenderSystem;
import org.joml.Matrix4f;

import java.awt.Color;


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
}
