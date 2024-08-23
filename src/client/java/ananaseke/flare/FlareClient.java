package ananaseke.flare;

import ananaseke.flare.Plaques.Plaques;
import ananaseke.flare.Utils.RenderUtils;
import ananaseke.flare.dungeons.Dungeon;
import ananaseke.flare.dungeons.DungeonMapRenderer;
import ananaseke.flare.dungeons.HighlightStarredMobs;
import ananaseke.flare.dungeons.Solvers;
import ananaseke.flare.fullbright.Fullbright;
import ananaseke.flare.garden.VisitorTracker;
import ananaseke.flare.misc.AntiSpam;
import ananaseke.flare.misc.ChatHider;
import ananaseke.flare.overlays.ItemOverlays;
import com.mojang.blaze3d.systems.RenderSystem;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.TranslucentBlock;
import net.minecraft.block.TransparentBlock;
import net.minecraft.block.entity.VaultBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.type.MapIdComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FlareClient implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("flare");
    public static final String MOD_ID = "flare";


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
        Solvers.initialize();
        HighlightStarredMobs.initialize();
        Dungeon.initialize();

        WorldRenderEvents.AFTER_ENTITIES.register(context -> {
            if (KeyBinds.highlightEntitiesBoxToggle) {
                client.world.getEntities().forEach(entity -> {
//                    LOGGER.info(entity.getName().getString());
                    if (entity instanceof ClientPlayerEntity) return;
                    RenderUtils.drawEntityBox(context, entity, Color.RED);
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

        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> {
            if (!config.showSlayerInfo) return;

            PlayerEntity player = client.player;
            if (player == null) return;
            Box targetArea = new Box(player.getPos().add(-10, -10, -10), player.getPos().add(10, 10, 10));

            List<LivingEntity> entities = new ArrayList<>();

            assert client.world != null;
            client.world.getEntities().forEach(entity -> {
                if (entity instanceof LivingEntity) {
                    if (targetArea.contains(entity.getPos())) {
                        entities.add((LivingEntity) entity);
                    }
                }
            });

            Optional<LivingEntity> OpSlayer = entities.stream().filter(livingEntity ->
                    livingEntity.getName().getString().contains("Revenant Horror") ||
                            livingEntity.getName().getString().contains("Tarantula Broodfather") ||
                            livingEntity.getName().getString().contains("Sven Packmaster") ||
                            livingEntity.getName().getString().contains("Voidgloom Seraph") ||
                            livingEntity.getName().getString().contains("Riftstalker Bloodfiend") ||
                            livingEntity.getName().getString().contains("Inferno Demonlord")
            ).findFirst();


            Optional<LivingEntity> OpMiniboss = entities.stream().filter(livingEntity ->
                    livingEntity.getName().getString().contains("Revenant Champion") ||
                            livingEntity.getName().getString().contains("Deformed Revenant") ||
                            livingEntity.getName().getString().contains("Atoned Champion") ||
                            livingEntity.getName().getString().contains("Atoned Revenant") ||
                            livingEntity.getName().getString().contains("Sven Follower") ||
                            livingEntity.getName().getString().contains("Sven Alpha") ||
                            livingEntity.getName().getString().contains("Tarantula Vermin") ||
                            livingEntity.getName().getString().contains("Mutant Tarantula") ||
                            livingEntity.getName().getString().contains("Voidling Devotee") ||
                            livingEntity.getName().getString().contains("Voidling Radical") ||
                            livingEntity.getName().getString().contains("Voidcrazed Maniac")

            ).findFirst();

            String slayerName;

            if (OpSlayer.isPresent()) {
                slayerName = OpSlayer.get().getName().getString();
                RenderUtils.drawCenteredText(slayerName, config.slayerInfoVerticalOffset, drawContext);
            }

            if (OpMiniboss.isPresent()) {
                RenderUtils.drawCenteredText("Miniboss", -75, drawContext);
            }

        });


        WorldRenderEvents.AFTER_ENTITIES.register((worldRenderContext) -> {
            PlayerEntity player = client.player;
            Box box = new Box(player.getPos().add(-10, -5, -10), player.getPos().add(10, 5, 10));
            BlockPos beaconPos = null;
            // find beacon block
            assert client.world != null;
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
            RenderUtils.drawBox(worldRenderContext, worldRenderContext.matrixStack(), new Box(beaconPos), Color.RED);
        });

        ClientTickEvents.START_CLIENT_TICK.register(client1 -> {
//            if (KeyBinds.devKeybind.wasPressed()) {
//                client.player.sendMessage(Text.of("/warp dhub"), false);
//            }
        });

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


