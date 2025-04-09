package ananaseke.flare;

import ananaseke.flare.Utils.RenderUtils;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

import java.awt.*;

public class DevTools {
    public DevTools() {
        WorldRenderEvents.AFTER_ENTITIES.register(context -> {
            if (KeyBinds.isToggleActive(KeyBinds.highlightEntitiesBox)) {
                MinecraftClient client = MinecraftClient.getInstance();
                if (client.world == null) return;
                client.world.getEntities().forEach(entity -> {
//                    LOGGER.info(entity.getName().getString());
                    if (entity instanceof ClientPlayerEntity) return;
                    RenderUtils.drawEntityBox(context, entity, Color.RED);
                });
            }
        });
    }
}
