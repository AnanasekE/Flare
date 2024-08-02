package ananaseke.flare;

import ananaseke.flare.Plaques.Plaques;
import ananaseke.flare.Utils.RenderUtils;
import ananaseke.flare.dungeons.DungeonMapRenderer;
import ananaseke.flare.fullbright.Fullbright;
import ananaseke.flare.garden.VisitorTracker;
import ananaseke.flare.misc.AntiSpam;
import ananaseke.flare.misc.ChatHider;
import ananaseke.flare.overlays.ItemOverlays;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.MapRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.type.MapIdComponent;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlareClient implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("flare");
    public static final String MOD_ID = "flare";

    private static Optional<Boolean> lastIsLower = Optional.empty();

    private static MinecraftClient client = MinecraftClient.getInstance();

    Config config;

    @Override
    public void onInitializeClient() {
        AutoConfig.register(Config.class, GsonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(Config.class).getConfig();

        Plaques.initialize();
        Fullbright.initialize();
        ItemOverlays.initialize();
        Info.initialize();
        AntiSpam.initialize();
        KeyBinds.initialize();
        ChatHider.initialize();
        VisitorTracker.initialize();
        DungeonMapRenderer.initialize();


        WorldRenderEvents.AFTER_ENTITIES.register(context -> {
            if (KeyBinds.highlightEntitiesBoxToggle) {
                client.world.getEntities().forEach(entity -> {
//                    LOGGER.info(entity.getName().getString());
                    if (entity instanceof ClientPlayerEntity) return;
                    RenderUtils.drawEntityBox(context, entity);
                });
            }
        });

        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> {
            if (!config.shouldRenderDungeonMap) return;
            try {
                MapRenderer mapRenderer = client.gameRenderer.getMapRenderer();
                assert client.player != null;
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
                LOGGER.error("Error rendering map: " + e.getMessage());
            }


        });

        WorldRenderEvents.AFTER_ENTITIES.register(worldRenderContext -> {
            if (!config.dungeonHigherLowerSolver) return;
//            HIGHER/LOWER SOLVER
            BlazeEntity lowestBlaze;
            BlazeEntity highestBlaze;
            boolean isLower = true; // chest under y 75
            List<BlazeEntity> blazes = new ArrayList<>();
            client.world.getEntities().forEach(entity -> {
                if (entity instanceof BlazeEntity) {
                    if (entity.squaredDistanceTo(client.player) < 10000) {
                        // if blaze is not already in the list
                        blazes.add((BlazeEntity) entity);
                    }
                }
            });

            // is in blaze

            BlockPos playerPos = client.player.getBlockPos();
            Box box = new Box(
                    playerPos.getX() + 10,
                    playerPos.getY() + 10,
                    playerPos.getZ() + 10,
                    playerPos.getX() - 10,
                    playerPos.getY() - 10,
                    playerPos.getZ() - 10
            );

//          70, 119
            Optional<BlockPos> chest = searchForChestAtY(box, 70);
            Optional<BlockPos> chest2 = searchForChestAtY(box, 69);

            if (lastIsLower.isEmpty()) {
                if (chest.isPresent()) {
                    isLower = true;
                    lastIsLower = Optional.of(true);
                } else if (chest2.isPresent()) {
                    isLower = false;
                    lastIsLower = Optional.of(false);
                }
            } else {
                isLower = lastIsLower.get();
            }




            // height is based on hp
            if (blazes.size() > 0) {
                lowestBlaze = blazes.get(0);
                highestBlaze = blazes.get(0);
                for (BlazeEntity blaze : blazes) {
                    if (blaze.getHealth() < lowestBlaze.getHealth()) {
                        lowestBlaze = blaze;
                    }
                    if (blaze.getHealth() > highestBlaze.getHealth()) {
                        highestBlaze = blaze;
                    }
                }

                if (isLower) {
                    RenderUtils.drawEntityBox(worldRenderContext, lowestBlaze);
                } else {
                    RenderUtils.drawEntityBox(worldRenderContext, highestBlaze);
                }
            }

        });

        ClientReceiveMessageEvents.GAME.register((message, overlay) -> {
            if (message.getString().contains("entered The Catacombs,")) {
                lastIsLower = Optional.empty();
            }
        });

    }


    private static Optional<BlockPos> searchForChestAtY(Box box, int y) {
        for (int x = (int) box.minX; x < box.maxX; x++) {
            for (int z = (int) box.minZ; z < box.maxZ; z++) {
                BlockPos searchPos = new BlockPos(x, y, z);
                if (client.world.getBlockEntity(searchPos) instanceof ChestBlockEntity) {
                    return Optional.of(searchPos);
                }
            }
        }
        return Optional.empty();
    }


}


