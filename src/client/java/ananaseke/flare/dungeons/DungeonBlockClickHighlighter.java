package ananaseke.flare.dungeons;

import ananaseke.flare.FlareClient;
import ananaseke.flare.callbacks.ItemUsedOnBlock;
import ananaseke.flare.misc.Waypoint;
import ananaseke.flare.misc.WaypointUtils;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemUsageContext;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DungeonBlockClickHighlighter {
    private static final Map<Waypoint, Integer> waypointCooldowns = new HashMap<>();

    public DungeonBlockClickHighlighter() {
        ItemUsedOnBlock.EVENT.register(DungeonBlockClickHighlighter::onItemUsed);
        WorldRenderEvents.AFTER_ENTITIES.register(DungeonBlockClickHighlighter::onWorldRender);
        ClientTickEvents.END_CLIENT_TICK.register(DungeonBlockClickHighlighter::onEndTick);
    }

    private static void onItemUsed(ItemUsageContext context) {
        if (!context.getWorld().isClient) return;
        if (!Dungeon.isInDungeon) return;
        FlareClient.LOGGER.info("CLICKED");
        Block block = context.getWorld().getBlockState(context.getBlockPos()).getBlock();
        FlareClient.LOGGER.info(block.getName().toString());
        if (block != Blocks.CHEST && block != Blocks.TRAPPED_CHEST && block != Blocks.LEVER) return;

        Waypoint waypoint = new Waypoint(context.getBlockPos(), Color.RED, false);

        FlareClient.LOGGER.info("Player created a waypoint at {}", waypoint.pos);
        if (waypointCooldowns.containsKey(waypoint)) return;
        waypointCooldowns.put(waypoint, 5 * 20);
    }

    private static void onWorldRender(WorldRenderContext context) {
        for (Map.Entry<Waypoint, Integer> entry : waypointCooldowns.entrySet()) {
            Waypoint waypoint = entry.getKey();

            WaypointUtils.renderWaypoint(context, waypoint);
        }

    }

    private static void onEndTick(MinecraftClient client) {
        Iterator<Map.Entry<Waypoint, Integer>> iterator = waypointCooldowns.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Waypoint, Integer> entry = iterator.next();
            int ticks = entry.getValue();

            if (ticks <= 0) {
                FlareClient.LOGGER.info("Removed waypoint");
                iterator.remove();
            } else {
                entry.setValue(ticks - 1);
            }
        }
    }
}