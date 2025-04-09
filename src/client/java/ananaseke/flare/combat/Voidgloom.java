package ananaseke.flare.combat;

import ananaseke.flare.Utils.RenderUtils;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

import java.awt.*;
import java.util.Objects;

public class Voidgloom {
    public Voidgloom() {
        MinecraftClient client = MinecraftClient.getInstance();

        WorldRenderEvents.AFTER_ENTITIES.register((worldRenderContext) -> {
            PlayerEntity player = client.player;
            if (player == null) return;
            if (client.world == null) return;

            Box box = new Box(player.getPos().add(-10, -5, -10), player.getPos().add(10, 5, 10));
            BlockPos beaconPos = null;
            // find beacon block
            for (int x = (int) box.minX; x < box.maxX; x++) {
                for (int y = (int) box.minY; y < box.maxY; y++) {
                    for (int z = (int) box.minZ; z < box.maxZ; z++) {
                        if (client.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.BEACON) continue;
                        beaconPos = new BlockPos(x, y, z);
                        break;
                    }
                }
            }

            if (beaconPos == null) return;
            RenderUtils.drawBox(worldRenderContext, Objects.requireNonNull(worldRenderContext.matrixStack()), new Box(beaconPos), Color.RED);
        });
    }
}
