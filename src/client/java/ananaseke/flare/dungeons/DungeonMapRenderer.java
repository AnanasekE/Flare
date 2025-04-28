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

import static ananaseke.flare.FlareClient.LOGGER;

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

            if (stack.getItem() instanceof FilledMapItem) {
                cachedMapItemStack = stack;
                mapRenderer.draw(
                        matrixStack,
                        drawContext.getVertexConsumers(),
                        defaultMapIdComponent,
                        FilledMapItem.getMapState(stack, client.world),
                        false,
                        15
                );
            } else {
                if (cachedMapItemStack == null) return;
                mapRenderer.draw(
                        matrixStack,
                        drawContext.getVertexConsumers(),
                        defaultMapIdComponent,
                        FilledMapItem.getMapState(cachedMapItemStack, client.world),
                        false,
                        15
                );
            }
        } catch (Exception e) {
            LOGGER.error("Error rendering map: {}", e.getMessage());
        }
    }


}
