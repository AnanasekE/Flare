package ananaseke.flare.misc;

import ananaseke.flare.Utils.render.FlareRenderLayers;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BeaconBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;


public class WaypointUtils {
    private static final Identifier BEAM_TEXTURE = Identifier.ofVanilla("textures/entity/beacon_beam.png");

    public static void renderWaypoint(WorldRenderContext context, Waypoint waypoint) {
        MatrixStack matrixStack = context.matrixStack();
        VertexConsumerProvider consumers = context.consumers();

        if (matrixStack == null) return;
        if (consumers == null) return;

        matrixStack.push();

        matrixStack.translate(
                -context.camera().getPos().x,
                -context.camera().getPos().y,
                -context.camera().getPos().z
        );

        WorldRenderer.drawBox(matrixStack,
                consumers.getBuffer(FlareRenderLayers.LINES_THROUGH_WALLS),
                new Box(waypoint.pos),
                (float) waypoint.color.getRed() / 255,
                (float) waypoint.color.getGreen() / 255,
                (float) waypoint.color.getBlue() / 255,
                (float) waypoint.color.getAlpha() / 255
        );

        if (waypoint.isBeacon) { // UNTESTED
            BeaconBlockEntityRenderer.renderBeam(matrixStack,
                    consumers,
                    BEAM_TEXTURE,
                    context.tickCounter().getTickDelta(true),
                    1.0f,
                    context.world().getTime(),
                    0,
                    319,
                    waypoint.color.getRGB(),
                    0.2f,
                    0.25f);
        }

        matrixStack.translate(
                context.camera().getPos().x,
                context.camera().getPos().y,
                context.camera().getPos().z
        );

        matrixStack.pop();
    }
}
