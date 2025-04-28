package ananaseke.flare.Utils.render;

import net.minecraft.client.render.*;

import java.util.OptionalDouble;

public class FlareRenderLayers extends RenderLayer {
    public FlareRenderLayers(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, Runnable startAction, Runnable endAction) {
        super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction);
    }

    public static final RenderLayer FILLED_THROUGH_WALLS = RenderLayer.of("filled_through_walls", //FIXME
            VertexFormats.POSITION_COLOR,
            VertexFormat.DrawMode.TRIANGLE_STRIP,
            RenderLayer.CUTOUT_BUFFER_SIZE,
            false,
            true,
            MultiPhaseParameters.builder()
                    .program(RenderPhase.POSITION_TEXTURE_PROGRAM)
                    .layering(RenderPhase.VIEW_OFFSET_Z_LAYERING)
                    .transparency(RenderPhase.TRANSLUCENT_TRANSPARENCY)
                    .depthTest(RenderPhase.ALWAYS_DEPTH_TEST)
                    .build(false));

    public static final RenderLayer LINES_THROUGH_WALLS = RenderLayer.of("lines_through_walls",
            VertexFormats.LINES,
            VertexFormat.DrawMode.LINES,
            1536,
            MultiPhaseParameters.builder()
                    .program(LINES_PROGRAM)
                    .lineWidth(new RenderPhase.LineWidth(OptionalDouble.empty()))
                    .layering(VIEW_OFFSET_Z_LAYERING)
                    .transparency(TRANSLUCENT_TRANSPARENCY)
                    .target(ITEM_ENTITY_TARGET)
                    .writeMaskState(ALL_MASK)
                    .cull(DISABLE_CULLING)
                    .depthTest(ALWAYS_DEPTH_TEST)
                    .build(false));

    public static final RenderLayer LINES = FlareRenderLayers.of("lines",
            VertexFormats.LINES,
            VertexFormat.DrawMode.LINES,
            1536,
            MultiPhaseParameters.builder()
                    .program(LINES_PROGRAM)
                    .lineWidth(new RenderPhase.LineWidth(OptionalDouble.empty()))
                    .layering(VIEW_OFFSET_Z_LAYERING)
                    .transparency(TRANSLUCENT_TRANSPARENCY)
                    .target(ITEM_ENTITY_TARGET)
                    .writeMaskState(ALL_MASK)
                    .cull(DISABLE_CULLING)
                    .build(false));
}
