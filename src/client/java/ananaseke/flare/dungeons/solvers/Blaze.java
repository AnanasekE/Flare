package ananaseke.flare.dungeons.solvers;

import ananaseke.flare.Config;
import ananaseke.flare.Utils.RenderUtils;
import ananaseke.flare.callbacks.DungeonEvents;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.mob.BlazeEntity;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Blaze {
    //    private static Optional<Boolean> lastIsLower = Optional.empty();
    static MinecraftClient client = MinecraftClient.getInstance();
    private static boolean isRoomOpen;

    public static void initialize() {
        Config config = AutoConfig.getConfigHolder(Config.class).getConfig();


        WorldRenderEvents.AFTER_ENTITIES.register(worldRenderContext -> {
            if (!config.dungeonHigherLowerSolver) return;
            if (client.player == null) return;

            client.player.networkHandler.getListedPlayerListEntries().stream().limit(80L).toList().forEach(entry -> {
                if (entry.getDisplayName() == null) return;
                if (entry.getDisplayName().getString().contains("Higher Or Lower")) isRoomOpen = true;
            });

            if (!isRoomOpen) return;
//            HIGHER/LOWER SOLVER
            BlazeEntity lowestBlaze;
            BlazeEntity highestBlaze;
//            boolean isLower = true; // chest under y 75
            List<BlazeEntity> blazes = new ArrayList<>();
            if (client.world == null) return;
            client.world.getEntities().forEach(entity -> {
                if (entity instanceof BlazeEntity) {
                    if (entity.squaredDistanceTo(client.player) < 10000) {
                        // if blaze is not already in the list
                        blazes.add((BlazeEntity) entity);
                    }
                }
            });

            // is in blaze

//            BlockPos playerPos = client.player.getBlockPos();
//            Box box = new Box(
//                    playerPos.getX() + 10,
//                    playerPos.getY() + 10,
//                    playerPos.getZ() + 10,
//                    playerPos.getX() - 10,
//                    playerPos.getY() - 10,
//                    playerPos.getZ() - 10
//            );

//          70, 119
//            Optional<BlockPos> chestLower = searchForChestAtY(box, 70);
//            Optional<BlockPos> chestHigher = searchForChestAtY(box, 69);


            // height is based on hp
            if (blazes.size() > 1) {
                lowestBlaze = blazes.getFirst();
                highestBlaze = blazes.getFirst();
                for (BlazeEntity blaze : blazes) {
                    if (blaze.getHealth() < lowestBlaze.getHealth()) {
                        lowestBlaze = blaze;
                    }
                    if (blaze.getHealth() > highestBlaze.getHealth()) {
                        highestBlaze = blaze;
                    }
                }
                RenderUtils.drawEntityBox(worldRenderContext, lowestBlaze, Color.GREEN);
                RenderUtils.drawEntityBox(worldRenderContext, highestBlaze, Color.RED);
            }

        });

        DungeonEvents.ENTER.register(floor1 -> isRoomOpen = false);

//        ClientReceiveMessageEvents.GAME.register((message, overlay) -> {
//            if (message.getString().contains("entered The Catacombs,")) {
//                lastIsLower = Optional.empty(); // reset
//            }
//        });

    }


//    private static Optional<BlockPos> searchForChestAtY(Box box, int y) {
//        for (int x = (int) box.minX; x < box.maxX; x++) {
//            for (int z = (int) box.minZ; z < box.maxZ; z++) {
//                BlockPos searchPos = new BlockPos(x, y, z);
//                if (client.world.getBlockEntity(searchPos) instanceof ChestBlockEntity) {
//                    return Optional.of(searchPos);
//                }
//            }
//        }
//        return Optional.empty();
//    }
}
