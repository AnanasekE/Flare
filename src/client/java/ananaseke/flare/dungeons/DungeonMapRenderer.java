package ananaseke.flare.dungeons;

import ananaseke.flare.Config;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.MapRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.type.MapIdComponent;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;

import static ananaseke.flare.FlareClient.LOGGER;

public class DungeonMapRenderer {
    public DungeonMapRenderer() {
        MinecraftClient client = MinecraftClient.getInstance();
        Config config = AutoConfig.getConfigHolder(Config.class).getConfig();

        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> {
            if (!config.shouldRenderDungeonMap) return;
            try {
                MapRenderer mapRenderer = client.gameRenderer.getMapRenderer();
                if (client.player == null) return;
                ItemStack stack = client.player.getInventory().getStack(8);
                if (stack.getItem() instanceof FilledMapItem) {
                    MatrixStack matrixStack = new MatrixStack();

                    float scale = 1.5F;
                    matrixStack.scale(scale, scale, 1);

                    mapRenderer.draw(
                            matrixStack,
                            drawContext.getVertexConsumers(),
                            new MapIdComponent(1024),
                            FilledMapItem.getMapState(stack, client.world),
                            false,
                            15
                    );
                }
            } catch (Exception e) {
                LOGGER.error("Error rendering map: {}", e.getMessage());
            }


        });
    }
}
