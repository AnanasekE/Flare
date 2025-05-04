package ananaseke.flare.dungeons;

import ananaseke.flare.Config;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.MapRenderer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.type.MapIdComponent;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.map.MapDecoration;
import net.minecraft.item.map.MapDecorationTypes;
import net.minecraft.item.map.MapState;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DungeonMapRenderer {
    static MinecraftClient client;
    static Config config;
    static ItemStack cachedMapItemStack;
    static final MapIdComponent defaultMapIdComponent = new MapIdComponent(1024);

    public DungeonMapRenderer() {
        config = AutoConfig.getConfigHolder(Config.class).getConfig();
        client = MinecraftClient.getInstance();
        HudRenderCallback.EVENT.register(DungeonMapRenderer::onHudRender);
    }

    private static void onHudRender(DrawContext drawContext, RenderTickCounter tickCounter) {
        if (!config.shouldRenderDungeonMap) return;
        try {
            MapRenderer mapRenderer = client.gameRenderer.getMapRenderer();
            if (client.player == null) return;

            ItemStack stack = client.player.getInventory().getStack(8);
            MatrixStack matrixStack = new MatrixStack();
            float scale = 1.5F;
            matrixStack.scale(scale, scale, 1);

            if (stack.getItem() instanceof FilledMapItem || cachedMapItemStack != null) {
                MapState mapState;

                if (stack.getItem() instanceof FilledMapItem) {
                    mapState = FilledMapItem.getMapState(stack, client.world);
                } else {
                    mapState = FilledMapItem.getMapState(cachedMapItemStack, client.world);
                }

                if (mapState == null) return;
                Iterable<MapDecoration> defaultDecorations = mapState.getDecorations();
                List<MapDecoration> changedDecorations = new ArrayList<>();

                for (MapDecoration decoration : defaultDecorations) {
                    if (decoration.type() == MapDecorationTypes.PLAYER) {
                        decoration = new MapDecoration(MapDecorationTypes.BANNER_CYAN, decoration.x(), decoration.z(), decoration.rotation(), Optional.of(Text.of("PLAYER1")));
                    }
                    changedDecorations.add(decoration);
                }

                mapState.replaceDecorations(changedDecorations);

                if (stack.getItem() instanceof FilledMapItem) {
                    cachedMapItemStack = stack;
                    mapRenderer.draw(
                            matrixStack,
                            drawContext.getVertexConsumers(),
                            defaultMapIdComponent,
                            mapState,
                            false,
                            15
                    );

                } else {
                    if (cachedMapItemStack == null) return;
                    mapRenderer.draw(
                            matrixStack,
                            drawContext.getVertexConsumers(),
                            defaultMapIdComponent,
                            mapState,
                            false,
                            15
                    );
                }
            }
        } catch (Exception e) {
//            LOGGER.error("Error rendering map: {}", e.getMessage());
        }
    }


}
