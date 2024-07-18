package ananaseke.flare;

import ananaseke.flare.Plaques.Plaques;
import ananaseke.flare.Utils.RenderUtils;
import ananaseke.flare.fullbright.Fullbright;
import ananaseke.flare.garden.VisitorTracker;
import ananaseke.flare.misc.AntiSpam;
import ananaseke.flare.misc.ChatHider;
import ananaseke.flare.overlays.ItemOverlays;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.sound.SoundExecutor;
import net.minecraft.client.util.InputUtil;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class FlareClient implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("flare");
    public static final String MOD_ID = "flare";

    private static MinecraftClient client = MinecraftClient.getInstance();

    public static boolean shouldRender = false;
//    public static FilledMapItem map;
//    public static boolean shouldRenderMap = false;

    @Override
    public void onInitializeClient() {
        Plaques.initialize();
        Fullbright.initialize();
        ItemOverlays.initialize();
        Info.initialize();
        AntiSpam.initialize();
        KeyBinds.initialize();
        ChatHider.initialize();
        VisitorTracker.initialize();




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
//            if (client.player != null) {
//                client.player.networkHandler.getListedPlayerListEntries().forEach(entry -> {
//                    if (entry.getDisplayName() != null) {
//                        LOGGER.info(entry.getDisplayName().getString());
//                    }
//                });
//            }
        });


//        ClientTickEvents.END_CLIENT_TICK.register(client1 -> {
//            dev();
//        });


    }
}


