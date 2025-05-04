package ananaseke.flare.dungeons;

import ananaseke.flare.Config;
import ananaseke.flare.Utils.RenderUtils;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HighlightStarredMobs {
    private static List<Entity> entities;

    public static void initialize() {
        Config config = AutoConfig.getConfigHolder(Config.class).getConfig();
        entities = new ArrayList<>();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
//            if (!config.highlightStarredMobs) return;
//            if (client.world == null) return;
//            client.world.getEntities().forEach(entity -> {
//                if (entity instanceof ArmorStandEntity && entity.getName().getString().contains("✯")) {
//                    if (!entities.contains(entity)) {
//                        entities.add(entity);
//                    }
//                }
//            });
//            try {
//                entities.forEach(entity -> {
//                    if (entity instanceof ArmorStandEntity armorStandEntity && armorStandEntity.isDead()) {
//                        entities.remove(entity);
//                    }
//                });
//            } catch (Exception ignored) {
//            }
        });

        WorldRenderEvents.AFTER_ENTITIES.register(worldRenderContext -> {
            if (!config.highlightStarredMobs) return;
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.world == null) return;
            client.world.getEntities().forEach(entity -> {
                if (entity instanceof ArmorStandEntity && entity.getName().getString().startsWith("✯ ")) {
                    RenderUtils.drawBox(worldRenderContext, new Box(0.5, 2, 0.5, -0.5, 0, -0.5).offset(entity.getPos()).offset(new Vec3d(0, -2, 0)), Color.RED);
                }
            });
//            try {
                entities.forEach(entity -> {
                    if (entity instanceof ArmorStandEntity armorStandEntity && armorStandEntity.isDead()) {
                        entities.remove(entity);
                    }
                });
//            } catch (Exception ignored) {
//            }
//            entities.forEach(entity -> {
//                RenderUtils.drawBox(worldRenderContext, new Box(0.5, 2, 0.5, -0.5, 0, -0.5).offset(entity.getPos()).offset(new Vec3d(0, -2, 0)), Color.RED);
//            });
        });
    }
}
