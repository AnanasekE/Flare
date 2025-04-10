package ananaseke.flare.misc;

import ananaseke.flare.Utils.RenderUtils;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.TranslucentBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3i;

import java.awt.*;

public class AspectOfTheVoid {
    public AspectOfTheVoid() {
        MinecraftClient client = MinecraftClient.getInstance();

        WorldRenderEvents.AFTER_ENTITIES.register(context -> {
            if (client.player == null) return;
            if (client.world == null) return;
            if (client.cameraEntity == null) return;
            double maxReach = 61;
            if (client.player.getMainHandStack().getName().getString().contains("Aspect of the Void") && client.player.isSneaking()) {
                HitResult hit = client.cameraEntity.raycast(maxReach, 1.0F, true);
                if (hit == null) return;
                if (hit.getType() == HitResult.Type.BLOCK) {
                    BlockHitResult blockHitResult = (BlockHitResult) hit;
                    BlockPos blockPos = blockHitResult.getBlockPos();
                    Block block = client.world.getBlockState(blockPos).getBlock();
                    if (block instanceof TranslucentBlock) return;
                    if (client.world.getBlockState(blockPos.add(new Vec3i(0, 1, 0))).getBlock() == Blocks.AIR &&
                            client.world.getBlockState(blockPos.add(new Vec3i(0, 2, 0))).getBlock() == Blocks.AIR)
                        RenderUtils.drawBox(context, new Box(blockPos), Color.RED);
                }
            }
        });
    }
}
